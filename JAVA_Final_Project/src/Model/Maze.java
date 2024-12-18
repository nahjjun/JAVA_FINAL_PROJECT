package Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Maze {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int USER_PLACE = 2;
	public static final int WALL_ENTRANCE = 3; // 벽 입구
	public static final int USER_ENTRANCE = 4; // 사용자 공간 입구
	public static final int USER_CHARACTER = 5; // 사용자 캐릭터
	public static final int Enemy_CHARACTER= 6; // 적 캐릭터
	public static final int BULLET = 7; // 총알
	
	
	
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static final int ROWS = 20;
	public static final int COLS = 21;
	// 맵 자동생성 시, 2칸씩 뚫어주기 때문에 한쪽 면이 제대로 안뚫리는 문제가 생겨서 Col의 길이를 하나 늘림
	
	
	private static final int [][]initialMatrix = {
			{1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1}, // 0
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 1
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 2
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 3
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 4
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 5
			{1,0,0,0,0,0,1,1,1,4,1,1,1,1,0,0,0,0,0,0,1}, // 6
			{1,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,1}, // 7
			{1,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,1}, // 8
			{3,0,0,0,0,0,4,2,2,2,2,2,2,4,0,0,0,0,0,0,3}, // 9
			{1,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,1}, // 10
			{1,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,1}, // 11
			{1,0,0,0,0,0,1,2,2,2,2,2,2,1,0,0,0,0,0,0,1}, // 12
			{1,0,0,0,0,0,1,1,1,4,1,1,1,1,0,0,0,0,0,0,1}, // 13
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 14
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 15
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 16
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 17
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 18
			{1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1}}; // 19
		  // 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 20
	
	
	
	
	
	private int[][] mazeMatrix;	// 해당 미로의 형태를 담고 있는 int형 배열. View에서 미로를 출력할 때 사용할 예정
	private ArrayList<Coordinate>[][] graph; // 2차원 그래프 인접 리스트
	private final ArrayList<Coordinate> mazeEntrance;	// 미로의 입구 배열 
	private final ArrayList<Coordinate> userEntrance; 	// 사용자 공간의 입구의 배열
	private HashSet<Coordinate> userPlaceCoordinateSet; // 사용자 공간의 배열 객체들이 들어있는 집합. 이를 활용해서 사용자 캐릭터가 해당 공간에 들어왔는지 확인할 것이다.
	private HashSet<Coordinate> cantChangeWallCoordinateSet; // 사용자가 바꿀 수 없는 벽 좌표들이 들어있는 집합. 이를 활용해서 사용자가 맵을 변환할 때 제한 사항을 만들것이다.
	private HashSet<Coordinate> userPlaceWallCoordinateSet; // 사용자 공간을 둘러싸고 있는 벽을 담는 집합
	// 미로의 각 위치 노드를 미리 생성해놓기 위한 배열
	private Coordinate[][] coordinateMatrix;
	
	
	// ------------------------생성자-------------------------//
	public Maze() {
		mazeMatrix = new int[ROWS][COLS]; 
		initMazeMatrix();
		
		graph = new ArrayList[ROWS][COLS];
		for(int i=0; i<ROWS; ++i)
			for(int j=0; j<COLS; ++j)
				graph[i][j] = new ArrayList<Coordinate>();
				
		mazeEntrance = new ArrayList<Coordinate>();		
		// 기존에 주어줄 미로의 입구 위치 초기화 -> 북,동,남,서 순서대로 넣어야 함
		mazeEntrance.add(new Coordinate(0,9)); // 북쪽 입구
		mazeEntrance.add(new Coordinate(9,19)); // 동쪽 입구
		mazeEntrance.add(new Coordinate(19,9)); // 남쪽 입구
		mazeEntrance.add(new Coordinate(9,0)); // 서쪽 입구
				
		userEntrance = new ArrayList<Coordinate>();		
		// 기존에 주어줄 사용자 공간의 입구 위치 초기화 -> 북,동,남,서 순서대로 넣어야 함
		userEntrance.add(new Coordinate(6,9)); // 북쪽 입구
		userEntrance.add(new Coordinate(9,13)); // 동쪽 입구
		userEntrance.add(new Coordinate(13,9)); // 남쪽 입구
		userEntrance.add(new Coordinate(9,6)); // 서쪽 입구
		
		
		// 사용자 공간을 담은 집합 생성 및 초기화
		userPlaceCoordinateSet = new HashSet<Coordinate>();
		for(int row=7; row<=12; ++row) {
			for(int col=7; col<=12; ++col)
				userPlaceCoordinateSet.add(new Coordinate(row,col));
		}
		
		userPlaceWallCoordinateSet = new HashSet<Coordinate>();
		for(int row=6; row<=13; ++row) {
			for(int col=6; col<=13; ++col) {
				if(initialMatrix[row][col] == Maze.WALL || initialMatrix[row][col] == USER_ENTRANCE) // 사용자 공간을 둘러싸고 있는 벽을 만나면
					// 해당 집합에 좌표값을 넣는다.
					userPlaceWallCoordinateSet.add(new Coordinate(row,col));
			}
		}
		
		// 사용자가 변환할 수 없는 벽들의 좌표를 담은 집합 생성 및 초기화
		// 사용자 공간을 둘러싼 곳은 제외
		cantChangeWallCoordinateSet = new HashSet<Coordinate>();
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				if(initialMatrix[row][col] == Maze.WALL && !userPlaceWallCoordinateSet.contains(new Coordinate(row,col))) // 초기 벽을 만나면
					cantChangeWallCoordinateSet.add(new Coordinate(row,col));
			}
		}
		
		coordinateMatrix = new Coordinate[Maze.ROWS][Maze.COLS];
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				coordinateMatrix[row][col] = new Coordinate(row,col);		
			}
		}
		
		
	}
	// -------------------------------------------------//
	
	//---------------getter/setter--------------- //
	// 미로의 상태(벽, 길, 사용자 공간)를 설정하는 함수
	public void setMazeMatrix(int row, int col, int state) throws Exception{
		if(row<0 || row>=ROWS || col<0 || col>=COLS) {
			throw new Exception("Model/Maze/setMazeMatrix()/잘못된 row/col값을 입력했습니다.");
		}
		mazeMatrix[row][col] = state;
	}
	
	// mazeMatrix를 반환하는 함수
	public int[][] getMazeMatrix(){
		return mazeMatrix;
	}
	
	public void addMazeEntrance(int row, int col){
		userEntrance.add(new Coordinate(row, col));
	}
	public ArrayList<Coordinate> getMazeEntrance(){
		return mazeEntrance;
	}
	
	public HashSet<Coordinate> getUserPlaceWallCoordinateSet(){
		return userPlaceWallCoordinateSet;
	}
	
	//----------------public void initMazeMatrix()------------------------
	// int형 2차원 배열 mazeMatrix를 기존 상태로 초기화 하는 함수
	// 이 함수를 호출하고 나면 이후에 buildGraph()를 필히 호출해야 함
	public void initMazeMatrix() {
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				mazeMatrix[row][col] = initialMatrix[row][col];
			}
		}
	}
	
	
	//----------------public boolean isInUserPlace()------------------------
	// 인자로 받은 현재 좌표가 사용자 공간에 존재하는지 확인하는 함수
	// 해당 좌표가 사용자 공간에 존재하면 true, 존재하지 않으면 false 반환
	public boolean isInUserPlace(Coordinate currentCoordinate) {
		// 사용자 공간의 좌표들을 모아둔 집합에 해당 좌표가 포함되는지 확인한다.
		return userPlaceCoordinateSet.contains(currentCoordinate);
	}
	public boolean isInUserPlace(int row, int col) {
		// 사용자 공간의 좌표들을 모아둔 집합에 해당 좌표가 포함되는지 확인한다.
		return userPlaceCoordinateSet.contains(new Coordinate(row,col));
	}
	
	
	//----------------public boolean canChangeItCoordinateState()------------------------
	// 인자로 받은 해당 좌표가 상태를 바꿀 수 있는 좌표인지 확인하는 함수
	// 해당 좌표가 상태를(벽으로) 바꿀 수 있으면 true, 바꿀 수 없으면 false 반환
	public boolean canChangeItCoordinateState(Coordinate selectedCoordinate) {
		// 사용자 공간이나 바꿀 수 없는 벽을 선택하지 않았다면 true 반환
		return !(isInUserPlace(selectedCoordinate) || cantChangeWallCoordinateSet.contains(selectedCoordinate));
	}
	public boolean canChangeItCoordinateState(int row, int col) {
		Coordinate selectedCoordinate = new Coordinate(row,col);
		// 사용자 공간이나 바꿀 수 없는 벽을 선택하지 않았다면 true 반환
		return !(isInUserPlace(selectedCoordinate) || cantChangeWallCoordinateSet.contains(selectedCoordinate));
	}
	
	
	//----------------public void buildGraph()------------------------
	// mazeMatrix를 기반으로 그래프를 인접리스트로 build하는 함수
	public void buildGraph() {
		for(int row=0; row<ROWS; ++row) {
			for(int col=0; col<COLS; ++col) {
				graph[row][col].clear();
				switch(mazeMatrix[row][col]) {
				case PATH:
				case WALL_ENTRANCE:
				case USER_ENTRANCE:
				case USER_PLACE:
					buildGraphList(row, col); // 길이거나 벽/사용지공간 입구면 graph를 build시킨다.
					break;
				}
			}
		}
		System.out.println("그래프가 rebuild되었습니다.");
	}
		// 해당 좌표에 연결되어있는 좌표를 그래프에 추가해주는 함수
		private void buildGraphList(int row, int col) {
			if (row - 1 >= 0 && canGoCoordinate(row - 1, col)) {
				graph[row][col].add(coordinateMatrix[row - 1][col]);
			}
			if (row + 1 < ROWS && canGoCoordinate(row + 1, col)) {
				graph[row][col].add(coordinateMatrix[row + 1][col]);
			}
			if (col + 1 < COLS && canGoCoordinate(row, col + 1)) {
				graph[row][col].add(coordinateMatrix[row][col + 1]);
			}
			if (col - 1 >= 0 && canGoCoordinate(row, col - 1)) {
				graph[row][col].add(coordinateMatrix[row][col - 1]);
			}
		}

		// 해당 좌표로 갈 수 있는 지 확인하는 함수
		private boolean canGoCoordinate(int row, int col) {
			return mazeMatrix[row][col]==PATH || mazeMatrix[row][col]==WALL_ENTRANCE || mazeMatrix[row][col]==USER_ENTRANCE ;
		}
	 // 
	
	//--------------public ArrayList<Coordinate> getShortestPath()--------------------------
	// 시작 좌표를 입력하면, 해당 좌표를 기준으로 userEntrance들 중에서 가장 가까운 userEntrance로 가는 경로를 ArrayList로 반환해주는 함수 
	public ArrayList<Coordinate> getShortestPath(Coordinate startCoordinate) throws Exception{
		if(startCoordinate==null) throw new Exception("Maze/getShortestPath()/");
		
		ArrayList<Coordinate> shortestPath = new ArrayList<Coordinate>(); // 가장 짧은 경로를 담는 배열
		int shortestPathInt = Integer.MAX_VALUE; // 가장 짧은 경로의 길이를 담는 배열
		
		for(Coordinate destinationCoordinate:userEntrance) {
			ArrayList<Coordinate> path = new ArrayList<>();
			int pathInt = bfsShortestPath(startCoordinate, destinationCoordinate, path);
			if(pathInt != -1 && pathInt<shortestPathInt) { // 새로 찾은 길이 기존의 길보다 짧으면 경로 최신화
				shortestPathInt = pathInt;
				shortestPath = path;
			}
		}
		
		return shortestPath; 
	}
	
		// 적의 현재 좌표를 기준으로, destination까지의 최단경로를 계산(BFS)하여 해당 경로를 설정하고 거리를 반환하는 함수. detination에 도착하지 못하면 -1을 반환한다.
		public int bfsShortestPath(Coordinate startCoordinate, Coordinate destinationCoordinate, ArrayList<Coordinate> shortestPath) throws Exception{
			if(startCoordinate==null)
				throw new Exception("Maze/bfsShortestPath()/인자로 받은 현재 좌표값이 null입니다.");
			else if(destinationCoordinate==null)
				throw new Exception("Maze/bfsShortestPath()/인자로 받은 목적지 좌표값이 null입니다.");
			else if(shortestPath==null)
				throw new Exception("Maze/bfsShortestPath()/인자로 받은 shortestPath값이 null입니다.");
			
			
			int[][] distance = new int[ROWS][COLS]; // 시작 coordinate에서 각 좌표까지 걸리는 거리를 저장할 2차원 배열
			boolean[][] visited = new boolean[ROWS][COLS];
			Queue<Coordinate> queue = new ArrayDeque<>();
			
			Coordinate current = startCoordinate;
			int currentRow = startCoordinate.getRow();
			int currentCol= startCoordinate.getCol();
			int currentDistance = distance[currentRow][currentCol];
					
			queue.add(current);
			visited[currentRow][currentCol] = true;
			while(!queue.isEmpty()) {
				current = queue.poll();
				currentRow = current.getRow();
				currentCol = current.getCol();
				currentDistance = distance[currentRow][currentCol];

				// 목적지에 도착하면 거리 반환
				if(current.equals(destinationCoordinate)) {
					
					rebuildPath(current, shortestPath); // 도착 좌표를 기준으로 최단거리 경로를 rebuild해준다.
					break;
				}
				for(Coordinate next:graph[currentRow][currentCol]) {
					if(!visited[next.getRow()][next.getCol()]) {
						visited[next.getRow()][next.getCol()] = true; // 방문처리
						queue.add(next);
						distance[next.getRow()][next.getCol()] = ++currentDistance; // 거리 최신화
						// 이전 좌표값 설정
						next.setPrevCoordinate(current);
					}
				}
			}
			
			return currentDistance;		
		}
		// 도착 좌표를 기준으로 거슬러 올라가며 최단거리 상세 경로를 rebuild해주는 함수   
		private void rebuildPath(Coordinate destinationCoordinate, ArrayList<Coordinate> shortestPath) {
			Coordinate current = destinationCoordinate;
			while(current.getPrevCoordinate()!=null) {
				shortestPath.add(current);
				current = current.getPrevCoordinate(); 
			}
			Collections.reverse(shortestPath);
		}
		
	
		
		
	// ----------- 미로를 랜덤으로 만들어주는 함수(이차원 배열값을 수정해줌)  ------------ //
	// 입구가 막혔을 때, 
	public void makeRandomMaze() {
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				if(mazeMatrix[row][col]==0)
					mazeMatrix[row][col] = WALL;
			}
		}
		// Dfs로 미로의 길을 뚫어준다.
		makeRandomMazeDFS(mazeEntrance.get(0).getRow(), mazeEntrance.get(0).getCol());
		
		// 새로운 길이 뚫렸기에, 사용자 공간 입구를 다 지워주고 다시 설정해준다. 
		userEntrance.clear();
		for(int row=6; row<=13; ++row) {
			for(int col=6; col<=13; ++col) {
				if(mazeMatrix[row][col]==USER_ENTRANCE)
					userEntrance.add(new Coordinate(row,col));
			}
		}	
	}
		
		// DFS를 사용해서 맵을 만드는 방법
		private void makeRandomMazeDFS(int startRow, int startCol) {
			Stack<Coordinate> stack = new Stack<>();
			// 시작 좌표를 생성해서 Stack에 push한다.
			Coordinate newCoordinate = new Coordinate(startRow, startCol);
			stack.push(newCoordinate);
			// mazeMatrix는 dfs에서의 visited 역할을 해준다.
		    mazeMatrix[startRow][startCol] = PATH; // 시작점을 경로로 설정
		      
	        while (!stack.isEmpty()) {
	        	Coordinate currentCoordinate = stack.pop();
	            int row = currentCoordinate.getRow();
	            int col = currentCoordinate.getCol();

	            // 방향 데이터를 배열에 넣는다.
	            // Arrays.asList()로 해당 배열을 데이터를 넣은 상태로 초기화 할 수 있음
	            ArrayList<Integer> directions = new ArrayList<>(Arrays.asList(NORTH,SOUTH,WEST,EAST));
	            // Collections.shuffle 함수를 이용해서 랜덤으로 내용을 섞는다. 이로써 랜덤으로 맵 생성이 가능해진다. 
	            Collections.shuffle(directions);

	            // 랜덤으로 생성한 방향 데이터 순서대로 dfs 진행
	            for (int direction : directions) {
	            	// 다음으로 이동할 행/열 값을 가져옴
	            	// 2칸씩 이동해준다.
	            	// 1칸으로 하면 맵이 다 지워져버린다.
	                int nextRow = row + getMovePriceRow(direction)*2; 
	                int nextCol = col + getMovePriceCol(direction)*2;

	                // 다음으로 이동할 좌표가 허용범위 내에 있고, 다음으로 이동할 공간의 좌표가 벽이라면 해당 방향으로 2칸을 뚫어준다.
	                // 벽인지 검사하는 조건문을 뒤에 둬야함. 그래야 out of bound 오류가 안남
	                if (isInMazeBounds(nextRow, nextCol) && mazeMatrix[nextRow][nextCol] == WALL) {
	                	// 바꿀 좌표가 사용자 공간을 둘러싼 벽이라면, 해당 좌표는 사용자 공간 입구로 설정한다.
	                	int nextRow1 = row + getMovePriceRow(direction);
	                	int nextCol1 = col + getMovePriceCol(direction);
	                	if(userPlaceWallCoordinateSet.contains(new Coordinate(nextRow1,nextCol1)))
	                		mazeMatrix[nextRow1][nextCol1] = USER_ENTRANCE;
	                	else
	                		mazeMatrix[nextRow1][nextCol1] = PATH;
	                	
	                	if(userPlaceWallCoordinateSet.contains(new Coordinate(nextRow, nextCol)))
	                		mazeMatrix[nextRow][nextCol] = USER_ENTRANCE;
	                	else
	                		mazeMatrix[nextRow][nextCol] = PATH;
	                	
	                    // 새로운 다음 좌표를 stack에 push해준다.
	                    stack.push(new Coordinate(nextRow, nextCol));
	                }
	            }
	        }
		}
		
		// 행 기준으로 움직여야할 값을 반환해주는 함수 
		private int getMovePriceRow(int direction) {
			int answer=0;
			switch(direction) {
			case NORTH:
				answer = -1; 
				break;
			case SOUTH:
				answer = 1;
				break;
			}
			return answer;
		}
		// 열 기준으로 움직여야할 값을 반환해주는 함수 
		private int getMovePriceCol(int direction) {
			int answer=0;
			switch(direction) {
			case WEST:
				answer = -1; 
				break;
			case EAST:
				answer = 1;
				break;
			}
			return answer;
		}
				
		// 해당 좌표가 미로를 만들 허용 범위 내에 있는지 확인해주는 함수
		// 가장 가장자리는 벽이 있어야하므로, 해당 부분도 제외하고 확인한다.
		// 사용자 공간도 제외하고 확인한다
		private boolean isInMazeBounds(int row, int col) {
			if((row > 0 && row < Maze.ROWS && col > 0 && col < Maze.COLS) && !isInUserPlace(row,col))
				return true;
			return false;
		}
		
		
		
}

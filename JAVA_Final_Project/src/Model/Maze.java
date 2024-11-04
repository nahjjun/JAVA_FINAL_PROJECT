package Model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Maze {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int USER_PLACE = 2;
	public static final int WALL_ENTRANCE = 3; // 벽 입구
	public static final int USER_ENTRANCE = 4; // 사용자 공간 입구
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static final int ROWS = 20;
	public static final int COLS = 20;
	
	
	private int[][] mazeMatrix;	// 해당 미로의 형태를 담고 있는 int형 배열. View에서 미로를 출력할 때 사용할 예정
	private ArrayList<Coordinate>[][] graph; // 2차원 그래프 인접 리스트
	private final Coordinate[] mazeEntrance;	// 미로의 입구 배열 
	private final Coordinate[] userEntrance; 	// 사용자 공간의 입구의 배열
	
	public Maze() {
		mazeMatrix = new int[ROWS][COLS]; 
		graph = new ArrayList[ROWS][COLS];
		for(int i=0; i<ROWS; ++i)
			for(int j=0; j<COLS; ++j)
				graph[i][j] = new ArrayList<Coordinate>();
				
		mazeEntrance = new Coordinate[4];		
		// 기존에 주어줄 미로의 입구 위치 확정해야함
		for(int i=0; i<4; ++i)
			mazeEntrance[i] = new Coordinate();
		
		userEntrance = new Coordinate[4];
		// 기존에 주어줄 사용자의 입구 위치 확정해야함
		for(int i=0; i<4; ++i)
			userEntrance[i] = new Coordinate();
	}
	
	
	//---------------getter/setter---------------
	// 미로의 상태(벽, 길, 사용자 공간)를 설정하는 함수
	public void setMazeMatrix(int row, int col, int state) throws Exception{
		if(row<0 || row>=ROWS || col<0 || col>=COLS) {
			throw new Exception("Maze/setMazeMatrix()/잘못된 row/col값을 입력했습니다.");
		}
		mazeMatrix[row][col] = state;
	}
	
	// mazeMatrix를 반환하는 함수
	public int[][] getMazeMatrix(){
		return mazeMatrix;
	}
	//----------------public void buildGraph()------------------------
	// mazeMatrix를 기반으로 그래프를 인접리스트로 build하는 함수
	public void buildGraph() {
		for(int row=0; row<ROWS; ++row) {
			for(int col=0; col<COLS; ++col) {	
				switch(mazeMatrix[row][col]) {
				case WALL:
				case USER_PLACE:
					graph[row][col].clear(); // 사용자 공간이거나 벽이면 해당
					break;
				case PATH:
				case WALL_ENTRANCE:
				case USER_ENTRANCE:
					buildGraphList(row, col); // 길이거나 벽/사용지공간 입구면 graph를 build시킨다.
					break;
				}
			}
		}
	}
		// 해당 좌표에 연결되어있는 리스트를 그래프에 설정해주는 함수
		private void buildGraphList(int row, int col) {
			if(row-1>=0 && row-1<ROWS && canGoCoordinate(row-1,col)) // 북 검사 
				graph[row][col].add(new Coordinate(row-1,col));
			if(row+1>=0 && row+1<ROWS && canGoCoordinate(row+1,col)) // 남 검사
				graph[row][col].add(new Coordinate(row+1,col));
			if(col+1>=0 && col+1<COLS && canGoCoordinate(row,col+1)) // 동 검사
				graph[row][col].add(new Coordinate(row,col+1));
			if(col-1>=0 && col-1<COLS && canGoCoordinate(row,col-1)) // 서 검사
				graph[row][col].add(new Coordinate(row,col-1));
		}
		// 해당 좌표로 갈 수 있는 지 확인하는 함수
		private boolean canGoCoordinate(int row, int col) {
			return mazeMatrix[row][col]==PATH || mazeMatrix[row][col]==WALL_ENTRANCE || mazeMatrix[row][col]==USER_ENTRANCE;
		}
	
	
	//--------------public ArrayList<Coordinate> getShortestPath()--------------------------
	// 시작 좌표를 입력하면, 해당 좌표를 기준으로 userEntrance들 중에서 가장 가까운 userEntrance로 가는 경로를 ArrayList로 반환해주는 함수 
	public ArrayList<Coordinate> getShortestPath(Coordinate startCoordinate) throws Exception{
		if(startCoordinate==null) throw new Exception("Maze/getShortestPath()/");
		
		ArrayList<Coordinate> shortestPath = new ArrayList<Coordinate>(); // 가장 짧은 경로를 담는 배열
		int shortestPathInt = Integer.MAX_VALUE; // 가장 짧은 경로의 길이를 담는 배열
		
		for(Coordinate destinationCoordinate:userEntrance) {
			ArrayList<Coordinate> path = new ArrayList<>();
			int pathInt = bfsShortestPath(startCoordinate, destinationCoordinate, path);
			if(pathInt<shortestPathInt) { // 새로 찾은 길이 기존의 길보다 짧으면 경로 최신화
				shortestPathInt = pathInt;
				shortestPath = path;
			}
		}
		return shortestPath; 
	}
	
		// 적의 현재 좌표를 기준으로, destination까지의 최단경로를 계산(BFS)하여 해당 경로를 설정하고 거리를 반환하는 함수. detination에 도착하지 못하면 -1을 반환한다.
		private int bfsShortestPath(Coordinate startCoordinate, Coordinate destinationCoordinate, ArrayList<Coordinate> shortestPath) throws Exception{
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
					rebuildPath(destinationCoordinate, shortestPath); // 도착 좌표를 기준으로 최단거리 경로를 rebuild해준다.
					return currentDistance;
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
			return -1; // 목표 노드에 도달 못하면 -1을 반환한다.		
		}
		// 도착 좌표를 기준으로 거슬러 올라가며 최단거리 상세 경로를 rebuild해주는 함수   
		private void rebuildPath(Coordinate destinationCoordinate, ArrayList<Coordinate> shortestPath) {
			Coordinate current = destinationCoordinate;
			while(current.getPrevCoordinate()!=null) {
				shortestPath.add(current);
			}
			shortestPath.reversed();
		}
	
	
	
	// userEntrance까지 찾아서 도착했다면, 사용자 공간 안에서 user에게 실시간으로 다가가야 한다.	
	
	
}

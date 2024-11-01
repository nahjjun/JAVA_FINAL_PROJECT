package Model;

import java.util.ArrayList;
import java.util.Stack;

public class Maze {
	public static final int PATH = 0;
	public static final int WALL = 1;
	public static final int USERPLACE = 2;
	
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
	
	// mazeMatrix를 기반으로 그래프를 build하는 함수
	// 그래프를 build할 때, 인접 리스트로 build한다. 이때, 
	// 다익스트라가 필요 없을 수도 있다?
	public void buildGraph() {
		
	}
	
	
	
	// 적의 현재 좌표를 기준으로, 각 userEntrance까지의 최단경로를 계산(DFS)하여 해당 경로를 반환하는 함수
	// 만약 해당 경로가 userEntrance까지 가지 못한다면, 해당 list를 비워서 return한다.
	public ArrayList<Coordinate> getShortestPath(Coordinate currentCoordinate) throws Exception{
		if(currentCoordinate==null) {
			throw new Exception("Maze/getShortestPath()/인자로 받은 현재 좌표값이 null입니다.");
		}
		ArrayList<Coordinate> resultPath = new ArrayList<>();
		
		
		
		
	}
	
	// 각 userEntrance를 선택해서 다익스트라 알고리즘을 수행하는 함수.
	// path에 진행 완료한 경로를 저장하고, currentCoordinate는 현재 좌표, entrance는 userEntrance를 결정하는 변수이다.
	private int dijkstra(ArrayList<Coordinate> path, Coordinate currentCoordinate, int entrance) throws Exception{
		if(path==null) throw new Exception("Maze/dijkstra()/전달된 ArrayList의 주소가 null입니다.");
		else if(currentCoordinate==null)throw new Exception("Maze/dijkstra()/전달된 현재 좌표 객체가 null입니다.");
		else if(entrance<NORTH || entrance>WEST)throw new Exception("Maze/dijkstra()/전달된 user 공간의 입구가 잘못되었습니다.");
		
		int[][] distance = new int[ROWS][COLS]; // 거리를 저장하는 2차원 배열
		
		Stack<Coordinate> stack = new Stack<>();
		boolean [][]visited = new boolean[ROWS][COLS];
		int currentRow = currentCoordinate.getRow();
		int currentCol = currentCoordinate.getCol();
		visited[currentRow][currentCol] = true; // 방문처리
		stack.add(currentCoordinate);
		while()
		
		
		
		
	}
	
	
	
	
}

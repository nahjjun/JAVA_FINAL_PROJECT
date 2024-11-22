package Model;

import View.GamePlayPanel;

public class MainCharacter extends MoveObject{
	private Model model;
	private int direction;
	private int prevDirection;
	
	
	public MainCharacter(Model model) {
		super(300,300,20,20);
		this.model = model;
		direction = Maze.NORTH;
		prevDirection = Maze.NORTH;
	}
	
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setDirection(int direction) throws Exception {
		if(direction < 0 || direction >=4) throw new Exception("Model/MainCharacter/setDirection()/잘못된 방향 입력입니다.");
		prevDirection = this.direction;
		this.direction = direction;
	}
	public int getDirection() {
		return direction;
	}
	
	public int getPrevDirection() {
		return prevDirection;
	}
	
	
	@Override
	public void increaseRow() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		// 해당 위치의 maze의 행렬값을 확인했을 때 사용자 공간이면 캐릭터 이동
		if((model.getMaze().getMazeMatrix()[(row+height)/cellLength][col/cellLength])!=Maze.WALL && (model.getMaze().getMazeMatrix()[(row+height)/cellLength][col/cellLength])!=Maze.USER_ENTRANCE) {
			row += GamePlayPanel.GRID_LENGTH;
			prevRow = row; // 이전 위치 최신화
		}	
	}
	@Override
	public void decreaseRow() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[(row-GamePlayPanel.GRID_LENGTH)/cellLength][col/cellLength])!=Maze.WALL && (model.getMaze().getMazeMatrix()[(row-GamePlayPanel.GRID_LENGTH)/cellLength][col/cellLength])!=Maze.USER_ENTRANCE) {
			row -= GamePlayPanel.GRID_LENGTH;
			prevRow = row;
		}
	}
	@Override
	public void increaseCol() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[row/cellLength][(col+width)/cellLength])!=Maze.WALL && (model.getMaze().getMazeMatrix()[row/cellLength][(col+width)/cellLength])!=Maze.USER_ENTRANCE) {
			col += GamePlayPanel.GRID_LENGTH;
			prevCol = col;
		}			
	}
	@Override
	public void decreaseCol() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[row/cellLength][(col-GamePlayPanel.GRID_LENGTH)/cellLength])!=Maze.WALL && (model.getMaze().getMazeMatrix()[row/cellLength][(col-GamePlayPanel.GRID_LENGTH)/cellLength])!=Maze.USER_ENTRANCE) {
			col -= GamePlayPanel.GRID_LENGTH;
			prevCol = col;
		}			
	}
	
	
	
	
	
	
	
}

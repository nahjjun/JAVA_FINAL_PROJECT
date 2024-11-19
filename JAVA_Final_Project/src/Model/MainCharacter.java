package Model;

import View.GamePlayPanel;

public class MainCharacter extends MoveObject{
	private Model model;
	
	public MainCharacter(Model model) {
		super(300,300,20,20);
		this.model = model;
	}
	@Override
	public void increaseRow() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		// 해당 위치의 maze의 행렬값을 확인했을 때 사용자 공간이면 캐릭터 이동
		if((model.getMaze().getMazeMatrix()[(int)(row+cellLength)/cellLength][(int)col/cellLength])==Maze.USER_ENTRANCE) {
			row += GamePlayPanel.GRID_LENGTH;
			prevRow = row; // 이전 위치 최신화
		}
			
	}
	@Override
	public void decreaseRow() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[(int)(row-cellLength)/cellLength][(int)col/cellLength])==Maze.USER_ENTRANCE) {
			row -= GamePlayPanel.GRID_LENGTH;
			prevRow = row;
		}
	}
	@Override
	public void increaseCol() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[(int)row/cellLength][(int)(col+cellLength)/cellLength])==Maze.USER_ENTRANCE) {
			col += GamePlayPanel.GRID_LENGTH;
			prevCol = col;
		}			
	}
	@Override
	public void decreaseCol() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		if((model.getMaze().getMazeMatrix()[(int)row/cellLength][(int)(col-cellLength)/cellLength])==Maze.USER_ENTRANCE) {
			col -= GamePlayPanel.GRID_LENGTH;
			prevCol = col;
		}			
	}
	
	
	
	
	
	
	
}

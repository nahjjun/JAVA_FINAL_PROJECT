package Model;

import java.util.ArrayList;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject {
	private Model model;
	private ArrayList<Coordinate> path; // 
	
	public EnemyCharacter(Model model, int row, int col) {
		super(row,col,20,20);
		
		this.model = model;	
		path = new ArrayList<Coordinate>();
		updatePath();
	}
	
	// 멀티스레딩으로 enemy 캐릭터를 동작시키기 위해, 스레드를 상속받아서 해당 스레드로 동작시킬 함수를 구현한다. 
	@Override
	public void run() {
	    while (true) {
	        if (path.isEmpty() || path.size() <= 1) {
	            updatePath();
	        }
	        if (!path.isEmpty()) {
	            moveAlongPath();
	        }

	        try {
	            Thread.sleep(1000/100); // 속도 조절
	        } catch (InterruptedException e) {
	            break; // 스레드 종료
	        }
	    }
	}

	private void moveAlongPath() {
	    Coordinate next = path.get(0); // 다음 좌표
	    path.remove(0); // 이동한 좌표 제거

	    int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
	    if (next.getRow() < row / cellLength) {
	        decreaseRow();
	    } else if (next.getRow() > row / cellLength) {
	        increaseRow();
	    } else if (next.getCol() < col / cellLength) {
	        decreaseCol();
	    } else if (next.getCol() > col / cellLength) {
	        increaseCol();
	    }
	}
 
	
	
	
	// view에서 해당 적 캐릭터의 위치를 설정해줘야 함.
	public void updatePath() {
	    try {
	        int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
	        path = model.getMaze().getShortestPath(new Coordinate(row / cellLength, col / cellLength));
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	}


	
	public ArrayList<Coordinate> getPath(){
		return path;
	}
		
	
	public void increaseRow() {
		prevRow = row;
		row += GamePlayPanel.GRID_LENGTH;
	}
	public void decreaseRow() {
		prevRow = row;
		row -= GamePlayPanel.GRID_LENGTH;
	}
	public void increaseCol() {
		prevCol = col;
		col += GamePlayPanel.GRID_LENGTH;
	}
	public void decreaseCol() {
		prevCol = col;
		col -= GamePlayPanel.GRID_LENGTH;
	}
}

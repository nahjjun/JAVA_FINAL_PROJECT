package Model;

import java.util.ArrayList;

import javax.swing.CellEditor;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject {
	private Model model;
	private ArrayList<Coordinate> path; //
	// 적 캐릭터의 이동 속도를 조절할 수 있는 변수
	private final int moveOnce = 1;
	
	
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
	    	int i=0;
	    	// user entrance에 도착했을 때 수행할 작업
	        if (path.isEmpty()||path.size()==1) {
	        	break;
	        }
	        if (!path.isEmpty()) {
	            moveAlongPath();
	        }

	        try {
	            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
	        } catch (InterruptedException e) {
	            break; // 스레드 종료
	        }
	    }
	}

	private void moveAlongPath() {
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
	    
	    Coordinate current = path.get(0);
	    Coordinate next = path.get(1); // 다음 좌표
	    
	    int direction=-1;
	    if(next.getRow() < current.getRow())
	    	direction = Maze.NORTH;
	    else if(next.getRow() > current.getRow())
	    	direction = Maze.SOUTH;
	    else if(next.getCol() < current.getCol())
	    	direction = Maze.WEST;
	    else if(next.getCol() > current.getCol())
	    	direction = Maze.EAST;
	    
	    if (direction == Maze.NORTH && current.getRow()*cellLength < row+25) {
	    		decreaseRow();
	    } else if (direction == Maze.SOUTH && current.getRow()*cellLength > row-25) {
	    		increaseRow();
	    } else if (direction == Maze.WEST && current.getCol()*cellLength < col+25) {
	    		decreaseCol();
	    } else if (direction == Maze.EAST && current.getCol()*cellLength > col-35) {
	    		increaseCol();
	    }
	    else
	    	path.remove(0);
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
		row += moveOnce;
	}
	public void decreaseRow() {
		prevRow = row;
		row -= moveOnce;
	}
	public void increaseCol() {
		prevCol = col;
		col += moveOnce;
	}
	public void decreaseCol() {
		prevCol = col;
		col -= moveOnce;
	}
}

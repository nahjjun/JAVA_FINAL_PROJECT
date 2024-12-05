package Model;

import java.util.Iterator;

import View.GamePlayPanel;

public class Bullet extends MoveObject{
	private Model model;
	private final int direction; // 해당 총알이 부여받은 방향. 값이 -1이면 스레드 종료
	
	// ----- 총알 데이터 ----- // 
	private static int damage = 5;
	private static int moveOnce = 3;
	
	public boolean canRun = true;
	
	public static final int BULLET_SIZE=6;
	
	public Bullet(Model model, int row, int col, int direction) {
		super(row, col, BULLET_SIZE, BULLET_SIZE);
		this.model = model;
		this.direction = direction;
	}
	
	
	public void run() {
		// 총알 움직임 구현
        switch(direction) {
        case Maze.NORTH:
        	decreaseRow();
        	break;
        case Maze.SOUTH:
        	increaseRow();
        	break;
        case Maze.WEST:
        	decreaseCol();
        	break;
        case Maze.EAST:
        	increaseCol();
        	break;
        }   
        isWallImpacted();	
	}

	
	// 총알이 아직 미로(맵)안에 존재하는지 확인하는 함수
	private boolean isInMaze() {
		if(row >=0 && row<=GamePlayPanel.MAX_ROW && col >=0 && col<=GamePlayPanel.MAX_COL)
			return true;
		return false;
	}
	// 총알이 벽에 부딪혔는지 확인하는 함수
	private void isWallImpacted() {
		Iterator<Wall> iterator = model.getWalls().iterator();
        while (iterator.hasNext()) {
            Wall wall = iterator.next();
            if (isImpacted(wall)) {	            	
            	canRun = false;
                break;
            }         
        }
	}

	
	
	// ----- setter / getter ----- //
	
	public static void setDamage(int d) {
		damage = d;	
	}
	public static int getDamage() {return damage;}
	
	public static void setMoveOnce(int m) {
		moveOnce = m;	
	}
	public static int getMoveOnce() {return moveOnce;}
	
	// ----- increase / decrease ----- //
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

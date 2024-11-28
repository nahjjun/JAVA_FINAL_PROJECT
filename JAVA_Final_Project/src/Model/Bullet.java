package Model;

import java.util.Iterator;

import View.GamePlayPanel;

public class Bullet extends MoveObject{
	private Model model;
	private final int direction; // 해당 총알이 부여받은 방향. 값이 -1이면 스레드 종료
	public volatile boolean canRun = true;
	// https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%99%80-%ED%95%B4%EB%B2%95-%EC%8A%A4%EB%A0%88%EB%93%9C-%EB%8F%99%EA%B8%B0%ED%99%94
	// 자바의 volatile은 JIT 컴파일러가 최적화 작업을 수행하지 않게 함으로써, 연산과정에서 캐시가 아닌 실제 메모리에 바로 접근하도록 한다
	
	// ----- 총알 데이터 ----- // 
	private static int damage = 5;
	private static int moveOnce = 2;
	
	
	public static final int BULLET_SIZE=6;
	
	public Bullet(Model model, int row, int col, int direction) {
		super(row, col, BULLET_SIZE, BULLET_SIZE);
		this.model = model;
		this.direction = direction;
	}
	
	@Override
	public void run() {
		// 스레드 상태를 명확하게 관리하기 위해 "interrupted()" 사용
		while (canRun && !Thread.currentThread().isInterrupted() && isInMaze()) {
			synchronized (this) {
				try {					
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
			        
		            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
		        } catch (InterruptedException e) {
		        	Thread.currentThread().interrupt();
		            break; // 스레드 종료
		        }	
			}
		}
	}

	public void stopRunning() {
		canRun = false;
	}
	
	// 총알이 아직 미로(맵)안에 존재하는지 확인하는 함수
	private boolean isInMaze() {
		if(row >=0 && row<=GamePlayPanel.MAX_ROW && col >=0 && col<=GamePlayPanel.MAX_COL)
			return true;
		return false;
	}
	// 총알이 벽에 부딪혔는지 확인하는 함수
	private void isWallImpacted() {
		synchronized (model.getBullets()) {
	        Iterator<Wall> iterator = model.getWalls().iterator();
	        while (iterator.hasNext()) {
	            Wall wall = iterator.next();
	            if (isImpacted(wall)) {	            	
	                this.stopRunning();
	                this.canRun=false;
	                model.getBullets().remove(this);
	                break;
	            }         
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

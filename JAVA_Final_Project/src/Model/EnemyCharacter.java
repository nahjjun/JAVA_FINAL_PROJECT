package Model;

import java.util.ArrayList;
import java.util.Iterator;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject{
	private Model model;
	private ArrayList<Coordinate> path; //
	// 적 캐릭터의 이동 속도를 조절할 수 있는 변수
	
	public volatile boolean canRun = true;
	// https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%99%80-%ED%95%B4%EB%B2%95-%EC%8A%A4%EB%A0%88%EB%93%9C-%EB%8F%99%EA%B8%B0%ED%99%94
	
	
	// ------ 캐릭터 데이터 ------ //
	private int damage = 5;  
	private int health = 5;
	
	public static int moveOnce = 1;
	
	
	public EnemyCharacter(Model model, int row, int col) {
		super(row,col,20,20);
		
		this.model = model;	
		path = new ArrayList<Coordinate>();
	}
	
	// 멀티스레딩으로 enemy 캐릭터를 동작시키기 위해, 스레드를 상속받아서 해당 스레드로 동작시킬 함수를 구현한다. 
	@Override
	public void run() {
		// 스레드 상태를 명확하게 관리하기 위해 "interrupted()" 사용
	    while (canRun && !Thread.currentThread().isInterrupted()) {
	    	updatePath();
	    	try {
	    		// user entrance에 도착했을 때 수행할 작업
	    	
	    		// 갈 길을 전부 갔다면 스레드 종료
	    		if (path.isEmpty() || path.size()==1) {
	    			canRun = false;
	    			continue;
	    		}
	    		// 먼저 움직이고 총알이 부딪혔는지 확인
	    		synchronized (this) {
	    			moveAlongPath();
	    			// 해당 블록 내에서 model.getBullets() 리스트에 대한 모든 읽기 및 쓰기 작업이 단일 스레드에서만 수행되도록 보호한다.
		    		// synchronized를 이용해서 데이터 경쟁을 방지해서 데이터를 보호한다.
		    		// 임계 영역 설정과 락 권한
	    			synchronized (model.getBullets()) {
		    			isBulletImpacted();
		    		}	
	    		}
	    		
	            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
    		} catch (InterruptedException e) {
    			Thread.currentThread().interrupt();
    			break; // 스레드 종료
    		}
    	}
	}
	
	// 총알이 해당 적 객체와 부딪혔는지 확인하는 메소드. 
	// iterator를 사용하면 더 안정적으로 해당 Bullet ArrayList에 있는 값을 수정할 수 있다.
	private void isBulletImpacted() {
		synchronized (model.getBullets()) {
	        Iterator<Bullet> iterator = model.getBullets().iterator();
	        while (iterator.hasNext()) {
	            Bullet bullet = iterator.next();
	            System.out.println("충돌 검사: 적(" + this + ")와 총알(" + bullet + ")");
	            if (isImpacted(bullet)) {	            	
	                health -= Bullet.getDamage();
	                bullet.stopRunning();
	                iterator.remove();
	                if(health<=0) {
	                	this.canRun=false;
	                }
	            
	                break;
	            }         
	        }
	    }
	}

	private void moveAlongPath() {
		synchronized (this) {
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
		    System.out.println("적 캐릭터 이동: " + row +", "+col);
		}
	}
 

	
	// view에서 해당 적 캐릭터의 위치를 설정해줘야 함.
	public void updatePath() {
		synchronized (this) {
			try {
		        int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		        path = model.getMaze().getShortestPath(new Coordinate(row / cellLength, col / cellLength));
		        
		        	
		    } catch (Exception e) {
		        System.err.println(e.getMessage());
		    }	
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

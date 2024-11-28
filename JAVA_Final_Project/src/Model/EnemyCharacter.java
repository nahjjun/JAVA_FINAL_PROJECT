package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject{
	private Model model;
	private ArrayList<Coordinate> path; //
	// 적 캐릭터의 이동 속도를 조절할 수 있는 변수
	
	public volatile boolean canRun = true;
	// https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%99%80-%ED%95%B4%EB%B2%95-%EC%8A%A4%EB%A0%88%EB%93%9C-%EB%8F%99%EA%B8%B0%ED%99%94
	// 사용자 공간에 적 캐릭터가 도착했는지 판별하는 변수
	private volatile boolean isArrivedUserPlace = false;
	// 적 캐릭터가 메인 캐릭터와 부딪혔는지 확인하는 변수
	private volatile boolean isImpactedMainCharacter = false;

	
	// ------ 캐릭터 데이터 ------ //
	private int damage = 5;  
	private int health = 5;
	
	public static int moveOnce = 1;
	
	
	public EnemyCharacter(Model model, int row, int col) {
		super(row,col,20,20);
		
		this.model = model;	
		path = new ArrayList<Coordinate>();
		updatePath(); // 이 함수를 run()에 넣으면 정상적으로 동작이 안됨
	}
	
	// 멀티스레딩으로 enemy 캐릭터를 동작시키기 위해, 스레드를 상속받아서 해당 스레드로 동작시킬 함수를 구현한다. 
	@Override
	public void run() {		
		// 스레드 상태를 명확하게 관리하기 위해 "interrupted()" 사용
	    while (canRun && !isImpactedMainCharacter && !Thread.currentThread().isInterrupted()) {
	    	// 만약 사용자 공간에 도착했다면, 계속해서 사용자에게 다가가는 
	    	if(isArrivedUserPlace) {
	    		// 사용자가 메인 캐릭터에게 무한으로 다가간다.
	    		traceMainCharacter();
	    	}
	    	// 적 캐릭터의 위치가 사용자 공간이 아닌 경우
	    	else {
	    		// 사용자 공간 입구까지 이동한다.
	    		moveToUserEntrance();
	    	}
    	}    
	    
	}
	
	// ------------- private void isBulletImpacted() ------------//
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

	
	// -------------- private void moveToUserEntrance() --------------
	// 사용자 공간까지 미로를 따라서 이동하는 함수
	private void moveToUserEntrance() {
		while(canRun && !Thread.currentThread().isInterrupted()) {
			// user entrance에 도착했을 때 수행할 작업
			if(!isArrivedUserPlace && path.size()<=1) {
				isArrivedUserPlace = true;
				break;
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
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
	}
		// 나중에 리팩토링 해야함
		private void moveAlongPath() {
			synchronized (path) {	
			    Coordinate current = path.get(0);
			    Coordinate next = path.get(1); // 다음 좌표
			    synchronized(model) {
			    	int direction=-1;
				    if(next.getRow() < current.getRow())
				    	direction = Maze.NORTH;
				    else if(next.getRow() > current.getRow())
				    	direction = Maze.SOUTH;
				    else if(next.getCol() < current.getCol())
				    	direction = Maze.WEST;
				    else if(next.getCol() > current.getCol())
				    	direction = Maze.EAST;
				    
				    if (direction == Maze.NORTH && current.getRow()*GamePlayPanel.CELL_LENGTH < row+30) {
				    		decreaseRow();
				    } else if (direction == Maze.SOUTH && current.getRow()*GamePlayPanel.CELL_LENGTH > row-30) {
				    		increaseRow();
				    } else if (direction == Maze.WEST && current.getCol()*GamePlayPanel.CELL_LENGTH < col+30) {
				    		decreaseCol();
				    } else if (direction == Maze.EAST && current.getCol()*GamePlayPanel.CELL_LENGTH > col-30) {
				    		increaseCol();
				    }
				    else
				    	path.remove(0);		
			    }
			    
			}
		}
		// view에서 해당 적 캐릭터의 위치를 설정해줘야 함.
		private void updatePath() {
			synchronized (path) {
				try {
			        path = model.getMaze().getShortestPath(new Coordinate(row / GamePlayPanel.CELL_LENGTH, col / GamePlayPanel.CELL_LENGTH));
			        	
			    } catch (Exception e) {
			        System.err.println(e.getMessage());
			    }	
			}
		}


	// -------- private void traceMainCharacter() --------//
	// 사용자 공간에 적이 들어가면 메인 캐릭터에게 무한정 다가가는 함수
	private void traceMainCharacter() {
	    while (canRun && !Thread.currentThread().isInterrupted()) {
	        try {
	        	moveToMainCharacter();
	        	if(isImpacted(model.getMainCharacter())) {
	        		isImpactedMainCharacter = true;
	        		model.getMainCharacter().decreaseHealth(damage);
	        		System.out.println("메인 캐릭터 체력: "+model.getMainCharacter().getHealth());
	        		break;
	        	}
	        	synchronized (model.getBullets()) {
	    			isBulletImpacted();
	    		}
	            Thread.sleep(1000 / 60); // 이걸로 속도 조절 가능
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            break; // 스레드 종료
	        }
	    }
	}
	
		// 적 캐릭터가 메인 캐릭터에게 다가가는 메소드
		private void moveToMainCharacter() {
			int mainCharacterX = model.getMainCharacter().getCol();
			int mainCharacterY = model.getMainCharacter().getRow();
			
			// x 방향으로 이동
			if (col < mainCharacterX) {
				increaseCol();
			} else if (col > mainCharacterX) {
				decreaseCol();
			}
			
			// y 방향으로 이동
			if (row < mainCharacterY) {
				increaseRow();
			} else if (row > mainCharacterY) {
				decreaseRow();
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

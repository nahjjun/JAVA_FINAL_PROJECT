package Model;

import java.util.ArrayList;
import java.util.Iterator;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject{
	private Model model;
	private ArrayList<Coordinate> path; //
	// 적 캐릭터의 이동 속도를 조절할 수 있는 변수
	
	private volatile boolean canRun = true;
	// https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%99%80-%ED%95%B4%EB%B2%95-%EC%8A%A4%EB%A0%88%EB%93%9C-%EB%8F%99%EA%B8%B0%ED%99%94
	
	
	// ------ 캐릭터 데이터 ------ //
	private int damage = 5;  
	private int health = 5;
	
	public static int moveOnce = 1;
	
	
	public EnemyCharacter(Model model, int row, int col) {
		super(row,col,20,20);
		
		this.model = model;	
		path = new ArrayList<Coordinate>();
		updatePath();
	}
	
	// 멀티스레딩으로 enemy 캐릭터를 동작시키기 위해, 스레드를 상속받아서 해당 스레드로 동작시킬 함수를 구현한다. 
	@Override
	public void run() {
	    while (canRun) {
	    	int i=0;
	    	// user entrance에 도착했을 때 수행할 작업
	    	
	    	// 갈 길을 전부 갔거나, 체력이 전부 떨어지면 스레드 종료
	        if (path.isEmpty() || path.size()==1 || health<=0) {
	        	canRun = false;
	        	continue;
	        }
	         
	        // 해당 블록 내에서 model.getBullets() 리스트에 대한 모든 읽기 및 쓰기 작업이 단일 스레드에서만 수행되도록 보호한다.
	        // synchronized를 이용해서 데이터 경쟁을 방지해서 데이터를 보호한다.
	        synchronized (model.getBullets()) {
	        	isBulletImpacted();
	        }
            moveAlongPath();

	        try {
	            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
	        } catch (InterruptedException e) {
	            break; // 스레드 종료
	        }
	    }
	}
	
	
	private void isBulletImpacted() {
	    synchronized (model.getBullets()) {
	        Iterator<Bullet> iterator = model.getBullets().iterator();
	        while (iterator.hasNext()) {
	            Bullet bullet = iterator.next();
	            if (isImpacted(bullet)) {
	                health -= Bullet.getDamage();
	                bullet.stopRunning();
	                iterator.remove();
	            }         
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
 
	// 적 캐릭터가 총알과 부딪혔는지 확인하는 함수
    // 최적화를 위해, 한 반복문에 여러 2개씩 총알을 확인한다.
	public void checkIsImpacted() {
		int i;
		for (i = 0; i < model.getBullets().size(); i += 2) {
            if (isImpacted(model.getBullets().get(i))) {
                health -= Bullet.getDamage();
                model.getBullets().get(i).stopRunning();
                model.getBullets().remove(i);
                break;
            }
            if (i + 1 < model.getBullets().size() && isImpacted(model.getBullets().get(i + 1))) {
                health -= Bullet.getDamage();
                model.getBullets().get(i+1).stopRunning();
                model.getBullets().remove(i+1);
                break;
            }
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

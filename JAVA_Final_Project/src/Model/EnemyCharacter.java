package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import View.GamePlayPanel;

public class EnemyCharacter extends MoveObject{
	private Model model;
	private ArrayList<Coordinate> path; //
	// 적 캐릭터의 이동 속도를 조절할 수 있는 변수
	
	// 사용자 공간에 적 캐릭터가 도착했는지 판별하는 변수
	private boolean isArrivedUserPlace = false;

	public boolean canRun = true;
	
	// ------ 캐릭터 기본 데이터 ------ //
	public static int damage = 1;  
	public static int health = 1;
	public static int moveOnce = 2;
	
	// 현재 내 데이터
	private int myHealth = health;
	
	public EnemyCharacter(Model model, int row, int col) {
		super(row,col,20,20);
		
		this.model = model;	
		path = new ArrayList<Coordinate>();
		updatePath(); // 이 함수를 run()에 넣으면 정상적으로 동작이 안됨
	} 

	public void run() {		
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
	
	public static void resetData() {
		health = 1;
		damage = 1;
		moveOnce = 2;
	}
	
	public int getCurrentHealth() {
		return myHealth;
	}
	
	// ------------- private void isBulletImpacted() ------------//
	// 총알이 해당 적 객체와 부딪혔는지 확인하는 메소드. 
	// iterator를 사용하면 더 안정적으로 해당 Bullet ArrayList에 있는 값을 수정할 수 있다.
	private void isBulletImpacted() {
		Iterator<Bullet> iterator = model.getBullets().iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            if (isImpacted(bullet)) {	
            	System.out.println("적(" + myHealth + ")와 총알(" +model.getMainCharacter().getDamage() + ")");
            	myHealth -= model.getMainCharacter().getDamage();
                model.getBullets().remove(bullet);
                if(myHealth<=0) {
                	model.setRemainEnemyNum(model.getRemainEnemyNum()-1);
                	canRun = false;
                }
                break;
            }         
        }
	}

	
	// -------------- private void moveToUserEntrance() --------------
	// 사용자 공간까지 미로를 따라서 이동하는 함수
	private void moveToUserEntrance() {
		if(!isArrivedUserPlace && path.size()<=1) {
			isArrivedUserPlace = true;
			return;
		}
		moveAlongPath();
		// 먼저 움직이고 총알이 부딪혔는지 확인
		isBulletImpacted();
	}
	// 나중에 리팩토링 해야함
	private void moveAlongPath() {
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
	// view에서 해당 적 캐릭터의 위치를 설정해줘야 함.
	public void updatePath() {
		try {
			path = model.getMaze().getShortestPath(new Coordinate(row / GamePlayPanel.CELL_LENGTH, col / GamePlayPanel.CELL_LENGTH));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// -------- private void traceMainCharacter() --------//
	// 사용자 공간에 적이 들어가면 메인 캐릭터에게 무한정 다가가는 함수
	// 메인 캐릭터와 접촉하면 메인 캐릭터의 피를 깎는 함수
	private void traceMainCharacter() {
		moveToMainCharacter();
    	if(isImpacted(model.getMainCharacter())) {
    		canRun = false;
    		model.getMainCharacter().minusHealth(damage);
    		System.out.println("메인 캐릭터 체력: "+model.getMainCharacter().getHealth());
    		// 적과 사용자가 부딪히면 남아있는 적 개수도 감소시킨다.
    		model.setRemainEnemyNum(model.getRemainEnemyNum()-1);
    		return;
    	}
    	isBulletImpacted();
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

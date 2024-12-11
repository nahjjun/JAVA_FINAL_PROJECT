package Model;

import java.util.Iterator;

import View.GamePlayPanel;

public class Bullet extends MoveObject{
	private Model model;
	private final int direction; // 해당 총알이 부여받은 방향. 값이 -1이면 스레드 종료
	
	// ----- 총알 데이터 ----- // 
	public static int moveOnce = 3;
	
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
            	// 벽의 체력 최신화
            	wall.setHealth(wall.getHealth()-model.getMainCharacter().getDamage());
            	// 만약 벽의 체력이 0 이하가 된 경우
            	if(wall.getHealth() <= 0) {
            		// 해당 벽을 삭제한다.
            		model.getWalls().remove(wall);

            		// MazeMatrix의 row,col
            		int rowM = wall.getRow() / GamePlayPanel.CELL_LENGTH;
        			int colM = wall.getCol() / GamePlayPanel.CELL_LENGTH;
            		// 만약 삭제된 벽이 사용자 공간을 둘러싼 벽이라면
            		if(isBrokenWallNearUserPlace(wall)) {
                		// 해당 벽의 위치 데이터를 maze의 배열에서 설정한다.
                		try {
							model.getMaze().setMazeMatrix(rowM, colM, Maze.USER_ENTRANCE);
						} catch (Exception e) {
							e.printStackTrace();
						}
                		// 사용자 입구를 추가해준다.
                		model.getMaze().addMazeEntrance(rowM, colM);
                		// model의 userEntrance 배열에 해당 위치 추가
                		model.getUserEntrances().add(new UserEntrance(rowM*GamePlayPanel.CELL_LENGTH,colM*GamePlayPanel.CELL_LENGTH)); 
            		}
            		else
	            		// 삭제된 벽이 사용자 공간을 둘러싼 벽이 아니라면, 해당 공간을 path로 설정한다.
	            		model.getMaze().getMazeMatrix()[rowM][colM] = Maze.PATH;
	            		
            		
            		// 맵이 바뀐 이후에, map을 rebuild하고 path를 재설정한다.
            		model.getMaze().buildGraph();
            	}
                break;
            }         
        }
	}
		// 부서진 벽이 사용자 공간 인근에 있는 벽인지 확인하는 함수
		private boolean isBrokenWallNearUserPlace(Wall wall) {
			int row = wall.getRow() / GamePlayPanel.CELL_LENGTH;
			int col = wall.getCol() / GamePlayPanel.CELL_LENGTH;
			if(row >= 6 && row<=13 && col >= 6 && col <= 13)
				return true;
			return false;
		}

	
	
	// ----- setter / getter ----- //	
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

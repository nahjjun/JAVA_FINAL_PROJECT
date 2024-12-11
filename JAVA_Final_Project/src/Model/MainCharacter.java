package Model;

import View.GamePlayPanel;

public class MainCharacter extends MoveObject{
	private Model model;
	private int direction;
	private int prevDirection;
	
	// ------ 캐릭터 데이터 ------ //  
	private int health;
	private int damage;
	private int moveOnce;
	
	
	public MainCharacter(Model model) {
		super(300,300,20,20);
		this.model = model;
		resetData();
		direction = Maze.NORTH;
		prevDirection = Maze.NORTH;
	}
	
	
	public void resetData() {
		health = 10;
		damage = 1;
		moveOnce = 2;
	}
	
	
	// ----- setter/getter ----- // 
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void setDirection(int direction) throws Exception {
		if(direction < 0 || direction >=4) throw new Exception("Model/MainCharacter/setDirection()/잘못된 방향 입력입니다.");
		prevDirection = this.direction;
		this.direction = direction;
	}
	public int getDirection() {
		return direction;
	}
	
	public int getPrevDirection() {
		return prevDirection;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;	
	}
	public int getDamage() {return this.damage;}
	
	public void setHealth(int health) {
		this.health = health;	
	}
	public int getHealth() {return health;}
	public int getMoveOnce() {return moveOnce;}
	
	
	
	
	
	public void minusHealth(int value) {
		health -= value;
	}
	// 한번에 5씩 회복시켜줌 (스킬)
	public void increaseHealth() {
		health += 5;
	}
	public void increaseDamage() {
		damage++;
	}
	public void increaseMoveOnce() {
		moveOnce++;
	}
	
	
	
	// ------ increase/decrease ----- // 
	@Override
	public void increaseRow() {
		// 해당 위치의 maze의 행렬값을 확인했을 때 사용자 공간이면 캐릭터 이동
		if((model.getMaze().getMazeMatrix()[(row+height)/GamePlayPanel.CELL_LENGTH][col/GamePlayPanel.CELL_LENGTH])!=Maze.WALL && (model.getMaze().getMazeMatrix()[(row+height)/GamePlayPanel.CELL_LENGTH][col/GamePlayPanel.CELL_LENGTH])!=Maze.USER_ENTRANCE) {
			row += moveOnce;
			prevRow = row; // 이전 위치 최신화
		}	
	}
	@Override
	public void decreaseRow() {
		if((model.getMaze().getMazeMatrix()[(row-moveOnce)/GamePlayPanel.CELL_LENGTH][col/GamePlayPanel.CELL_LENGTH])!=Maze.WALL && (model.getMaze().getMazeMatrix()[(row-moveOnce)/GamePlayPanel.CELL_LENGTH][col/GamePlayPanel.CELL_LENGTH])!=Maze.USER_ENTRANCE) {
			row -= moveOnce;
			prevRow = row;
		}
	}
	@Override
	public void increaseCol() {
		if((model.getMaze().getMazeMatrix()[row/GamePlayPanel.CELL_LENGTH][(col+width)/GamePlayPanel.CELL_LENGTH])!=Maze.WALL && (model.getMaze().getMazeMatrix()[row/GamePlayPanel.CELL_LENGTH][(col+width)/GamePlayPanel.CELL_LENGTH])!=Maze.USER_ENTRANCE) {
			col += moveOnce;
			prevCol = col;
		}			
	}
	@Override
	public void decreaseCol() {
		if((model.getMaze().getMazeMatrix()[row/GamePlayPanel.CELL_LENGTH][(col-moveOnce)/GamePlayPanel.CELL_LENGTH])!=Maze.WALL && (model.getMaze().getMazeMatrix()[row/GamePlayPanel.CELL_LENGTH][(col-moveOnce)/GamePlayPanel.CELL_LENGTH])!=Maze.USER_ENTRANCE) {
			col -= moveOnce;
			prevCol = col;
		}			
	}	
}

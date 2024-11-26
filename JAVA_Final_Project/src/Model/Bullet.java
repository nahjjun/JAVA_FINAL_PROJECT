package Model;

public class Bullet extends MoveObject{
	private Model model;
	private final int direction; // 해당 총알이 부여받은 방향. 값이 -1이면 스레드 종료
	private volatile boolean canRun = true;
	//// https://velog.io/@jhl221123/%EC%9E%90%EB%B0%94-%EB%A9%80%ED%8B%B0-%EC%8A%A4%EB%A0%88%EB%93%9C%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%99%80-%ED%95%B4%EB%B2%95-%EC%8A%A4%EB%A0%88%EB%93%9C-%EB%8F%99%EA%B8%B0%ED%99%94
	
	// ----- 총알 데이터 ----- // 
	private static int damage = 5;
	private static int moveOnce = 1;
	
	
	public static final int BULLET_SIZE=6;
	
	public Bullet(Model model, int row, int col, int direction) {
		super(row, col, BULLET_SIZE, BULLET_SIZE);
		this.model = model;
		this.direction = direction;
	}
	
	@Override
	public void run() {
		while (canRun) {
	    	int i=0;
	    	if(direction == -1) canRun=false;
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
	        
	        
	        
	        try {
	            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
	        } catch (InterruptedException e) {
	            break; // 스레드 종료
	        }
	    }		
	}


	// ----- setter / getter ----- //
	public void stopRunning() {
		canRun = false;
	}
	
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

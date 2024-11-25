package Model;

public class Bullet extends MoveObject{
	private Model model;
	private int direction; // 해당 총알이 부여받은 방향. 값이 -1이면 스레드 종료
	
	private final int moveOnce = 1;
	public static final int BULLET_SIZE=6;
	
	public Bullet(Model model, int row, int col, int direction) {
		super(row, col, BULLET_SIZE, BULLET_SIZE);
		this.model = model;
		this.direction = direction;
	}
	
	@Override
	public void run() {
		while (true) {
	    	int i=0;
	    	if(direction == -1) break;
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
	        // 1. 움직인 뒤 적과 부딪혔는지 확인
	        for(MoveObject obj:model.getEnemyCharacters()) {
	        	if(isImpacted(obj)) break;
	        }
	        
	        // 2. 움직인 뒤 벽과 부딪혔는지 확인
	        
	        
	        try {
	            Thread.sleep(1000/60); // gamePlayPanel의 타이머와 동일해야함. 그래야 동기화 가능
	        } catch (InterruptedException e) {
	            break; // 스레드 종료
	        }
	    }		
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

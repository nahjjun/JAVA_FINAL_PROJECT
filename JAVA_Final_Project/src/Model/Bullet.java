package Model;

public class Bullet extends MoveObject{
	private Model model;
	private final int moveOnce = 1;
	public static final int BULLET_SIZE=6;
	
	public Bullet(Model model, int row, int col) {
		super(row, col, BULLET_SIZE, BULLET_SIZE);
		this.model = model;
		
	}
	
	@Override
	public void run() {
		
		
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

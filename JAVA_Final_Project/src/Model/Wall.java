package Model;

import java.util.Objects;

public class Wall extends MoveObject{
	private int health;
	
	public Wall(int row, int col) {
		super(row,col,30,30);
		health = 4;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	public int getHealth() {
		return health; 
	}
	
	public void increaseRow() {}
	public void decreaseRow() {}
	public void increaseCol() {}
	public void decreaseCol() {}
	
	
		
}

package Model;

import java.awt.Rectangle;

public abstract class MoveObject {
	protected int row ; 
	protected int col;
	protected int width, height; // 해당 객체의 폭, 높이 
	 
	
	public MoveObject(int row, int col, int width, int height) {
		this.row = row;
		this.col = col;
		this.width = width;
		this.height= height;
	}
	
	// ----------- getter / setter ---------------- //
	public int getRow() { 
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() { 
		return col;
	}
	public void setCol(int col) {
		this.col= col;
	}
	
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height= height;}
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width= width;}
	
	
	// ---- View의 GamePlayPanel의 한번에 캐릭터가 가는 좌표만큼 움직이기게 해주는 함수 ---- //
	public abstract void increaseRow();
	public abstract void decreaseRow();
	public abstract void increaseCol();
	public abstract void decreaseCol();
	
	
	
	
	// ----------- public boolean isImpacted(MoveObject obj) ---------------- //	
	// 각 객체들이 충돌했는지 확인하는 함수. 이 함수가 문제였음
	public boolean isImpacted(MoveObject obj) {
        Rectangle thisRect = new Rectangle(this.col, this.row, this.width, this.height);
        Rectangle objRect = new Rectangle(obj.getCol(), obj.getRow(), obj.getWidth(), obj.getHeight());
        // intersects() 함수는 두 Rectangle 객체가 서로 충돌했는지 확인하는 역할을 한다.
        boolean isImpacted = false;

        // 현재 사각형들이(this,obj) 충돌되지 않았다면
        if (!isImpacted) {
        	// 충돌했는지 확인
            isImpacted = thisRect.intersects(objRect);
        }
        return isImpacted;
	}	
}

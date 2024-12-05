package Model;

import java.awt.Rectangle;

public abstract class MoveObject {
	protected int row ; 
	protected int col;
	protected int prevRow;
	protected int prevCol;
	protected int width, height; // 해당 객체의 폭, 높이 
	 
	
	public MoveObject(int row, int col, int width, int height) {
		this.row = row;
		this.col = col;
		this.prevRow= row;
		this.prevCol = col;
		this.width = width;
		this.height= height;
	}
	
	// ----------- getter / setter ---------------- //
	public int getRow() { 
		return row;
	}
	public void setRow(int row) {
		prevRow = this.row;
		this.row = row;
	}
	public int getCol() { 
		return col;
	}
	public void setCol(int col) {
		prevCol = this.col;
		this.col= col;
	}
	
	public int getPrevRow(){
		return prevRow;
	}
	public int getPrevCol(){
		return prevCol;
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

        // 현재 사각형들이(this,obj) 충돌되지 않았다면, 이전 위치(this의 prev)와 현재 위치(obj) 사이도 검사한다.
        if (!isImpacted) {
            Rectangle thisPrevRect = new Rectangle(this.prevCol, this.prevRow, this.width, this.height);
            // 이전 위치와 부딪혔는지 확인한다.
            isImpacted = thisPrevRect.intersects(objRect);
        }
        return isImpacted;
	}

	
	
	
}

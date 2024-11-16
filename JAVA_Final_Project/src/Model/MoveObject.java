package Model;

import javax.swing.JPanel;

public class MoveObject {
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
	
	
	
	public void increaseRow() {
		
		
	}
	
	
	
	
	// ----------- public boolean isImpacted(MoveObject obj) ---------------- //	
	// 각 객체들이 충돌했는지 확인하는 함수.
	public boolean isImpacted(MoveObject obj) {
		if(((row>= obj.getRow() && row <= obj.getRow()+obj.getHeight()) ||
			(row+height>= obj.getRow() && row+height <= obj.getRow()+obj.getHeight())) &&
			((col>= obj.getCol() && col <= obj.getCol()+obj.getWidth()) ||
			(col+width>= obj.getCol() && col+width<= obj.getCol()+obj.getWidth())))
			return true;
		return false;
	}
	
	
	
}

package Model;

import javax.swing.JPanel;

import View.GamePlayPanel;

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
	
	
	// ---- View의 GamePlayPanel의 한번에 캐릭터가 가는 좌표만큼 움직이기게 해주는 함수 ---- //
	public void increaseRow() {
		prevRow = row;
		if(row+GamePlayPanel.GRID_LENGTH>=GamePlayPanel.MAX_ROW)
			row = GamePlayPanel.MAX_ROW;
		else
			row += GamePlayPanel.GRID_LENGTH;
	}
	public void decreaseRow() {
		prevRow = row;
		if(row-GamePlayPanel.GRID_LENGTH<=0)
			row = 0;
		else
			row -= GamePlayPanel.GRID_LENGTH;
	}
	public void increaseCol() {
		prevCol = col;
		if(col+GamePlayPanel.GRID_LENGTH>=GamePlayPanel.MAX_COL)
			col = GamePlayPanel.MAX_COL;
		else
			col += GamePlayPanel.GRID_LENGTH;
	}
	public void decreaseCol() {
		prevCol = col;
		if(col-GamePlayPanel.GRID_LENGTH<=0)
			col = 0;
		else
			col -= GamePlayPanel.GRID_LENGTH;
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

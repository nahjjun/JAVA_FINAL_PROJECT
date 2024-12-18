package View;

import java.util.Objects;

import javax.swing.JLabel;

import Model.Coordinate;
import Model.Maze;

public class MazeLabel extends JLabel{
	private int row,col;
	private boolean isDragged = false;
	
	public MazeLabel(int row, int col) throws Exception{
		if(row<0 || row>=Maze.ROWS || col<0 || col>=Maze.COLS) 
			throw new Exception("Model/MazeButton/MazeButton()/옳지 않은 범위의 행/열값이 입력되었습니다.");
		this.row = row;
		this.col = col;
	}
	
	// ---------------- getter/setter ---------------- //
	public int getRow() {
		return row;
	}
	public void setRow(int row) throws Exception{
		if(row<0 || row>=Maze.ROWS) 
			throw new Exception("Model/MazeButton/setRow()/옳지 않은 범위의 행값이 입력되었습니다.");
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) throws Exception{
		if(col<0 || col>=Maze.COLS) 
			throw new Exception("Model/MazeButton/setRow()/옳지 않은 범위의 열값이 입력되었습니다.");
		this.col= col;
	}
	
	public boolean getIsDragged() {
		return isDragged;
	}
	public void setIsDragged(boolean value) {
		isDragged = value;
	}
}

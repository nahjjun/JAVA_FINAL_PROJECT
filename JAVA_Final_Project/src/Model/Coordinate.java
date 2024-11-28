package Model;

import java.util.Objects;

public class Coordinate {
	private int row,col;
	private Coordinate prevCoordinate; // 경로 탐색을 위해 각 Coordinate 객체의 이전에 연결된 객체의 주소
	
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
		this.prevCoordinate=null;
	}
	public Coordinate() {
		this(0,0);
	}
	
	public void setRow(int row) throws Exception{
		this.row=row;
	}
	public int getRow() {
		return row;
	}
	public void setCol(int col) throws Exception{
		this.col=col;
	}
	public int getCol() {
		return col;
	}
	
	public void setPrevCoordinate(Coordinate prevCoordinate) throws Exception{
		this.prevCoordinate=prevCoordinate;
	}
	public Coordinate getPrevCoordinate() {
		return this.prevCoordinate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj==null || getClass() != obj.getClass()) return false; // obj가 null이거나 클래스가 같지 않으면 false return
		Coordinate other = (Coordinate) obj;
		return row==other.row && col==other.col;
	}
	@Override
	public int hashCode() {
		return Objects.hash(row, col);
	}
}

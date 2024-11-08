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
		if(row<0 || row>=Maze.ROWS) throw new Exception("Coordinate/setRow()/범위를 벗어난 행 값이 입력되었습니다.");
		this.row=row;
	}
	public int getRow() {
		return row;
	}
	public void setCol(int col) throws Exception{
		if(col<0 || col>=Maze.COLS) throw new Exception("Coordinate/setCol()/범위를 벗어난 열 값이 입력되었습니다.");
		this.col=col;
	}
	public int getCol() {
		return col;
	}
	
	public void setPrevCoordinate(Coordinate prevCoordinate) throws Exception{
		if(prevCoordinate==null) throw new Exception("Coordinate/setPrevCoordinate()/이전 좌표 객체가 null값이 입력되었습니다.");
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

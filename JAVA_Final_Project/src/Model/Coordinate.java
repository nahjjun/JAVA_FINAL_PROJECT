package Model;

public class Coordinate {
	private int row,col;
	private Coordinate prevCoordinate;
	
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
}

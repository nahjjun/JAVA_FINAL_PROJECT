package View;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import Model.Maze;
import Model.Model;

public class GamePlayPanel extends JPanel{
	public static final int GRID_LENGTH = 3; // 캐릭터가 한번에 움직일 수 있는 거리/격자 한 면의 길이
	public static final int MAX_ROW_GRID = 200; // GamePlayPanel에서 격자 기준 최대로
	
	
	private Model model;
	private Timer timer;
	
	private Wall wall;
	
	
	
	
	public GamePlayPanel(Model model) {
		this.model = model;
		setLayout(null);
		
		timer = new Timer(1000/60, new GamePlayActionListener());
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintMaze(g);
	}
	
	
	// ------------------- public void paintMaze() ----------------- // 
	// 미로를 그리는 함수
	public void paintMaze(Graphics g) {
		for(int row=0; row< Maze.ROWS; ++row) {
			for(int col=0; col< Maze.COLS; ++col) {	
				switch(model.getMaze().getMazeMatrix()[row][col]) {
				case Maze.WALL:
					printWall(g, row,col);
					break;
				case Maze.WALL_ENTRANCE:
				case Maze.USER_ENTRANCE:
					printEntrance(g, row,col);
					break;
				case Maze.USER_PLACE:
					printUserPlace(g, row, col);
					break;
				}
			}
		}
		
	}
		// 주어진 행렬(mazeMatrix)의 값을 기준으로 GamePlayPanel에서 위치 특정.
		// 이후에 벽 출력
		private void printWall(Graphics g, int row, int col) {
			int currentRow = row*10*GRID_LENGTH;
			int currentCol = col*10*GRID_LENGTH;
			
			g.setColor(View.WALL_COLOR);
			g.fillRect(currentCol, currentRow, 10*GRID_LENGTH, 10*GRID_LENGTH);
		}
	
		private void printEntrance(Graphics g, int row, int col) {
			int currentRow = row*10*GRID_LENGTH;
			int currentCol = col*10*GRID_LENGTH;
			
			g.setColor(View.ENTRANCE_COLOR);
			g.fillRect(currentCol, currentRow, 10*GRID_LENGTH, 10*GRID_LENGTH);
		}
		private void printUserPlace(Graphics g, int row, int col) {
			int currentRow = row*10*GRID_LENGTH;
			int currentCol = col*10*GRID_LENGTH;
			
			g.setColor(View.USERPLACE_COLOR);
			g.fillRect(currentCol, currentRow, 10*GRID_LENGTH, 10*GRID_LENGTH);
		}
	
	
	
	
	// ----------------GamePlayActionListener--------------------- //
		// GamePlayPanel에서 게임을 진행할때 지정된 타이머동안 수행할 일을
		private class GamePlayActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("타이머 실행중...");
			}
			
		}
}

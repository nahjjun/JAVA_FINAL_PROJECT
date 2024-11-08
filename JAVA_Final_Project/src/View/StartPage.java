package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import Model.Maze;
import Model.Model;

public class StartPage extends JPanel{	
	public static final int MAZE_PANEL_WIDTH = 600;
	public static final int MAZE_PANEL_HEIGHT = 600;
	public static final int USER_INTERFACE_PANEL_WIDTH = 600;
	public static final int USER_INTERFACE_PANEL_HEIGHT = 50;
	
	
	private Model model; // 데이터를 받아올 model 객체
	
	private JPanel mazePanel; // 미로를 둘 패널
	private JPanel userInterfacePanel; // 사용자 인터페이스 패널
	
	private MazeButton[][] mazeButtons; // 사용자가 만들 맵
	private JButton gameStartButton; // 게임 시작 버튼
	private JButton makeAgainButton; // 맵을 다시 만들 버튼
	
	
	public StartPage(Model model) {	
		try {
			this.model = model;
			this.setLayout(new BorderLayout());
			
			// 각 패널들 초기화 및 레이아웃 설정
			mazePanel = new JPanel();
			mazePanel.setPreferredSize(new Dimension(MAZE_PANEL_WIDTH, MAZE_PANEL_HEIGHT));
			mazePanel.setLayout(new GridLayout(20,20,0,0));
			
			userInterfacePanel = new JPanel();
			userInterfacePanel.setPreferredSize(new Dimension(USER_INTERFACE_PANEL_WIDTH, USER_INTERFACE_PANEL_HEIGHT));
			userInterfacePanel.setLayout(new FlowLayout());
			
			// mazePanel에 넣을 JButton들 메모리 할당 및 mazePanel에 add
			mazeButtons = new MazeButton[Maze.ROWS][Maze.COLS];
			for(int row=0; row<Maze.ROWS; ++row) {
				for(int col=0; col<Maze.COLS; ++col) {
					mazeButtons[row][col] = new MazeButton(row,col);
					mazePanel.add(mazeButtons[row][col]);
				}
			}
			// 각 버튼의 색깔 재설정
			updateMazeButtonsColor();
			
			// 사용자 인터페이스 공간에 넣을 데이터들 초기화 및 add
			gameStartButton = new JButton("게임 시작");
			makeAgainButton = new JButton("맵 다시 만들기");
			userInterfacePanel.add(gameStartButton);
			userInterfacePanel.add(makeAgainButton);
			
			// StartPage JPanel에 해당 패널들(mazePanel, userInterfacePanel) 추가
			add(mazePanel, BorderLayout.CENTER);
			add(userInterfacePanel, BorderLayout.SOUTH);
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	// --------------getter/setter--------------//	
	public JButton getGameStartButton() {
		return gameStartButton;
	}
	public JButton getMakeAgainButton() {
		return makeAgainButton;
	}
	public JButton getMazeButton(int row, int col) throws Exception{
		if(row<0 || row>=Maze.ROWS || col<0 || col>=Maze.COLS) throw new Exception("View/StartPage/getMazeButton()/인자로 입력된 행/열 값이 범위를 벗어났습니다.");
		return mazeButtons[row][col];
	}
	
	
	// --------------public void updateMazeButtonsColor()--------------//
	// 미로 버튼의 색깔을 model의 mazeMatrix를 기반으로 최신화 하는 함수
	public void updateMazeButtonsColor() {
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				switch(model.getMaze().getMazeMatrix()[row][col]) {
				case Maze.PATH:
					mazeButtons[row][col].setBackground(View.PATH_COLOR);
					break;
				case Maze.WALL:
					mazeButtons[row][col].setBackground(View.WALL_COLOR);
					break;
				case Maze.USER_PLACE:
					mazeButtons[row][col].setBackground(View.USERPLACE_COLOR);
					break;
				case Maze.USER_ENTRANCE:
				case Maze.WALL_ENTRANCE:
					mazeButtons[row][col].setBackground(View.ENTRANCE_COLOR);
					break;
				}
			}
		}
	}
	
}

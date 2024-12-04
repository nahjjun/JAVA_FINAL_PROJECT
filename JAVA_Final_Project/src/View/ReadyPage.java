package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Maze;
import Model.Model;

public class ReadyPage extends JPanel{		
	private Model model; // 데이터를 받아올 model 객체
	
	// -------- 게임 패널 ---------- // 
	private JPanel gamePanel; // 미로를 둘 패널
	private MazeButton[][] mazeButtons; // 사용자가 만들 맵
	
	// -------- 인터페이스 ---------- // 
	private JPanel userInterfacePanel_NORTH; // 사용자 인터페이스 패널 - 북쪽
	private JPanel userInterfacePanel_SOUTH; // 사용자 인터페이스 패널 - 남쪽
	
	private JButton gameStartButton; // 게임 시작 버튼
	private JButton makeAgainButton; // 맵을 다시 만들 버튼
	private JLabel stageLabel; // 현재 몇 스테이진지 출력할 함수 
	private JLabel remainWall; // 현재 몇개의 벽이 남았는지 출력

	
	
	
	
	public ReadyPage(Model model) {
		this.model = model;
		this.setLayout(new BorderLayout());
		
		// 각 패널들 초기화 및 레이아웃 설정
		gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(View.MAZE_PANEL_WIDTH, View.MAZE_PANEL_HEIGHT));
		gamePanel.setLayout(new GridLayout(20,20,0,0));
		// mazePanel에 넣을 JButton들 메모리 할당 및 mazePanel에 add
		mazeButtons = new MazeButton[Maze.ROWS][Maze.COLS];
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {
				try {
					mazeButtons[row][col] = new MazeButton(row,col);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gamePanel.add(mazeButtons[row][col]);
			}
		}	
		
		
		
		// ------ 남쪽 사용자 인터페이스에 넣을 버튼들 생성 -------- // 
		gameStartButton = new JButton("게임 시작");
		makeAgainButton = new JButton("맵 다시 만들기");
		
		// ------ stageLabel 초기화 ------ // 
		stageLabel = new JLabel("Stage " + model.getCurrentGameStage());
		stageLabel.setSize(100,20);
		stageLabel.setLocation(10,10);
		// ------ remainWallLabel 초기화 ------ // 		
		remainWall = new JLabel("남은 벽의 수 : " + model.getRemainWallNum());
		remainWall.setSize(100,20);
		remainWall.setLocation(500,10);
		
		// ---------- 사용자 인터페이스 - 북쪽 초기화 --------- //
		userInterfacePanel_NORTH = new JPanel();
		userInterfacePanel_NORTH.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
		userInterfacePanel_NORTH.setLayout(null);
		
		userInterfacePanel_NORTH.add(stageLabel);
		userInterfacePanel_NORTH.add(remainWall);
		
		// ---------- 사용자 인터페이스 - 남쪽 초기화 --------- //
		userInterfacePanel_SOUTH = new JPanel();
		userInterfacePanel_SOUTH.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
		userInterfacePanel_SOUTH.setLayout(new FlowLayout());
		
		userInterfacePanel_SOUTH.add(gameStartButton);
		userInterfacePanel_SOUTH.add(makeAgainButton);		
		

		// 각 버튼의 색깔 재설정
		updateMazeButtonsColor();
		

		
		// ReadyPage JPanel에 해당 패널들(mazePanel, userInterfacePanel) 추가
		add(gamePanel, BorderLayout.CENTER);
		add(userInterfacePanel_NORTH, BorderLayout.NORTH);
		add(userInterfacePanel_SOUTH, BorderLayout.SOUTH);
		
		
	}
	
	
	// --------------getter/setter--------------//	
	public JButton getGameStartButton() {
		return gameStartButton;
	}
	public JButton getMakeAgainButton() {
		return makeAgainButton;
	}
	public JButton getMazeButton(int row, int col) throws Exception{
		if(row<0 || row>=Maze.ROWS || col<0 || col>=Maze.COLS) throw new Exception("View/ReadyPage/getMazeButton()/인자로 입력된 행/열 값이 범위를 벗어났습니다.");
		return mazeButtons[row][col];
	}
	
	// 벽의 개수를 최신화함과 동시에, readyPage에 있는 남은 벽의 개수도 갱신
	public void updateWallNum() {
		remainWall.setText("남은 벽의 수 : " + model.getRemainWallNum());
	}
	public void updateCurrentGameStage() {
		stageLabel.setText("Stage" + model.getCurrentGameStage());
	}
	
	
	
	
	// --------------public void updateMazeButtonsColor()--------------//
	// 미로 버튼의 색깔을 model의 mazeMatrix를 기반으로 최신화 하는 함수
	private void updateMazeButtonsColor() {
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
	
	// MakeAgainButton을 눌렀을 때 실행될 함수
	public void remakeMazeButtons() {
		model.getMaze().initMazeMatrix();
		updateMazeButtonsColor();
	}
	
}

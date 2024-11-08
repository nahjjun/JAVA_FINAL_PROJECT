package Controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Maze;
import Model.Model;
import View.MazeButton;
import View.View;

public class Controller {
	private Model model;
	private View view;
	
	public Controller() {
		model = new Model();
		view = new View(model);
		
		try {
			// ---- StartPage class 액션 리스너 할당 ---- //
			view.getStartPage().getGameStartButton().addActionListener(new GameStartButtonActionListener());
			view.getStartPage().getMakeAgainButton().addActionListener(new MakeAgainButtonActionListener());
			for(int row=0; row<Maze.ROWS; ++row) {
				for(int col=0; col<Maze.COLS; ++col) {
					view.getStartPage().getMazeButton(row, col).addActionListener(new MazeButtonListener());
				}
			}	
			
			
			// ---- GamePage class 액션 리스너 할당 ---- //
			
			
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void playGame() {
		view.startGame();
	}
	
	
	

	// ---------------- StartPage 리스너 ---------------- // 
	// GameStartButton이 눌렸을 때 실행될 이벤트 리스너
	private class GameStartButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Container contentPane = view.getContentPane(); // view의 contentPane를 받아서
			// view(JFrame)의 내용을 비우고, 레이아웃 설정 후 게임 페이지로 변환한다.
			contentPane.removeAll();
			contentPane.setLayout(new BorderLayout());
			contentPane.add(view.getGamePage());
			
			// 게임 timer 설정? 해야함. GamePage 클래스에 구현하던지 등
			
		}
	}
	
	// MakeAgainButton이 눌렸을 때 실행될 이벤트 리스너
	private class MakeAgainButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			model.getMaze().initMazeMatrix();
			view.getStartPage().updateMazeButtonsColor();
		}
		
	}
	
	// MazeButton들이 눌렸을 때 실행될 이벤트 리스너
	private class MazeButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				MazeButton pressedButton = (MazeButton)e.getSource();
				// 해당 버튼이 속성이 바뀔 수 있는 버튼이라면, 사용자가 미로를 바꿀 수 있게 설정 및 색깔 변경
				if(model.getMaze().canChangeItCoordinateState(pressedButton.getRow(), pressedButton.getCol())) {
					switch(model.getMaze().getMazeMatrix()[pressedButton.getRow()][pressedButton.getCol()]) {
					case 0:
						model.getMaze().setMazeMatrix(pressedButton.getRow(), pressedButton.getCol(), 1);
						pressedButton.setBackground(View.WALL_COLOR);
						break;
					case 1:
						model.getMaze().setMazeMatrix(pressedButton.getRow(), pressedButton.getCol(), 0);
						pressedButton.setBackground(View.PATH_COLOR);
						break;
					default:
						break;
					}

				}							
			}catch(Exception err) {
				System.err.println(err.getMessage());
			}
		}
	}
	
	
	
	
	// ---------------- GamePage 리스너 ---------------- // 
	
	
	
	
	
	
	
	
}

package Controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Maze;
import Model.Model;
import View.View;

public class Controller {
	private Model model;
	private View view;
	
	public Controller() {
		model = new Model();
		view = new View(model);
		
		try {
			// StartPage class 액션 리스너 할당
			view.getStartPage().getGameStartButton().addActionListener(new GameStartButtonActionListener());
			view.getStartPage().getMakeAgainButton().addActionListener(new MakeAgainButtonActionListener());
			for(int row=0; row<Maze.ROWS; ++row) {
				for(int col=0; col<Maze.COLS; ++col) {
					view.getStartPage().getMazeButton(row, col).addActionListener(new MazeButtonListener());
				}
			}			
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
			
		}
	}
	
	
	
	
	// ---------------- GamePage 리스너 ---------------- // 
	
	
	
	
	
	
	
	
}

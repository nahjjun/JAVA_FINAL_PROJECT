package Controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import Model.MainCharacter;
import Model.Maze;
import Model.Model;
import View.GamePage;
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
			view.setFocusable(true);
			view.addKeyListener(new GamePageKeyListener());
			
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void playGame() {
		view.startGame();
	}
	
	
	

	// ---------------- StartPage 리스너 ---------------- // 
	// GameStartButton이 눌렸을 때 실행될 이벤트 리스너
	private class GameStartButtonActionListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        Container contentPane = view.getContentPane(); // view의 contentPane를 받아서

	        // 기존 내용 제거 및 새 컴포넌트 추가
	        contentPane.removeAll();
	        GamePage gamePage = new GamePage(model);
	        contentPane.setLayout(new BorderLayout());
	        contentPane.add(gamePage, BorderLayout.CENTER);

	        // 새롭게 추가된 패널에 대해 레이아웃을 재정비하고 화면을 다시 그리기
	        contentPane.revalidate(); 
	        contentPane.repaint();    

	        // 게임 시작
	        gamePage.startGame();
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
	public class GamePageKeyListener extends KeyAdapter { 
	    private Timer timer;
	    private String currentDirection = null; // 현재 방향 추적

	    public GamePageKeyListener() {
	        // 타이머 초기화: 하나의 리스너만 등록
	        timer = new Timer(1000 / 60, e -> {
	            MainCharacter mainCharacter = model.getMainCharacter();
	            if (currentDirection != null) {
	                switch (currentDirection) {
	                    case "LEFT":
	                        mainCharacter.decreaseCol();
	                        break;
	                    case "RIGHT":
	                        mainCharacter.increaseCol();
	                        break;
	                    case "UP":
	                        mainCharacter.decreaseRow();
	                        break;
	                    case "DOWN":
	                        mainCharacter.increaseRow();
	                        break;
	                }
	            }
	        });
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	        // 방향 설정
	        switch (e.getKeyCode()) {
	            case KeyEvent.VK_A:
	                currentDirection = "LEFT";
	                break;
	            case KeyEvent.VK_D:
	                currentDirection = "RIGHT";
	                break;
	            case KeyEvent.VK_W:
	                currentDirection = "UP";
	                break;
	            case KeyEvent.VK_S:
	                currentDirection = "DOWN";
	                break;
	        }
	        MainCharacter mainCharacter = model.getMainCharacter();
	        if (!timer.isRunning()) {
	            timer.start(); // 타이머 시작
	            System.out.println((mainCharacter.getRow()) +", " + (mainCharacter.getCol()));
	        }
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        // 방향 제거
	        switch (e.getKeyCode()) {
	            case KeyEvent.VK_A:
	            case KeyEvent.VK_D:
	            case KeyEvent.VK_W:
	            case KeyEvent.VK_S:
	                currentDirection = null;
	                break;
	        }

	        if (currentDirection == null && timer.isRunning()) {
	            timer.stop(); // 타이머 중지
	            System.out.println("key 떼짐");
	        }
	    }
	}

	
	
	
}

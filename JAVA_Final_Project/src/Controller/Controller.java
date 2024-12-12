package Controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import Model.Bullet;
import Model.Coordinate;
import Model.EnemyCharacter;
import Model.MainCharacter;
import Model.Maze;
import Model.Model;
import View.GamePage;
import View.GamePlayPanel;
import View.MazeLabel;
import View.ReadyPage;
import View.View;

public class Controller {
	private Model model;
	private View view;
	private StageController stageController;
	
	public Controller() {
		model = new Model();
		view = new View(model, this);
		stageController = new StageController(model, view);
		
		try {
			// ---- StartPage class 액션 리스너 할당 ---- //
			view.getStartPage().getGoStagePageButton().addActionListener(new GoStagePageButtonActionListener());
			view.getStartPage().getExitButton().addActionListener(new EndGameActionListener());
			
			// ---- ReadyPage class 액션 리스너 할당 ---- //
			view.getReadyPage().getGameStartButton().addActionListener(new GameStartButtonActionListener());
			view.getReadyPage().getMakeAgainButton().addActionListener(new MakeAgainButtonActionListener());
			
			for(int row=0; row<Maze.ROWS; ++row) {
				for(int col=0; col<Maze.COLS; ++col) {
					view.getReadyPage().getMazeLabel(row, col).addMouseListener(new MazeLabelListener());
//					view.getReadyPage().getMazeLabel(row, col).addMouseMotionListener(new MazeLabelDragListener());
				}
			}	
			
			// ---- SelectSkillPage 액션 리스너 할당 ---- //
			view.getSelectSkillPage().getSkillButtons()[0].addActionListener(new SelectSkillButtonActionListener1());
			view.getSelectSkillPage().getSkillButtons()[1].addActionListener(new SelectSkillButtonActionListener2());
			view.getSelectSkillPage().getSkillButtons()[2].addActionListener(new SelectSkillButtonActionListener3());
			
			
			// ---- GamePage class 액션 리스너 할당 ---- //
			view.setFocusable(true);
			view.addKeyListener(new MainCharacterMoveKeyListener());
			
	        view.getEndPage().getRestartGameButton().addActionListener(new GoStagePageButtonActionListener());
	        view.getEndPage().getEndGameButton().addActionListener(new EndGameActionListener());
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void playGame() {
		view.startGame();
	}
	

	public void addTimerActionListener() {
		// ---- GamePlayPanel class 액션 리스너 할당 ---- //
		view.getGamePage().getGamePlayPanel().getGameTimer().addActionListener(new GamePlayActionListener());;
	}
	
	// ---------------- StartPage 리스너 ---------------- //
	private class GoStagePageButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// model의 데이터를 전부 초기화해줌
			model.resetModelData();
			view.getReadyPage().remakeMazeLabels();
			view.getReadyPage().updateWallNum();
			stageController.playStage1();
		}
	}
	private class EndGameActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// 시스템 종료
			System.exit(0);
		}
	}
	
	
	

	// ---------------- ReadyPage 리스너 ---------------- // 
	// GameStartButton이 눌렸을 때 실행될 이벤트 리스너
	private class GameStartButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    Container contentPane = view.getContentPane();
		    model.getMaze().buildGraph(); // 그래프 생성
		    // 만약 게임이 실행될 수 없는 상황이면 다시 맵을 만들도록 설정
		    if(!canPlayGame()) {
		    	// 알림창을 띄울 수 있는 클래스
		    	JOptionPane.showMessageDialog(view.getContentPane(), "길을 완전히 막도록 만들 수는 없습니다!");
		    	model.resetModelData();
				view.getReadyPage().remakeMazeLabels();
				view.getReadyPage().updateWallNum();
				return;
		    }
		    	
		    model.setWalls(); // 벽 객체들 생성. 맨 처음에만 생성한다.
		    model.setUserEntrances();
		    contentPane.removeAll();
		    // 새로운 게임 페이지를 생성.
		    view.resetGamePage();
		    GamePage gamePage = view.getGamePage();
		    contentPane.add(gamePage, BorderLayout.CENTER);
		    contentPane.revalidate();
		    contentPane.repaint();
		    gamePage.startGame();
		}
	}
		// 해당 게임이 실행될 수 있는지 확인하는 함수. 적 캐릭터가 path를 가지고 있지 않으면 false
		private boolean canPlayGame() {
			for(Coordinate entrance : model.getMaze().getMazeEntrance()) {
				try {
					if(model.getMaze().getShortestPath(entrance).isEmpty())
						return false;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return true;
		}
	
	
	
	
	// MakeAgainButton이 눌렸을 때 실행될 이벤트 리스너
	private class MakeAgainButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			model.resetModelData();
			view.getReadyPage().remakeMazeLabels();
			view.getReadyPage().updateWallNum();
		}	
	}
	
	private boolean mousePressed = false;
	
	// MazeButton들이 눌렸을 때 실행될 이벤트 리스너
	private class MazeLabelListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			MazeLabel pressedLabel = (MazeLabel)e.getSource();
			// 눌린 버튼의 상태를 변환
			switchLabelState(pressedLabel);	
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			MazeLabel pressedLabel = (MazeLabel)e.getSource();
			// 마우스가 나가고 나면 drag되었는지 확인하는 변수를 false로 설정
			pressedLabel.setIsDragged(false);
			
		}
	}
	
//	
//	// MazeButton들이 눌렸을 때 실행될 이벤트 리스너
//	private class MazeLabelDragListener extends MouseMotionAdapter{		
//		@Override
//		public void mouseDragged(MouseEvent e) {
//			ReadyPage pagePanel = view.getReadyPage();
//			// 마우스 위치를 기준으로 해당 컴포넌트를 찾기
//			// e.getPoint()는 마우스 이벤트가 발생한 위치를 반환한다.
//			Component component = pagePanel.getComponentAt(e.getPoint());
//			
//			// 만약 마우스 이벤트가 발생한 위치에 있는 컴포넌트가 MazeLabel이라면, 해당 라벨을 얻어온다.
//			if(component instanceof MazeLabel) {
//				MazeLabel draggedLabel = (MazeLabel) component;
//	            if (!draggedLabel.getIsDragged()) {
//	                switchLabelState(draggedLabel);
//	                draggedLabel.setIsDragged(true);
//	            }
//			}
//		}
//		
//		
//	}
	
		// 해당 버튼의 상태를 바꾸는 함수. 인자로 눌려진 버튼을 받음
		private void switchLabelState(MazeLabel pressedButton) {
			// 해당 버튼이 속성이 바뀔 수 있는 버튼이라면, 사용자가 미로를 바꿀 수 있게 설정 및 색깔 변경
			if(model.getMaze().canChangeItCoordinateState(pressedButton.getRow(), pressedButton.getCol())) {
				switch(model.getMaze().getMazeMatrix()[pressedButton.getRow()][pressedButton.getCol()]) {
				case 0:
					// 길을 벽으로 만들 때, 만들 수 있는 벽을 전부 다 썼디면, 경고창을 띄우고 break;
					if(model.getRemainWallNum()<=0) {
						// 알림창을 띄울 수 있는 클래스
				    	JOptionPane.showMessageDialog(view.getContentPane(), "벽을 모두 사용했습니다! 기존에 있는 벽을 지우고 사용할 수 있습니다.");
						break;
					}
					try {
						model.getMaze().setMazeMatrix(pressedButton.getRow(), pressedButton.getCol(), 1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pressedButton.setBackground(View.WALL_COLOR);
					model.setRemainWallNum(model.getRemainWallNum()-1);
					break;
				case 1:
					try {
						model.getMaze().setMazeMatrix(pressedButton.getRow(), pressedButton.getCol(), 0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pressedButton.setBackground(View.PATH_COLOR);
					model.setRemainWallNum(model.getRemainWallNum()+1);
					break;
				default:
					break;
				}
				
				view.getReadyPage().updateWallNum();
			}
		}
	
	
	// ---------------- GamePage 리스너 ---------------- // 
	public class MainCharacterMoveKeyListener extends KeyAdapter { 
	    private Timer timer;
	    private int currentDirection = -1; // 현재 방향 추적

	    public MainCharacterMoveKeyListener() {
	        // 타이머 초기화: 하나의 리스너만 등록
	        timer = new Timer(1000 / 60, e -> {
	            MainCharacter mainCharacter = model.getMainCharacter();
	            switch (currentDirection) {
                case Maze.WEST:
                    mainCharacter.decreaseCol();
                    break;
                case Maze.EAST:
                    mainCharacter.increaseCol();
                    break;
                case Maze.NORTH:
                    mainCharacter.decreaseRow();
                    break;
                case Maze.SOUTH:
                    mainCharacter.increaseRow();
                    break;
            }
	        });
	    }
	    
	    @Override
	    public void keyPressed(KeyEvent e) {
	    	try {
	    		int newDirection = -1;
		        switch (e.getKeyCode()) {
		        	case KeyEvent.VK_LEFT:
		            case KeyEvent.VK_A: newDirection = Maze.WEST; break;
		            case KeyEvent.VK_RIGHT:
		            case KeyEvent.VK_D: newDirection = Maze.EAST; break;
		            case KeyEvent.VK_UP:
		            case KeyEvent.VK_W: newDirection = Maze.NORTH; break;
		            case KeyEvent.VK_DOWN:
		            case KeyEvent.VK_S: newDirection = Maze.SOUTH; break;
		            case KeyEvent.VK_SPACE: 
		            	model.addBullet();
		            	view.getGamePage().updateRemainBulletLabel();
		            	break;
		        }
		        if (newDirection != currentDirection && newDirection != -1) {
		            currentDirection = newDirection;
		            model.getMainCharacter().setDirection(currentDirection);
		            if (!timer.isRunning()) {
		                timer.start();
		            }
		        }	    		
	    	}catch(Exception err) {
	    		System.out.println(err.getMessage());
	    	}
	     }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        if (currentDirection != -1 && (
	            e.getKeyCode() == KeyEvent.VK_A ||
	            e.getKeyCode() == KeyEvent.VK_D ||
	            e.getKeyCode() == KeyEvent.VK_W ||
	            e.getKeyCode() == KeyEvent.VK_S ||
	            e.getKeyCode() == KeyEvent.VK_UP||
	            e.getKeyCode() == KeyEvent.VK_DOWN||
	            e.getKeyCode() == KeyEvent.VK_RIGHT||
	            e.getKeyCode() == KeyEvent.VK_LEFT)) {
	            currentDirection = -1;
	            timer.stop();
	        }
	    }	
	}
	
	
	// ------------ GamePlayPanel 리스너 -------------- //
    // ----------------GamePlayActionListener--------------------- //
    // GamePlayPanel에서 게임을 진행할 때 지정된 타이머 동안 수행할 일을 지정
    private class GamePlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	GamePage gamePage = view.getGamePage();
        	GamePlayPanel gamePlayPanel = gamePage.getGamePlayPanel();
        	
        	// 데이터를 갱신해줌
        	updateData();
        	
        	if(gamePlayPanel.didMainCharacterDie()) {
        		gamePlayPanel.endGamePlay();
        		stageController.endStage();
        		return;
        	}
        	
        	// 사용자가 해당 스테이지를 클리어했을 때, 스킬 선택 페이지로 넘어간다.
        	if(model.getRemainEnemyNum() <= 0) {
        		gamePlayPanel.endGamePlay();
        		// 만약 해당 스테이지가 마지막 스테이지였다면 게임 종료
        		if(model.getCurrentGameStage()== Model.MAX_STAGE) {
        			// 게임 클리어 여부 변수를 true로 설정
        			model.setClearedGame(true);
        			stageController.endStage();
        			return;
        		}
        		// 해당 스테이지가 마지막 스테이지가 아니면 스킬 선택 페이지 출력
        		view.getSelectSkillPage().paintSelectSkillPage();
        		return;
        	}
        	// 적이 아직 전부 죽지 않았다면, 인터페이스 창의 남아있는 적의 개수를 갱신한다. 
        	view.getGamePage().updateRemainEnemyLabel();
        	view.getGamePage().updateRemainUserHealthLabel();
        	
        	gamePlayPanel.revalidate();
        	gamePlayPanel.repaint();
        }
    }   
 // gamePlayPanel에서 timer로 매번 실행시켜 줄 함수
 	private void updateData() {
 		updateEnemyData();
 		updateBulletData();
 	}
 	// 방향에 따라 데이터를 증가시켜줌. 만약 움직인 뒤 벽에 부딪힌 총알이 있으면, 해당 총알 삭제 
 	private void updateBulletData() {
 		model.setCurrentBulletAddTime(model.getCurrentBulletAddTime()+1);
 		// 총알 
 		if(model.getCurrentBulletAddTime() >= model.getBulletAddTime()) {
 			model.increaseBulletNum();		
 			view.getGamePage().updateRemainBulletLabel();
 			model.setCurrentBulletAddTime(0) ;
 		}
 		for(Bullet b:model.getBullets())
 			b.run();
 		model.getBullets().removeIf((b) -> !b.canRun);
 		
 	}
 	private void updateEnemyData() {
 		model.setCurrentEnemyAddTime(model.getCurrentEnemyAddTime()+1);
 		if(model.getCurrentEnemyAddTime() >= model.getEnemyAddTime()) {
 			model.addEnemyCharacter();	
 			model.setCurrentEnemyAddTime(0);
 		}
 		for(EnemyCharacter e:model.getEnemyCharacters()) {
 			e.run();
 		}
 		model.getEnemyCharacters().removeIf((e) -> !e.canRun);	
 	} 	
    
 	
 	// ----------- SelectSkillPage ActionListener ---------- //
 	// 다음 스테이지로 가기 전, 스킬을 선택하는 페이지
 	private class SelectSkillButtonActionListener1 implements ActionListener{
 		@Override
 		public void actionPerformed(ActionEvent e) {
 			// 버튼이 눌리면 해당 이벤트(스킬)을 수행하고
 			view.getSelectSkillPage().doSkillEvent(view.getSelectSkillPage().getRandomSelected()[0]);;
 			// 이후엔 현재 스테이지를 확인하고, 다음 스테이지를 실행한다.
 			switch(model.getCurrentGameStage()) {
 			case 1:
 				// 다음 스테이지 실행
 				stageController.playStage2();
 				break;
 			case 2:
 				stageController.playStage3();
 				break;
 			case 3:
 				stageController.playStage4();
 				break;
 			case 4:
 				stageController.playStage5();
 				break;
 			case 5:
 				stageController.playStage6();
 				break;
 			case 6:
 				stageController.endStage();
 				break;
 			}
 			
 		}
 	}
 	private class SelectSkillButtonActionListener2 implements ActionListener{
 		@Override
 		public void actionPerformed(ActionEvent e) {
 			view.getSelectSkillPage().doSkillEvent(view.getSelectSkillPage().getRandomSelected()[1]);;
 			// 이후엔 현재 스테이지를 확인하고, 다음 스테이지를 실행한다.
 			switch(model.getCurrentGameStage()) {
 			case 1:
 				// 다음 스테이지 실행
 				stageController.playStage2();
 				break;
 			case 2:
 				stageController.playStage3();
 				break;
 			case 3:
 				stageController.playStage4();
 				break;
 			case 4:
 				stageController.playStage5();
 				break;
 			case 5:
 				stageController.playStage6();
 				break;
 			case 6:
 				stageController.endStage();
 				break;
 			}
 		}
 	}
 	private class SelectSkillButtonActionListener3 implements ActionListener{
 		@Override
 		public void actionPerformed(ActionEvent e) {
 			view.getSelectSkillPage().doSkillEvent(view.getSelectSkillPage().getRandomSelected()[2]);;
 			// 이후엔 현재 스테이지를 확인하고, 다음 스테이지를 실행한다.
 			switch(model.getCurrentGameStage()) {
 			case 1:
 				// 다음 스테이지 실행
 				stageController.playStage2();
 				break;
 			case 2:
 				stageController.playStage3();
 				break;
 			case 3:
 				stageController.playStage4();
 				break;
 			case 4:
 				stageController.playStage5();
 				break;
 			case 5:
 				stageController.playStage6();
 				break;
 			case 6:
 				stageController.endStage();
 				break;
 			}
 		}
 	}
}


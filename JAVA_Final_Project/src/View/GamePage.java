package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;
import Model.Model;

public class GamePage extends JPanel {
    private Model model;
    private View view;
    private Controller controller;
    
    private GamePlayPanel gamePlayPanel; // 미로를 둘 패널
    private JPanel userInterfacePanel_NORTH; // 사용자 인터페이스 패널
    
    // 남은 적 캐릭터의 개수를 paint하는 라벨
    private JLabel remainEnemyLabel;
    // 남은 총알 개수를 paint하는 라벨
    private JLabel remainBulletLabel;

    
    public GamePage(Model model, View view, Controller controller) {
        try {
        	this.model = model;
            this.view = view;
            this.controller = controller;
            
            // 게임의 난이도 설정
        	setDifficulty();
        	
            setLayout(new BorderLayout());

            gamePlayPanel = new GamePlayPanel(model, view, controller);
            gamePlayPanel.setPreferredSize(new Dimension(View.MAZE_PANEL_WIDTH, View.MAZE_PANEL_HEIGHT));

            
            // --------- remainEnemyLabel -------- //
            remainEnemyLabel = new JLabel("남은 적의 개수 : " + model.getRemainEnemyNum());
            remainEnemyLabel.setSize(100,20);
            remainEnemyLabel.setLocation(400,5);
    		
            remainBulletLabel = new JLabel("남은 총알 개수 : " + model.getRemainBulletNum());
            remainBulletLabel.setSize(100,20);
            remainBulletLabel.setLocation(100,5);
            
            
            
            // --------- userInterfacePanel_NORTH -------- //
            userInterfacePanel_NORTH = new JPanel();
            userInterfacePanel_NORTH.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
            userInterfacePanel_NORTH.setLayout(null);
            userInterfacePanel_NORTH.add(remainEnemyLabel);
            userInterfacePanel_NORTH.add(remainBulletLabel);
            
            
            // 컴포넌트 추가
            add(gamePlayPanel, BorderLayout.CENTER);
            add(userInterfacePanel_NORTH, BorderLayout.NORTH);
            
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    // 게임 난이도를 설정하는 함수
    private void setDifficulty() {
    	// GamePage는 ReadyPage에서 start 버튼을 누를 때마다 새로 생성되므로, 생성자에서 해당 값(스테이지, 적 개수)을 초기화 시켜준다.
    	int currentStage = model.getCurrentGameStage();
    	model.setBulletAddTime(1000);
    	model.setMaxBulletNum(20);
    	model.setRemainBulletNum(model.getMaxBulletNum());
    	
    	switch(currentStage) {
    	case 1:
    		model.setRemainEnemyNum(50);
    		model.setEnemyAddTime(500);
    		break;
    	case 2:
    		model.setRemainEnemyNum(100);
    		model.setEnemyAddTime(500);
    		break;
    	case 3:
    		model.setRemainEnemyNum(200);
    		model.setEnemyAddTime(250);
    		break;
    	}
    }
    
    
	// --------------getter/setter--------------//
 // 벽의 개수를 최신화함과 동시에, readyPage에 있는 남은 벽의 개수도 갱신
 	public void updateRemainEnemyLabel() {
 		remainEnemyLabel.setText("남은 적의 수 : " + model.getRemainEnemyNum());
 	}
 	public void updateRemainBulletLabel() {
 		remainBulletLabel.setText("남은 총알 수 : " + model.getRemainBulletNum());
 	}
 	
    
    public GamePlayPanel getGamePlayPanel() {
    	return gamePlayPanel;
    }
    
    // 해당 스테이지의 게임을 시작하기 전, 현재 몇 스테이지인지 확인한 후에 gamePlayPanel의 게임을 실행시킨다.
    public void startGame() {
        gamePlayPanel.startGamePlay();
        gamePlayPanel.repaint(); // 게임 시작 시 화면 다시 그리기
        // 레이아웃을 재정비하고 화면을 다시 그리기
        revalidate();
        repaint();
    }

    
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

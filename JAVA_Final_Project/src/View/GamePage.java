package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Controller;
import Model.EnemyCharacter;
import Model.Model;

public class GamePage extends JPanel {
    private Model model;
    private View view;
    private Controller controller;
    
    private GamePlayPanel gamePlayPanel; // 미로를 둘 패널
    private JPanel userInterfacePanel_NORTH; // 사용자 인터페이스 패널
    private JPanel userInterfacePanel_SOUTH;
    
    // 남은 적 캐릭터의 개수를 paint하는 라벨
    private JLabel remainEnemyLabel;
    // 남은 총알 개수를 paint하는 라벨
    private JLabel remainBulletLabel;
    // 남은 캐릭터의 체력을 paint하는 라벨
    private JLabel remainUserHealthLabel;
    
    // 캐릭터의 공격력을 출력하는 라벨
    private JLabel userDamageLabel;
    // 캐릭터의 이동속도를 출력하는 라벨
    private JLabel userMoveOnceLabel;
    
    
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
            remainBulletLabel.setLocation(200,5);
            
            remainUserHealthLabel = new JLabel("체력 : " + model.getMainCharacter().getHealth());
            remainUserHealthLabel.setSize(100,20);
            remainUserHealthLabel.setLocation(100,5);
            
            
            
            userDamageLabel = new JLabel("공격력 : " + model.getMainCharacter().getDamage());
            userDamageLabel.setSize(100,20);
            userDamageLabel.setLocation(100,5);
            
            userMoveOnceLabel = new JLabel("이동속도 : " + model.getMainCharacter().getMoveOnce());
            userMoveOnceLabel.setSize(100,20);
            userMoveOnceLabel.setLocation(200,5);
            
            
            
            // --------- userInterfacePanel_NORTH -------- //
            // --------- userInterfacePanel_SOUTH-------- //
            userInterfacePanel_NORTH = new JPanel();
            userInterfacePanel_NORTH.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
            userInterfacePanel_NORTH.setLayout(null);
            userInterfacePanel_NORTH.add(remainEnemyLabel);
            userInterfacePanel_NORTH.add(remainBulletLabel);
            userInterfacePanel_NORTH.add(remainUserHealthLabel);
            
            userInterfacePanel_SOUTH = new JPanel();
            userInterfacePanel_SOUTH.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
            userInterfacePanel_SOUTH.setLayout(null);
            userInterfacePanel_SOUTH.add(userDamageLabel);
            userInterfacePanel_SOUTH.add(userMoveOnceLabel);
            
            
            // 컴포넌트 추가
            add(gamePlayPanel, BorderLayout.CENTER);
            add(userInterfacePanel_NORTH, BorderLayout.NORTH);
            add(userInterfacePanel_SOUTH, BorderLayout.SOUTH);
            
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    // 게임 난이도를 설정하는 함수
    private void setDifficulty() {
    	// GamePage는 ReadyPage에서 start 버튼을 누를 때마다 새로 생성되므로, 생성자에서 해당 값(스테이지, 적 개수)을 초기화 시켜준다.
    	int currentStage = model.getCurrentGameStage();
    	// 증가시킬 time 변수 0으로 초기화
    	model.setCurrentBulletAddTime(0);
    	model.setCurrentEnemyAddTime(0);
    	
    	
    	model.setBulletAddTime(model.getBulletAddTime()); 
    	model.setMaxBulletNum(model.getMaxBulletNum());
    	model.setRemainBulletNum(model.getMaxBulletNum());
    	
    	
    	switch(currentStage) {
    	case 1:
    		model.setMaxEnemyNum(30);
    		// 0.5초마다 적 추가
    		model.setEnemyAddTime(30); 
    		break;
    	case 2:
    		model.setMaxEnemyNum(40);
    		model.setEnemyAddTime(30);
    		EnemyCharacter.damage = 2;
    		EnemyCharacter.health = 2;
    		break;
    	case 3:
    		model.setMaxEnemyNum(50);
    		model.setEnemyAddTime(30);
    		EnemyCharacter.damage = 2;
    		EnemyCharacter.health = 3;
    		break;
    	case 4:
    		model.setMaxEnemyNum(60);
    		model.setEnemyAddTime(20);
    		EnemyCharacter.damage = 2;
    		EnemyCharacter.health = 3;
    		break;
    	case 5:
    		model.setMaxEnemyNum(70);
    		model.setEnemyAddTime(20);
    		EnemyCharacter.damage = 3;
    		EnemyCharacter.health = 3;
    		break;
    	case 6:
    		model.setMaxEnemyNum(80);
    		model.setEnemyAddTime(20);
    		EnemyCharacter.damage = 3;
    		EnemyCharacter.health = 4;
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
 	public void updateRemainUserHealthLabel() {
 		remainUserHealthLabel.setText("체력 : " + model.getMainCharacter().getHealth());
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

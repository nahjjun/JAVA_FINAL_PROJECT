package View;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import Controller.Controller;
import Model.Bullet;
import Model.EnemyCharacter;
import Model.MainCharacter;
import Model.Maze;
import Model.Model;

public class GamePlayPanel extends JPanel {
    public static final int MAX_ROW = 600; // GamePlayPanel에서 격자 기준 최대로
    public static final int MAX_COL = 600; // GamePlayPanel에서 격자 기준 최대로
    public static final int CELL_LENGTH = MAX_ROW/20; // 격자 한 면의 길이
    
    private Model model;
    private View view;
    private Controller controller;	
    
    private Timer timer_game;
    // 적이 추가되는 타이머  
    private Timer timer_enemy;
    // 총알이 일정 시간마다 추가되는 타이머
    private Timer timer_addBullet;
    
    
    public GamePlayPanel(Model model, View view, Controller controller) {
        this.model = model;
        this.view = view;
        this.controller= controller;
        
        setLayout(null);
        
        timer_game = new Timer(1000/60, null);
        timer_enemy = new Timer(model.getEnemyAddTime(), null);
        timer_addBullet = new Timer(model.getBulletAddTime(), null);
        model.addEnemyCharacter();
    }
    
    
    // ------------ setter / getter ------------------- //
    
    
    public Timer getGameTimer() {
    	return timer_game;
    }
    public Timer getEnemyTimer() {
    	return timer_enemy;
    }
    public Timer getBulletTimer() {
    	return timer_addBullet;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintMaze(g); // paintMaze()를 호출하여 미로를 그립니다.
        paintMainCharacter(g);
        paintEnemyCharacter(g);
        paintBullet(g);
    }

    
    // --------------public void paintMaze(Graphics g) -------//
    // 미로를 그리는 함수
    private void paintMaze(Graphics g) {
        for (int row = 0; row < Maze.ROWS; ++row) {
            for (int col = 0; col < Maze.COLS; ++col) {
                switch (model.getMaze().getMazeMatrix()[row][col]) {
                    case Maze.WALL:
                    	paintWall(g, row, col);
                        break;
                    case Maze.WALL_ENTRANCE:
                    case Maze.USER_ENTRANCE:
                    	paintEntrance(g, row, col);
                        break;
                    case Maze.USER_PLACE:
                    	paintUserPlace(g, row, col);
                        break;
                }
            }
        }
    }

	    // 벽을 그리기
	    private void paintWall(Graphics g, int row, int col) {
	        int currentRow = row * CELL_LENGTH; 
	        int currentCol = col * CELL_LENGTH;
	
	        g.setColor(View.WALL_COLOR);
	        g.fillRect(currentCol, currentRow, CELL_LENGTH, CELL_LENGTH);
	    }
	
	    // 출입구를 그리기
	    private void paintEntrance(Graphics g, int row, int col) {
	        int currentRow = row * CELL_LENGTH;
	        int currentCol = col * CELL_LENGTH;
	
	        g.setColor(View.ENTRANCE_COLOR);
	        g.fillRect(currentCol, currentRow, CELL_LENGTH, CELL_LENGTH);
	    }
	
	    // 사용자의 위치를 그리기
	    private void paintUserPlace(Graphics g, int row, int col) {
	        int currentRow = row * CELL_LENGTH;
	        int currentCol = col * CELL_LENGTH;
	
	        g.setColor(View.USERPLACE_COLOR);
	        g.fillRect(currentCol, currentRow, CELL_LENGTH, CELL_LENGTH);
	    }

    
	    
    // ---------- public void paintMainCharacter()-------- //
    // 메인 캐릭터 출력 함수
    private void paintMainCharacter(Graphics g) {
    	MainCharacter mainCharacter = model.getMainCharacter();
    	// 이전 캐릭터 삭제
    	g.setColor(View.USERPLACE_COLOR);
    	g.fillRect(mainCharacter.getPrevCol(), mainCharacter.getPrevRow(), mainCharacter.getWidth(), mainCharacter.getHeight());
    	
    	g.setColor(View.USERPLACE_COLOR);
    	switch(mainCharacter.getPrevDirection()) {
    	case Maze.NORTH:
    		g.fillRect(mainCharacter.getCol()+5, mainCharacter.getRow()-5, 10, 5);
    		break;
    	case Maze.SOUTH:
    		g.fillRect(mainCharacter.getCol()+5, mainCharacter.getRow(), 10, 5);
    		break;
    	case Maze.WEST:
    		g.fillRect(mainCharacter.getCol()-5, mainCharacter.getRow()+5, 5, 10);
    		break;
    	case Maze.EAST:
    		g.fillRect(mainCharacter.getCol(), mainCharacter.getRow()+5, 5, 10);
    		break;
    	}
    	
    	// 현재 캐릭터 출력
    	g.setColor(View.MAINCHARACTER_COLOR);
    	g.fillRect(mainCharacter.getCol(), mainCharacter.getRow(), mainCharacter.getWidth(), mainCharacter.getHeight());
    	
    	g.setColor(View.MAINCHARACTER_COLOR);
    	switch(mainCharacter.getDirection()) {
    	case Maze.NORTH:
    		g.fillRect(mainCharacter.getCol()+5, mainCharacter.getRow()-5, 10, 5);
    		break;
    	case Maze.SOUTH:
    		g.fillRect(mainCharacter.getCol()+5, mainCharacter.getRow()+mainCharacter.getHeight(), 10, 5);
    		break;
    	case Maze.WEST:
    		g.fillRect(mainCharacter.getCol()-5, mainCharacter.getRow()+5, 5, 10);
    		break;
    	case Maze.EAST:
    		g.fillRect(mainCharacter.getCol()+mainCharacter.getWidth(), mainCharacter.getRow()+5, 5, 10);
    		break;
    	}
    }
	    
	
    // ---------- public void paintEnemyCharacter()-------- //
    // 적 캐릭터 출력 함수
    private void paintEnemyCharacter(Graphics g) {
    	ArrayList<EnemyCharacter> enemyCharacters = model.getEnemyCharacters();
    	for (EnemyCharacter enemy : enemyCharacters) {
    	    g.setColor(View.PATH_COLOR);
    	    g.fillRect(enemy.getPrevCol(), enemy.getPrevRow(), enemy.getWidth(), enemy.getHeight());
    	    g.setColor(View.ENEMYCHARACTER_COLOR);
    	    g.fillRect(enemy.getCol(), enemy.getRow(), enemy.getWidth(), enemy.getHeight());
    	}    	
    }    
    
    // ---------- public void paintBullet()-------- //
    // 총알 출력 함수
    private void paintBullet(Graphics g) {
    	ArrayList<Bullet> bullets = model.getBullets();
    	for (Bullet bullet : bullets) {
    	    g.setColor(View.PATH_COLOR);
    	    g.fillRect(bullet.getPrevCol(), bullet.getPrevRow(), bullet.getWidth(), bullet.getHeight());
    	    g.setColor(View.BULLET_COLOR);
    	    g.fillRect(bullet.getCol(), bullet.getRow(), bullet.getWidth(), bullet.getHeight());
    	} 	
    }
    
    
    
    // -------------startGamePlay(), endGamePlay() --------------//
    // 게임 시작
    public void startGamePlay() {
    	// 타이머 액션 리스너 추가
    	controller.addTimerActionListener();
    	timer_game.start();
        timer_enemy.start();
        timer_addBullet.start();
    }

    // 게임 종료
    public void endGamePlay() {
    	timer_game.stop();
    	timer_enemy.stop();
    	timer_addBullet.stop();
    }

    
    // ------------- MoveObject 객체들의 스레드 상태 확인 -------------- //
    // 종료된 스레드는 배열에서 제거함으로써 그려지지 않게 한다.
    public void updateThreadState() {
    	ArrayList<EnemyCharacter> enemyCharacters = model.getEnemyCharacters();
    	ArrayList<Bullet> bullets = model.getBullets();
    	enemyCharacters.removeIf(enemy -> !enemy.isAlive() || !enemy.canRun);
    	bullets.removeIf(bullet -> !bullet.isAlive() || !bullet.canRun);
    }
    
    // ------------ MainCharacter의 체력 확인 --------------- //
    // 메인 캐릭터가 죽으면 true 반환
    public boolean didMainCharacterDie() {
    	if(model.getMainCharacter().getHealth() <= 0) {
    		return true;
    	}
    	return false;
    }
    
}

package View;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.Timer;

import Model.Bullet;
import Model.EnemyCharacter;
import Model.MainCharacter;
import Model.Maze;
import Model.Model;

public class GamePlayPanel extends JPanel {
    public static final int GRID_LENGTH = 3; // 캐릭터가 한 번에 움직일 수 있는 거리/격자 한 면의 길이
    public static final int MAX_ROW = 600; // GamePlayPanel에서 격자 기준 최대로
    public static final int MAX_COL = 600; // GamePlayPanel에서 격자 기준 최대로

    private Model model;
    private Timer timer_game;
    
    // ------ 적이 추가되는 타이머 ------ //  
    private Timer timer_enemy;
    private int enemyAddTime;
    
    
    
    public GamePlayPanel(Model model) {
        this.model = model;
        setLayout(null);

        enemyAddTime = 3000;
        timer_game = new Timer(1000 / 60, new GamePlayActionListener());
        timer_enemy = new Timer(enemyAddTime, new EnemyAddActionListener());
        
        timer_game.start();
        timer_enemy.start();
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
	    	int cellLength = 10 * GRID_LENGTH;
	        int currentRow = row * cellLength; 
	        int currentCol = col * cellLength;
	
	        g.setColor(View.WALL_COLOR);
	        g.fillRect(currentCol, currentRow, cellLength, cellLength);
	    }
	
	    // 출입구를 그리기
	    private void paintEntrance(Graphics g, int row, int col) {
	        int currentRow = row * 10 * GRID_LENGTH;
	        int currentCol = col * 10 * GRID_LENGTH;
	
	        g.setColor(View.ENTRANCE_COLOR);
	        g.fillRect(currentCol, currentRow, 10 * GRID_LENGTH, 10 * GRID_LENGTH);
	    }
	
	    // 사용자의 위치를 그리기
	    private void paintUserPlace(Graphics g, int row, int col) {
	        int currentRow = row * 10 * GRID_LENGTH;
	        int currentCol = col * 10 * GRID_LENGTH;
	
	        g.setColor(View.USERPLACE_COLOR);
	        g.fillRect(currentCol, currentRow, 10 * GRID_LENGTH, 10 * GRID_LENGTH);
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
        if (!timer_game.isRunning()) {
        	timer_game.start();
        }
    }

    // 게임 종료
    public void endGamePlay() {
        if (timer_game.isRunning()) {
        	timer_game.stop();
        }
    }

    
    // ------------- MoveObject 객체들의 스레드 상태 확인 -------------- //
    // 종료된 스레드는 배열에서 제거함으로써 그려지지 않게 한다.
    private void updateThreadState() {
    	ArrayList<EnemyCharacter> enemyCharacters = model.getEnemyCharacters();
    	enemyCharacters.removeIf(enemy -> !enemy.isAlive());
    }
    
    
    // ----------------GamePlayActionListener--------------------- //
    // GamePlayPanel에서 게임을 진행할 때 지정된 타이머 동안 수행할 일을 지정
    private class GamePlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	updateThreadState();
        	repaint();
        }
    }
    
    
    private class EnemyAddActionListener implements ActionListener{
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		model.addEnemyCharacter();
    		--enemyAddTime;    		
    	}
    }
}

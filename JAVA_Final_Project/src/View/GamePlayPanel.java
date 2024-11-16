package View;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import Model.MainCharacter;
import Model.Maze;
import Model.Model;

public class GamePlayPanel extends JPanel {
    public static final int GRID_LENGTH = 3; // 캐릭터가 한 번에 움직일 수 있는 거리/격자 한 면의 길이
    public static final int MAX_ROW_GRID = 200; // GamePlayPanel에서 격자 기준 최대로

    private Model model;
    private Timer timer;
    private Graphics g;  
    
    
    
    public GamePlayPanel(Model model) {
        this.model = model;
        setLayout(null);

        timer = new Timer(1000 / 60, new GamePlayActionListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        paintMaze(); // paintMaze()를 호출하여 미로를 그립니다.
        paintMainCharacter();
    }

    
    // --------------public void paintMaze(Graphics g) -------//
    // 미로를 그리는 함수
    public void paintMaze() {
        for (int row = 0; row < Maze.ROWS; ++row) {
            for (int col = 0; col < Maze.COLS; ++col) {
                switch (model.getMaze().getMazeMatrix()[row][col]) {
                    case Maze.WALL:
                    	paintWall(row, col);
                        break;
                    case Maze.WALL_ENTRANCE:
                    case Maze.USER_ENTRANCE:
                    	paintEntrance(row, col);
                        break;
                    case Maze.USER_PLACE:
                    	paintUserPlace(row, col);
                        break;
                }
            }
        }
    }

	    // 벽을 그리기
	    private void paintWall(int row, int col) {
	    	int cellLength = 10 * GRID_LENGTH;
	        int currentRow = row * cellLength; 
	        int currentCol = col * cellLength;
	
	        g.setColor(View.WALL_COLOR);
	        g.fillRect(currentCol, currentRow, cellLength, cellLength);
	    }
	
	    // 출입구를 그리기
	    private void paintEntrance(int row, int col) {
	        int currentRow = row * 10 * GRID_LENGTH;
	        int currentCol = col * 10 * GRID_LENGTH;
	
	        g.setColor(View.ENTRANCE_COLOR);
	        g.fillRect(currentCol, currentRow, 10 * GRID_LENGTH, 10 * GRID_LENGTH);
	    }
	
	    // 사용자의 위치를 그리기
	    private void paintUserPlace(int row, int col) {
	        int currentRow = row * 10 * GRID_LENGTH;
	        int currentCol = col * 10 * GRID_LENGTH;
	
	        g.setColor(View.USERPLACE_COLOR);
	        g.fillRect(currentCol, currentRow, 10 * GRID_LENGTH, 10 * GRID_LENGTH);
	    }

    
	    
    // ---------- public void paintMainCharacter()-------- //
    // 메인 캐릭터 출력 함수
    public void paintMainCharacter() {
    	MainCharacter mainCharacter = model.getMainCharacter();
    	g.setColor(View.USERPLACE_COLOR);
    	g.fillRect(mainCharacter.getPrevCol(), mainCharacter.getPrevRow(), mainCharacter.getWidth(), mainCharacter.getHeight());
    	
    	g.setColor(View.MAINCHARACTER_COLOR);
    	g.fillRect(mainCharacter.getCol(), mainCharacter.getRow(), mainCharacter.getWidth(), mainCharacter.getHeight());
    	
    	System.out.println("메인 캐릭터 출력");
    }
	    
	    
    // -------------startGamePlay(), endGamePlay() --------------//
    // 게임 시작
    public void startGamePlay() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    // 게임 종료
    public void endGamePlay() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    // ----------------GamePlayActionListener--------------------- //
    // GamePlayPanel에서 게임을 진행할 때 지정된 타이머 동안 수행할 일을 지정
    private class GamePlayActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	paintMainCharacter(); 
        }
    }
}

package Model;

import java.util.ArrayList;

import View.GamePlayPanel;

public class Model {
	private Maze maze;
	private MainCharacter mainCharacter;
	private ArrayList<EnemyCharacter> enemyCharacters;
	
	
	
	
	public Model() {
		maze = new Maze();
		mainCharacter = new MainCharacter(this);
		enemyCharacters = new ArrayList<EnemyCharacter>();
	}          
	
	
	public ArrayList<EnemyCharacter> getEnemyCharacters(){
		return enemyCharacters;
	}
	
	public EnemyCharacter getEnemyCharacter(int index) throws Exception{
		if(index >= enemyCharacters.size()) throw new Exception("Model/getEnemyCharacter()/범위를 벗어난 인덱스 접근입니다");
		return enemyCharacters.get(index);
	}
	
	// 일정 시간마다 적을 추가하는 함수. 랜덤으로 생성 위치를 정해야한다.
	public void addEnemyCharacter(){
		int direction = (int)(Math.random()*4);
		int row=0, col=0;
		int cellLength = 10 * GamePlayPanel.GRID_LENGTH;
		
		switch(direction) {
		case Maze.NORTH:
			row = 0;
			col = 9 * cellLength;
			break;
		case Maze.SOUTH:
			row = 19 * cellLength;
			col = 9 * cellLength;
			break;
		case Maze.WEST:
			row = 9 * cellLength;
			col = 0;
			break;
		case Maze.EAST:
			row = 9 * cellLength;
			col = 19 * cellLength;
			break;
		}
		EnemyCharacter enemy = new EnemyCharacter(this, row, col);
		enemy.start();
		enemyCharacters.add(enemy);
	}
	public void deleteEnemyCharacter(int index) throws Exception{
		if(index >= enemyCharacters.size()) throw new Exception("Model/deleteEnemyCharacter()/범위를 벗어난 인덱스 접근입니다");
		enemyCharacters.remove(index);
	}
	
	
	public Maze getMaze() {
		return maze;
	}
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}
}

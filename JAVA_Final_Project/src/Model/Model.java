package Model;

import java.util.ArrayList;

import View.GamePlayPanel;

public class Model {
	private Maze maze;
	private MainCharacter mainCharacter;
	private ArrayList<EnemyCharacter> enemyCharacters;
	private ArrayList<Bullet> bullets;
	
	
	
	public Model() {
		maze = new Maze();
		mainCharacter = new MainCharacter(this);
		enemyCharacters = new ArrayList<EnemyCharacter>();
		bullets = new ArrayList<Bullet>();
	}          
	
	
	// -------------- Enemy Character ----------------- //
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
		int middle = 5; // 캐릭터를 길 및 셀 중간에 나올 수 있게 위치 지정해줄 변수
		
		switch(direction) {
		case Maze.NORTH:
			row = middle;
			col = 9 * cellLength+middle;
			break;
		case Maze.SOUTH:
			row = 19 * cellLength+middle;
			col = 9 * cellLength+middle;
			break;
		case Maze.WEST:
			row = 9 * cellLength+middle;
			col = middle;
			break;
		case Maze.EAST:
			row = 9 * cellLength+middle;
			col = 19 * cellLength+middle;
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
	
	
	// -------------- Bullet ----------------- //
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public void addBullet() {
		int direction = mainCharacter.getDirection();
		int currentRow=mainCharacter.getRow(), currentCol=mainCharacter.getCol();
		Bullet bullet = null;
		switch(direction) {
		case Maze.NORTH:
			bullet = new Bullet(this, currentRow-Bullet.BULLET_SIZE-5, currentCol+(10-Bullet.BULLET_SIZE/2));
			break;
		case Maze.SOUTH:
			bullet = new Bullet(this, currentRow+mainCharacter.getHeight()+5, currentCol+(10-Bullet.BULLET_SIZE/2));
			break;
		case Maze.WEST:
			bullet = new Bullet(this, currentRow+(10-Bullet.BULLET_SIZE/2), currentCol-5-Bullet.BULLET_SIZE);
			break;
		case Maze.EAST:
			bullet = new Bullet(this, currentRow+(10-Bullet.BULLET_SIZE/2), currentCol+mainCharacter.getWidth()+5);
			break;
		}
		bullet.start();
		bullets.add(bullet);
	}
	public void deleteBullet(int index) throws Exception{
		if(index >= bullets.size()) throw new Exception("Model/deleteBullet()/범위를 벗어난 인덱스 접근입니다");
		bullets.remove(index);
	}
	
	
	
	// -------------- Maze ----------------- //
	public Maze getMaze() {
		return maze;
	}
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}
}

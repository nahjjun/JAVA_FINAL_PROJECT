package Model;

import java.util.ArrayList;

import View.GamePlayPanel;

public class Model {
	private Maze maze;
	private MainCharacter mainCharacter;
	private ArrayList<EnemyCharacter> enemyCharacters;
	private ArrayList<Bullet> bullets;
	private ArrayList<Wall> walls;
	// 현재 게임 스테이지 번호를 갖고 있음
	private int currentGameStage;
    // 남은 적 캐릭터의 개수를 담은 데이터
    private int remainEnemyNum;
	// 사용자가 만들 수 있는 벽의 개수
	private int remainWallNum;
	// 사용자가 사용할 수 있는 총알의 개수. 이 총알은 충전형이다.
	private int remainBulletNum;
	// 총알의 최대 개수
	private int maxBulletNum;

	
	// 적이 추가되는 시간
	private int enemyAddTime;
	// 총알이 충전되는 시간
	private int bulletAddTime;
	
	public Model() {
		maze = new Maze();
		mainCharacter = new MainCharacter(this);
		enemyCharacters = new ArrayList<EnemyCharacter>();
		bullets = new ArrayList<Bullet>();
		walls = new ArrayList<Wall>();
		
		currentGameStage = 0;
		remainEnemyNum = 0;
		remainWallNum = 0;
		remainBulletNum = 0;
		maxBulletNum = 0;
		
		enemyAddTime = 0;
		bulletAddTime = 0;

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
		synchronized (enemyCharacters) {
			int direction = (int)(Math.random()*4);
			int row=0, col=0;
			int middle = 0; // 캐릭터를 길 및 셀 중간에 나올 수 있게 위치 지정해줄 변수
			
			switch(direction) {
			case Maze.NORTH:
				row = middle;
				col = 9 * GamePlayPanel.CELL_LENGTH+middle;
				break;
			case Maze.SOUTH:
				row = 19 * GamePlayPanel.CELL_LENGTH+middle;
				col = 9 * GamePlayPanel.CELL_LENGTH+middle;
				break;
			case Maze.WEST:
				row = 9 * GamePlayPanel.CELL_LENGTH+middle;
				col = middle;
				break;
			case Maze.EAST:
				row = 9 * GamePlayPanel.CELL_LENGTH+middle;
				col = 19 * GamePlayPanel.CELL_LENGTH+middle;
				break;
			}
			EnemyCharacter enemy = new EnemyCharacter(this, row, col);
			enemy.start();
			enemyCharacters.add(enemy);
		}
	}
	public void deleteEnemyCharacter(int index) throws Exception{
		if(index >= enemyCharacters.size()) throw new Exception("Model/deleteEnemyCharacter()/범위를 벗어난 인덱스 접근입니다");
		enemyCharacters.remove(index);
	}
	
	public int getCurrentGameStage() {
		return currentGameStage;
	}
	public void setCurrentGameStage(int currentGameStage) {
		this.currentGameStage = currentGameStage;
	}
	public int getRemainEnemyNum() {
		return remainEnemyNum;
	}
	public void setRemainEnemyNum(int remainEnemyNum) {
		this.remainEnemyNum = remainEnemyNum;
	}
	public int getRemainWallNum() {
		return remainWallNum;
	}
	public void setRemainWallNum(int remainWallNum) {
		this.remainWallNum = remainWallNum;
	}
	public void setRemainBulletNum(int bullet) {
		remainBulletNum = bullet;
	}
	public int getRemainBulletNum() {
		return remainBulletNum;
	}
	public void setMaxBulletNum(int bullet) {
		maxBulletNum = bullet;
	}
	public int getMaxBulletNum() {
		return maxBulletNum;
	}
	
	// ---------- 타이머 ------------ //
	public void setEnemyAddTime(int millisecond) {
		enemyAddTime = millisecond;
	}
	public int getEnemyAddTime() {
		return enemyAddTime;
	}
	public void setBulletAddTime(int millisecond) {
		bulletAddTime = millisecond;
	}
	public int getBulletAddTime() {
		return bulletAddTime;
	}
	
	// -------------- Bullet ----------------- //
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public void addBullet() {
		// 남아있는 총알이 없으면 함수 종료
		decreaseBulletNum();
		if(remainBulletNum<=0)
			return;
		synchronized (bullets) {
			int direction = mainCharacter.getDirection();
			int currentRow = mainCharacter.getRow(), currentCol=mainCharacter.getCol();
			Bullet bullet = null;
			switch(direction) {
			case Maze.NORTH:
				bullet = new Bullet(this, currentRow-Bullet.BULLET_SIZE-5, currentCol+(10-Bullet.BULLET_SIZE/2), Maze.NORTH);
				break;
			case Maze.SOUTH:
				bullet = new Bullet(this, currentRow+mainCharacter.getHeight()+5, currentCol+(10-Bullet.BULLET_SIZE/2), Maze.SOUTH);
				break;
			case Maze.WEST:
				bullet = new Bullet(this, currentRow+(10-Bullet.BULLET_SIZE/2), currentCol-5-Bullet.BULLET_SIZE, Maze.WEST);
				break;
			case Maze.EAST:
				bullet = new Bullet(this, currentRow+(10-Bullet.BULLET_SIZE/2), currentCol+mainCharacter.getWidth()+5, Maze.EAST);
				break;
			}
			bullet.start();
			bullets.add(bullet);	
		}
	}
	public void deleteBullet(int index) throws Exception{
		if(index >= bullets.size()) throw new Exception("Model/deleteBullet()/범위를 벗어난 인덱스 접근입니다");
		bullets.remove(index);
		
	}
	
	public void increaseBulletNum() {
		if(maxBulletNum == remainBulletNum)
			return;
		++remainBulletNum;
	}
	public void decreaseBulletNum() {
		if(0 == remainBulletNum)
			return;
		--remainBulletNum;
	}
	
	// 사용자의 데이터를 기반으로 총알의 데미지를 최신화 하는 함수
	public void updateBulletDamage() {
		Bullet.setDamage(mainCharacter.getDamage());
	}
	
	// --------------- Wall ---------------- //
	public ArrayList<Wall> getWalls() {
		return walls;
	}
	// 여기서 인자로 받는 행렬값은 mazeMatrix의 값이 아니라, 실제 출력되는 JFrame에서의 행렬값이다.
	public void addWall(int row, int col) {  
		walls.add(new Wall(row,col));
	}
	
	// 게임 시작 버튼을 누르면 벽들을 전부 생성해서 모델에 set한다.
	public void setWalls() {
		for(int row=0; row<Maze.ROWS; ++row) {
			for(int col=0; col<Maze.COLS; ++col) {	
				if(maze.getMazeMatrix()[row][col] == Maze.WALL) {
					addWall(row*GamePlayPanel.CELL_LENGTH, col*GamePlayPanel.CELL_LENGTH);
				}
			}
		}
	}
	// 인자의 행렬은 JFrame의 행렬값이다.
	public void deleteWall(int row, int col) {
		Wall w = new Wall(row, col);
		if(walls.contains(w)) {
			walls.remove(w);
		}
	}
	
	// ------------ MainCharacter ------------ // 
	
	
	
	
	
	// -------------- Maze ----------------- //
	public Maze getMaze() {
		return maze;
	}
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}
	
	// -------------- Model ----------------//
	
}

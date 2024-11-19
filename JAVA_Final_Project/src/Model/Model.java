package Model;

public class Model {
	private Maze maze;
	private MainCharacter mainCharacter;
	
	
	
	
	
	public Model() {
		maze = new Maze();
		mainCharacter = new MainCharacter(this);
	}          
	
	public Maze getMaze() {
		return maze;
	}
	public MainCharacter getMainCharacter() {
		return mainCharacter;
	}
}

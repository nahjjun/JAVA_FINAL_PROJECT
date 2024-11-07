package View;

import javax.swing.JFrame;

import Model.Model;

public class View extends JFrame{
	// View JFrame 객체의 데이터들
	public static final int VIEW_WIDTH = 650;
	public static final int VIEW_HEIGHT = 650;
	
	
	
	private Model model;
	
	private StartPage startPage;
	private GamePage gamePage;
	
	
	public View(Model model) {		
		this.model = model;
	}
	public void startGame() {
		
		
	}
	
	public StartPage getStartPage() {
		return startPage;
	}
	public GamePage getGamePage() {
		return gamePage;
	}
}

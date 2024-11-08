package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import Model.Model;

public class View extends JFrame{
	// View JFrame 객체의 데이터들
	public static final int VIEW_WIDTH = 650;
	public static final int VIEW_HEIGHT = 650;
	
	public static final Color PATH_COLOR = Color.WHITE;
	public static final Color WALL_COLOR = Color.BLACK;
	public static final Color USERPLACE_COLOR = Color.YELLOW;
	public static final Color ENTRANCE_COLOR = Color.BLUE;

	
	
	private Model model;
	
	private StartPage startPage;
	private GamePage gamePage;
	
	
	public View(Model model) {		
		setTitle("미로 디펜스 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
		setLayout(new BorderLayout());
		
		this.model = model;
		startPage = new StartPage(model);
		gamePage = new GamePage(model);
		
		add(startPage,BorderLayout.CENTER);
		
		pack();
	}
	
	// visible 값을 true로 설정해서 화면 보이게 한다.
	public void startGame() {
		setVisible(true);
	}
	
	public StartPage getStartPage() {
		return startPage;
	}
	public GamePage getGamePage() {
		return gamePage;
	}
}

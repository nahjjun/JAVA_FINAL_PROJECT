package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import Controller.Controller;
import Model.Model;

public class View extends JFrame{
	// View JFrame 객체의 데이터들
	public static final int VIEW_WIDTH = 700;
	public static final int VIEW_HEIGHT = 700;
	
	public static final int MAZE_PANEL_WIDTH = 600;
	public static final int MAZE_PANEL_HEIGHT = 600;
	public static final int USER_INTERFACE_PANEL_WIDTH = 700;
	public static final int USER_INTERFACE_PANEL_HEIGHT = 35;
	
	public static final Color PATH_COLOR = Color.WHITE;
	public static final Color WALL_COLOR = Color.BLACK;
	public static final Color USERPLACE_COLOR = Color.YELLOW;
	public static final Color ENTRANCE_COLOR = Color.BLUE;
	
	public static final Color MAINCHARACTER_COLOR = Color.GREEN;
	public static final Color ENEMYCHARACTER_COLOR = Color.RED;
	public static final Color BULLET_COLOR = Color.green;

	
	private Model model;
	private Controller controller;
	
	private StartPage startPage;
	private ReadyPage readyPage;
	private GamePage gamePage;
	
	
	public View(Model model, Controller controller) {
		this.model = model;
		this.controller = controller;
		
		startPage = new StartPage();
		readyPage = new ReadyPage(model);
		gamePage = new GamePage(model, this, controller);
		
		setTitle("미로 디펜스 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
		setLayout(new BorderLayout());
		
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
	public ReadyPage getReadyPage() {
		return readyPage;
	}	
	
	// 새로운 게임 페이지를 생성하는 함수 
	public void resetGamePage() {
		gamePage = new GamePage(model, this, controller);
	}
    public GamePage getGamePage() {
    	return gamePage;
    }
}

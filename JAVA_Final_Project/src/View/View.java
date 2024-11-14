package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import Model.Model;

public class View extends JFrame{
	// View JFrame 객체의 데이터들
	public static final int VIEW_WIDTH = 700;
	public static final int VIEW_HEIGHT = 700;
	
	public static final int MAZE_PANEL_WIDTH = 600;
	public static final int MAZE_PANEL_HEIGHT = 600;
	public static final int USER_INTERFACE_PANEL_WIDTH = 600;
	public static final int USER_INTERFACE_PANEL_HEIGHT = 50;
	
	public static final Color PATH_COLOR = Color.WHITE;
	public static final Color WALL_COLOR = Color.BLACK;
	public static final Color USERPLACE_COLOR = Color.YELLOW;
	public static final Color ENTRANCE_COLOR = Color.BLUE;

	
	
	private Model model;
	
	private StartPage startPage;
	
	
	public View(Model model) {		
		setTitle("미로 디펜스 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));
		setLayout(new BorderLayout());
		
		this.model = model;
		startPage = new StartPage(model);
		
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
	
}

package View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartPage extends JPanel{
	// 첫번째 스테이지로 이동하는 버튼 (게임 시작 버튼)
	private JButton goStagePageButton;
	// 게임을 종료하는 버튼
	private JButton exitButton;
	// 게임 타이틀을 적을 JLabel
	private JLabel title;
	
	public StartPage() {
		goStagePageButton = new JButton("게임 시작");
		exitButton = new JButton("게임 종료");
		title = new JLabel();
		this.setLayout(null);
		setPageStyle();
		
		add(title);
		add(goStagePageButton);
		add(exitButton);
	}
	
	
	public JButton getGoStagePageButton() {
		return goStagePageButton;
	}
	public JButton getExitButton() {
		return exitButton;
	}
	
	
	private void setPageStyle() {
		title.setText("미로 디펜스 게임");
		title.setSize(275,100);
		title.setFont(new Font("a", Font.BOLD, 30));
		title.setLocation(200, 150);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.CENTER);
		
		goStagePageButton.setSize(100,40);
		goStagePageButton.setLocation(200, 250);
		goStagePageButton.setFont(new Font("a", Font.BOLD, 15));
		exitButton.setSize(100,40);
		exitButton.setLocation(350, 250);
		exitButton.setFont(new Font("a", Font.BOLD, 15));
	}
}

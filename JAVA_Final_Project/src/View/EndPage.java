package View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Controller.Controller;
import Model.Model;

public class EndPage extends JPanel{
	private Model model;
    private View view;
    private Controller controller;
    
    private JLabel interfaceLabel;
    private JButton restartGameButton;
    private JButton endGameButton;
    
    
    public EndPage(Model model, View view, Controller controller) {
    	this.model = model;
    	this.view = view;
    	this.controller = controller;
    	
    	interfaceLabel = new JLabel("게임 클리어!");
    	restartGameButton = new JButton("게임 다시하기");
    	endGameButton = new JButton("게임 종료하기");
    	// 페이지의 스타일을 설정
    	setPageStyle();
    	
    	this.add(interfaceLabel);
    	this.add(restartGameButton);
    	this.add(endGameButton);
    	
    }
	
    
    public JLabel getInterfaceLabel() {
    	return interfaceLabel; 
    }
    public JButton getRestartGameButton() {
    	return restartGameButton; 
    }
    public JButton getEndGameButton() {
    	return endGameButton; 
    }
    
    private void setPageStyle() {
    	this.setLayout(null);
    	
    	interfaceLabel.setSize(275,100);
		interfaceLabel.setFont(new Font("a", Font.BOLD, 30));
		interfaceLabel.setLocation(200, 150);
		interfaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceLabel.setVerticalAlignment(SwingConstants.CENTER);
    	
		restartGameButton.setSize(150,40);
		restartGameButton.setLocation(150, 250);
		restartGameButton.setFont(new Font("a", Font.BOLD, 15));
		endGameButton.setSize(150,40);
		endGameButton.setLocation(350, 250);
		endGameButton.setFont(new Font("a", Font.BOLD, 15));
	}
	
}

package Controller;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Model.Model;
import View.View;

public class StageController {
	private Model model;
	private View view;
	
	
	
	public StageController(Model model, View view) {
		this.model = model;
		this.view = view;
	}
	private void playStage(int stageNum) {
		paintStageTitle(stageNum);
		Thread thread = new Thread(){
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Container contentPane = view.getContentPane();
				contentPane.removeAll();
				contentPane.setLayout(new BorderLayout());
				
				model.getBullets().clear();
				model.getEnemyCharacters().clear();
				model.setCurrentGameStage(stageNum);
				
				// 현재 게임 스테이지를 설정
				if(stageNum == 1) {
					view.getReadyPage().updateUserInterFace();
					contentPane.add(view.getReadyPage(), BorderLayout.CENTER);
					view.getReadyPage().revalidate();
					view.getReadyPage().repaint();
				}
				else {
					view.resetGamePage();
					contentPane.add(view.getGamePage(), BorderLayout.CENTER);
					view.getGamePage().startGame();
				}
					
				
				view.revalidate();
				view.repaint();	
			}
		};
		thread.start();
	}
	
	
	public void playStage1() {	
		// Stage Title을 먼저 출력하고, 스레드를 사용해서 2초 대기 후 기존 동작을 수행하게 한다.
		playStage(1);
	}
	
	public void playStage2() {
		playStage(2);	
	}
	
	public void playStage3() {
		playStage(3);	
	}
	public void playStage4() {
		playStage(4);	
	}
	public void playStage5() {
		playStage(5);	
	}
	public void playStage6() {
		playStage(6);	
	}
	// 스테이지를 종료하고 게임 종료를 하는 함수
	public void endStage() {
		
		Container contentPane = view.getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());
		
		
		contentPane.add(view.getEndPage(), BorderLayout.CENTER);
		
		// 게임 클리어 여부에 따라 문구가 달라짐
		if(!model.getClearedGame())
			view.getEndPage().getInterfaceLabel().setText("클리어 실패...");
		else
			view.getEndPage().getInterfaceLabel().setText("클리어 성공!");
		
		view.revalidate();
		view.repaint();
		
	}
	
	// 스테이지 시작 전, 해당 스테이지가 몇번 스테이진지 보여주는 함수
	private void paintStageTitle(int stageNum) {
		Container contentPane = view.getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(null);
		
		JLabel stageTitle = new JLabel("Stage " + stageNum);
		stageTitle.setSize(275,100);
		stageTitle.setFont(new Font("a", Font.BOLD, 30));
		stageTitle.setLocation(200, 150);
		stageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		stageTitle.setVerticalAlignment(SwingConstants.CENTER);
		contentPane.add(stageTitle);
		view.revalidate();
		view.repaint();
	}
}

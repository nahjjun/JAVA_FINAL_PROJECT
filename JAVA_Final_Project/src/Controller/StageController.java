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
	
	public void playStage1() {
		// Stage Title을 먼저 출력하고, 스레드를 사용해서 2초 대기 후 기존 동작을 수행하게 한다.
		paintStageTitle(1);
		
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
				
				// 벽의 개수를 설정
				model.setRemainWallNum(40);
				model.setCurrentGameStage(1);
				view.getReadyPage().updateWallNum();
				view.getReadyPage().updateCurrentGameStage();
				
				contentPane.add(view.getReadyPage(), BorderLayout.CENTER);
				view.revalidate();
				view.repaint();	
			}
		};
		thread.start();
	}
	
	public void playStage2() {
		// Stage Title을 먼저 출력하고, 스레드를 사용해서 2초 대기 후 기존 동작을 수행하게 한다.
		paintStageTitle(2);
		
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
				
				// 벽의 개수를 설정
				model.setRemainWallNum(30);
				model.setCurrentGameStage(2);
				view.getReadyPage().updateWallNum();
				view.getReadyPage().updateCurrentGameStage();
				
				contentPane.add(view.getReadyPage(), BorderLayout.CENTER);
				view.revalidate();
				view.repaint();	
			}
		};
		thread.start();	
	}
	
	public void playStage3() {
		
		
	}
	// 스테이지를 종료하고 게암 종료를 하는 함수
	public void endStage() {
		
		
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
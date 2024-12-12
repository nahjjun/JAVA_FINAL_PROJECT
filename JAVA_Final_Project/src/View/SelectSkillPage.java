package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Model.Model;

public class SelectSkillPage extends JPanel{
	private Model model;
	private View view;
	private JLabel interfaceLabel;
	private JButton[] skillButtons;
	// 다양하게 존재하는 스킬들 중에서, 랜덤으로 뽑아서 스킬 버튼을 설정할 배열  
	private int[] randomSelected;
	
	// 현재 총 스킬의 개수. 
	private final int maxSkillNum = 5;
	
	public SelectSkillPage(Model model, View view) {
		this.model = model;
		this.view = view;
		setLayout(null);
		// interfaceLabel 초기화
		initInterfaceLabel();
		randomSelected = new int[3];
		
		// skillButtons 초기화
		skillButtons = new JButton[3];
		for(int i=0; i<3; ++i) {
			skillButtons[i] = new JButton();
			add(skillButtons[i]);
			skillButtons[i].setSize(200,40);
			skillButtons[i].setFont(new Font("a", Font.BOLD, 10));
		}
		skillButtons[0].setLocation(50, 400);
		skillButtons[1].setLocation(250, 400);
		skillButtons[2].setLocation(450, 400);
		
		add(interfaceLabel);
		
	}
	// 스킬 선택 페이지를 paint하는 함수
	public void paintSelectSkillPage() {
		Container contentPane = view.getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());
		
		// 스킬 버튼들을 다시 만들고, 화면에 추가한다.
		view.getSelectSkillPage().remakeSkillButtons();
		contentPane.add(view.getSelectSkillPage(), BorderLayout.CENTER);
		contentPane.revalidate();
	    contentPane.repaint();
	}
	
	
	// 랜덤으로 스킬 버튼들을 섞어주는 함수 
	private void remakeSkillButtons() {
		int count=0;
		// 0 ~ maxSkillNum-1까지의 숫자를 총 3개 뽑는다.(-1이 나올 가능성은 다른 확률보다 현저히 낮기에, 1~3 까지의 가능성으로 뽑아서 공평하게 스킬이 추출될 수 있게 한다.
		while(count<3) {
			randomSelected[count] = (int)(Math.random()*maxSkillNum);
			
			// 랜덤으로 뽑은 스킬을 
			switch(randomSelected[count]) {
			// 공격력 증가 스킬의 출현 확률을 제일 낮게 설정
			case 0:
				skillButtons[count].setText("공격력 증가(1)");
				break;
			case 1:
				skillButtons[count].setText("이동속도 증가(1)");
				break;
			case 2:
				skillButtons[count].setText("최대 체력 증가(5)");
				break;
			case 3:
				skillButtons[count].setText("총알 충전 속도 증가(0.1초 증가)");
				break;
			case 4:
				skillButtons[count].setText("총알 보유 최대치 증가(5)");
				break;
			}			
			++count;
		}
		
	}
	
	// i는 어떤 스킬이 선택되었는지 의미하는 변수 
	public void doSkillEvent(int i) {
		switch(i) {
		// 공격력을 증가시켜주는 스킬
		case 0:
			model.increaseUserDamage();
			break;
		// 이동속도를 증가시켜주는 스킬
		case 1:
			model.increaseUserMove();
			break;
		// 최대 체력을 증가시켜주는 스킬
		case 2:
			model.increaseUserHealth();
			break;
		// 총알 장전 속도를 증가시켜주는 스킬
		case 3:
			model.decreaseBulletAddTime();
			break;
		// 총알 보유 최대치를 증가시켜주는 스킬
		case 4:
			model.increaseMaxBullet();
			break;
		
		}
	}
	
	
	
	private void initInterfaceLabel() {
		interfaceLabel = new JLabel("스킬을 선택하세요");
		interfaceLabel.setSize(275,100);
		interfaceLabel.setFont(new Font("a", Font.BOLD, 30));
		interfaceLabel.setLocation(200, 150);
		interfaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		interfaceLabel.setVerticalAlignment(SwingConstants.CENTER);
		
	}
	
	// --------- getter / setter -------- //
	public int[] getRandomSelected() {
		return randomSelected;
	}
	public JButton[] getSkillButtons() {
		return skillButtons;
	}
	
	
}

package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

import Model.Model;

public class GamePage extends JPanel {
    private Model model;
    private GamePlayPanel gamePlayPanel; // 미로를 둘 패널
    private JPanel userInterfacePanel; // 사용자 인터페이스 패널

    public GamePage(Model model) {
        try {
            this.model = model;
            setLayout(new BorderLayout());

            gamePlayPanel = new GamePlayPanel(model);
            gamePlayPanel.setPreferredSize(new Dimension(View.MAZE_PANEL_WIDTH, View.MAZE_PANEL_HEIGHT));

            userInterfacePanel = new JPanel();
            userInterfacePanel.setPreferredSize(new Dimension(View.USER_INTERFACE_PANEL_WIDTH, View.USER_INTERFACE_PANEL_HEIGHT));
            userInterfacePanel.setLayout(new FlowLayout());

            // 컴포넌트 추가
            add(gamePlayPanel, BorderLayout.CENTER);
            add(userInterfacePanel, BorderLayout.SOUTH);

            // 레이아웃을 재정비하고 화면을 다시 그리기
            revalidate();
            repaint();
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    public void startGame() {
        gamePlayPanel.startGamePlay();
        gamePlayPanel.repaint(); // 게임 시작 시 화면 다시 그리기
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

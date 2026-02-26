package com.arcade.engine.display;

import com.arcade.engine.state.StateManager;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * 실제 게임 화면(현재 상태)을 렌더링하는 패널 클래스.
 * StateManager로부터 현재 상태를 받아와 Graphics2D 컨텍스트를 넘겨줍니다.
 */
public class GamePanel extends JPanel {

    private final StateManager stateManager;

    public GamePanel(StateManager stateManager) {
        this.stateManager = stateManager;
        
        // 화면 깜빡임(Flickering) 방지를 위한 Double Buffering 활성화 (JPanel은 기본 true이나 명시적 설정)
        this.setDoubleBuffered(true);
        this.setBackground(Color.BLACK); // 기본 배경색
        
        // 포커스를 받아 키보드 입력을 처리할 수 있도록 설정
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 부모의 paintComponent 호출 (배경 지우기 등 기본 처리)
        super.paintComponent(g);

        // Java 21 Graphics2D 캐스팅
        Graphics2D g2d = (Graphics2D) g;

        // 부드러운 안티앨리어싱(Anti-Aliasing) 적용
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // StateManager에게 렌더링 위임
        if (stateManager != null) {
            stateManager.render(g2d);
        }
        
        // 렌더링 동기화를 위한 처리 (Linux 등에서 프레임 지연 방지)
        java.awt.Toolkit.getDefaultToolkit().sync();
    }
}

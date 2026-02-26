package com.arcade.engine.display;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 게임의 메인 윈도우(JFrame)를 관리하는 클래스.
 * 화면 크기 설정 및 GamePanel 부착을 담당합니다.
 */
public class GameDisplay {

    private final JFrame frame;

    public GameDisplay(String title, int width, int height, GamePanel panel) {
        frame = new JFrame(title);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false); // 창 크기 변경 불가 (안정적인 게임 해상도 유지)
        frame.setLocationRelativeTo(null); // 화면 중앙에 배치
        
        // 컨텐츠 패인에 렌더링을 담당할 GamePanel 부착
        frame.add(panel);
    }

    /**
     * 윈도우를 화면에 표시합니다.
     * Swing의 Thread-Safe 원칙을 지키기 위해 EDT(Event Dispatch Thread)에서 실행합니다.
     */
    public void show() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }
}

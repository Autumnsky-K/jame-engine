package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import com.arcade.engine.io.SaveSystem;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * 게임 오버 상태를 나타내는 Record.
 * 종료 시점의 데이터(최종 점수 등)를 불변 객체로 안전하게 보유합니다.
 *
 * @param finalScore 획득한 최종 점수
 * @param highScore 저장된 최고 점수 (또는 방금 갱신된 최고 점수)
 */
public record GameOverState(int finalScore, int highScore) implements GameState {
    
    /**
     * 상태 진입 시 자동으로 파일에서 최고 점수를 로드하고, 
     * 신기록인 경우 파일에 저장하도록 커스텀 생성자를 제공합니다.
     * 기존 SnakeGame에서 new GameOverState(score) 로 호출할 때 이 생성자가 매핑됩니다.
     */
    public GameOverState(int finalScore) {
        // Record의 주 생성자 호출
        this(finalScore, Math.max(finalScore, SaveSystem.loadHighScore()));
        
        // SaveSystem 내부에서 점수 비교 후 신기록일 때만 저장 수행
        SaveSystem.saveHighScore(finalScore);
    }

    @Override
    public void handleInput(InputHandler input, StateManager stateManager) {
        // 스페이스바를 누르면 다시 메뉴 화면으로 복귀
        if (input.isKeyPressed(java.awt.event.KeyEvent.VK_SPACE)) {
            stateManager.switchState(new MenuState());
        }
    }

    @Override
    public void update(StateManager stateManager) {
        // TODO: 결과 표시 애니메이션 등
    }

    @Override
    public void render(Graphics2D g) {
        // GAME OVER 타이틀
        g.setColor(Color.RED);
        g.setFont(new Font("Monospaced", Font.BOLD, 48));
        String text = "GAME OVER";
        int x = 400 - g.getFontMetrics().stringWidth(text) / 2;
        g.drawString(text, x, 220);

        // 최종 점수 표시
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 24));
        String scoreText = "Final Score: " + finalScore;
        int sx = 400 - g.getFontMetrics().stringWidth(scoreText) / 2;
        g.drawString(scoreText, sx, 290);

        // 최고 점수(High Score) 표시
        g.setColor(Color.YELLOW);
        String highText = "High Score:  " + highScore;
        int hx = 400 - g.getFontMetrics().stringWidth(highText) / 2;
        g.drawString(highText, hx, 330);

        // 재시작 안내
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 18));
        String restartText = "Press SPACE to return to Menu";
        int rx = 400 - g.getFontMetrics().stringWidth(restartText) / 2;
        g.drawString(restartText, rx, 400);
    }
}

package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import com.arcade.games.snake.SnakeGame;
import com.arcade.games.breakout.BreakoutGame;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * 게임의 메인 메뉴 상태.
 * 사용자가 스네이크 게임과 벽돌 깨기 게임을 선택할 수 있도록 기능을 확장했습니다.
 */
public final class MenuState implements GameState {

    private int selectedIndex = 0;
    private final String[] games = {"1. Snake Game", "2. Brick Breaker"};
    
    private boolean upPressed = false;
    private boolean downPressed = false;

    @Override
    public void handleInput(InputHandler input, StateManager stateManager) {
        // 위 방향키 (단일 입력 처리)
        if (input.isKeyPressed(KeyEvent.VK_UP) && !upPressed) {
            selectedIndex = (selectedIndex - 1 + games.length) % games.length;
            upPressed = true;
        } else if (!input.isKeyPressed(KeyEvent.VK_UP)) {
            upPressed = false;
        }
        
        // 아래 방향키 (단일 입력 처리)
        if (input.isKeyPressed(KeyEvent.VK_DOWN) && !downPressed) {
            selectedIndex = (selectedIndex + 1) % games.length;
            downPressed = true;
        } else if (!input.isKeyPressed(KeyEvent.VK_DOWN)) {
            downPressed = false;
        }
        
        // 선택 (SPACE 또는 ENTER)
        if (input.isKeyPressed(KeyEvent.VK_SPACE) || input.isKeyPressed(KeyEvent.VK_ENTER)) {
            if (selectedIndex == 0) {
                stateManager.switchState(new PlayingState(new SnakeGame()));
            } else {
                stateManager.switchState(new PlayingState(new BreakoutGame()));
            }
        }
    }
    
    @Override
    public void update(StateManager stateManager) {
        // UI 애니메이션 등 업데이트 요소
    }

    @Override
    public void render(Graphics2D g) {
        // 메인 타이틀
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 50));
        String title = "JAME ENGINE";
        int tx = 400 - g.getFontMetrics().stringWidth(title) / 2;
        g.drawString(title, tx, 200);

        // 게임 선택 메뉴
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 24));
        for (int i = 0; i < games.length; i++) {
            if (i == selectedIndex) {
                g.setColor(Color.YELLOW);
                g.drawString("> " + games[i], 280, 350 + i * 50);
            } else {
                g.setColor(Color.WHITE);
                g.drawString("  " + games[i], 280, 350 + i * 50);
            }
        }
        
        // 조작 안내
        g.setColor(Color.GRAY);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 16));
        String guide = "Use UP/DOWN to select, SPACE to start";
        int gx = 400 - g.getFontMetrics().stringWidth(guide) / 2;
        g.drawString(guide, gx, 550);
    }
}

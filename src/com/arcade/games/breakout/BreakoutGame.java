package com.arcade.games.breakout;

import com.arcade.engine.GameLogic;
import com.arcade.engine.input.InputHandler;
import com.arcade.engine.state.MenuState;
import com.arcade.engine.state.StateManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 벽돌 깨기 게임의 메인 로직 클래스.
 * GameLogic 인터페이스를 구현하여 프레임워크의 PlayingState에 동적으로 주입됩니다.
 */
public class BreakoutGame implements GameLogic {
    private Paddle paddle;
    private Ball ball;
    private final List<Brick> bricks = new ArrayList<>();
    
    private int score = 0;
    private int level = 1;

    public BreakoutGame() {
        initLevel();
    }

    private void initLevel() {
        paddle = new Paddle();
        ball = new Ball();
        bricks.clear();
        
        // 레벨이 오를수록 벽돌 줄 수 증가
        int rows = Math.min(3 + level, 8); 
        int cols = 10;
        int brickWidth = 70;
        int brickHeight = 25;
        int padding = 8;
        int offsetX = 15;
        int offsetY = 50;

        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = offsetX + c * (brickWidth + padding);
                int y = offsetY + r * (brickHeight + padding);
                Color color = colors[r % colors.length];
                bricks.add(new Brick(x, y, brickWidth, brickHeight, color));
            }
        }
    }

    @Override
    public void handleInput(InputHandler input, StateManager stateManager) {
        if (input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            stateManager.switchState(new MenuState());
            return;
        }
        paddle.handleInput(input);
    }

    @Override
    public void update(StateManager stateManager) {
        ball.update(stateManager, paddle, score);
        
        boolean levelComplete = true;
        
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                levelComplete = false;
                // 공과 벽돌의 충돌 감지
                if (ball.getBounds().intersects(brick.getBounds())) {
                    brick.destroy();
                    ball.reverseY(); // 단순 상하 반사
                    score += 50;
                    break; // 한 프레임에 하나의 벽돌만 파괴되도록 보정
                }
            }
        }
        
        // 모든 벽돌 파괴 시 다음 레벨로
        if (levelComplete) {
            level++;
            initLevel();
        }
    }

    @Override
    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        
        for (Brick brick : bricks) {
            brick.render(g);
        }

        // 상단 UI
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 20));
        g.drawString("SCORE: " + score, 10, 25);
        g.drawString("LEVEL: " + level, 700, 25);
    }
}

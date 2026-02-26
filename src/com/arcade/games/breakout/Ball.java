package com.arcade.games.breakout;

import com.arcade.engine.state.GameOverState;
import com.arcade.engine.state.StateManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 반사 물리 로직을 포함하는 공 클래스.
 */
public class Ball {
    private double x, y;
    private double dx, dy;
    private final int size = 16; 
    private final int screenWidth = 800;
    private final int screenHeight = 600;

    public Ball() {
        this.x = screenWidth / 2.0 - size / 2.0;
        this.y = 400;
        this.dx = 4;
        this.dy = -5; // 위쪽으로 출발
    }

    public void update(StateManager stateManager, Paddle paddle, int currentScore) {
        x += dx;
        y += dy;

        // 좌/우 벽 충돌
        if (x <= 0 || x + size >= screenWidth) {
            dx = -dx;
        }
        // 천장 충돌
        if (y <= 0) {
            dy = -dy;
        }

        // 바닥 충돌 (게임 오버)
        if (y + size >= screenHeight) {
            stateManager.switchState(new GameOverState(currentScore));
            return;
        }

        // 패들 충돌 로직 (정교한 반사각 계산)
        Rectangle ballRect = getBounds();
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        if (ballRect.intersects(paddleRect)) {
            double paddleCenter = paddle.getX() + paddle.getWidth() / 2.0;
            double ballCenter = x + size / 2.0;
            
            // 중앙에서 멀어질수록 반사각(-1.0 ~ 1.0)이 커짐
            double hitPoint = (ballCenter - paddleCenter) / (paddle.getWidth() / 2.0);
            
            // 최대 수평 가속도 조정 및 무조건 위로 튕기도록 설정
            dx = hitPoint * 6;
            dy = -Math.abs(dy);
            y = paddle.getY() - size; // 패들에 끼이는 현상 방지
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.fillOval((int)x, (int)y, size, size);
    }
    
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, size, size);
    }
    
    public void reverseY() {
        dy = -dy;
    }
}

package com.arcade.games.breakout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 벽돌 데이터를 나타내는 불변 레코드.
 * 상태(파괴 여부)를 외부 리스트에서 필터링하거나 별도로 관리하여 객체 자체는 순수하게 유지할 수도 있지만,
 * 간단한 구현을 위해 클래스로 작성합니다.
 */
public class Brick {
    private final int x, y, width, height;
    private final Color color;
    private boolean destroyed;

    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.destroyed = false;
    }

    public void render(Graphics2D g) {
        if (!destroyed) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }
    }

    public boolean isDestroyed() { return destroyed; }
    public void destroy() { this.destroyed = true; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

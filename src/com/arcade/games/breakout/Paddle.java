package com.arcade.games.breakout;

import com.arcade.engine.input.InputHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * 사용자가 조작하는 패들 클래스.
 */
public class Paddle {
    private double x;
    private final int y;
    private final int width = 100;
    private final int height = 15;
    private final double speed = 10.0;
    private final int screenWidth = 800;

    public Paddle() {
        this.x = screenWidth / 2.0 - width / 2.0;
        this.y = 550;
    }

    public void handleInput(InputHandler input) {
        if (input.isKeyPressed(KeyEvent.VK_LEFT)) {
            x -= speed;
        }
        if (input.isKeyPressed(KeyEvent.VK_RIGHT)) {
            x += speed;
        }

        // 화면 밖으로 나가지 않도록 고정
        if (x < 0) x = 0;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    public void render(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect((int)x, y, width, height);
    }

    public int getX() { return (int)x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

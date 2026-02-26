package com.arcade.games.snake;

import com.arcade.engine.input.InputHandler;
import com.arcade.engine.state.GameOverState;
import com.arcade.engine.state.MenuState;
import com.arcade.engine.state.StateManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 800 / TILE_SIZE;  // 40칸
    private static final int HEIGHT = 600 / TILE_SIZE; // 30칸

    private final LinkedList<SnakeBody> snake = new LinkedList<>();
    private SnakeBody food;
    
    // 현재 이동 방향 (초기값: 오른쪽)
    private int dx = 1;
    private int dy = 0;
    private boolean directionChanged = false; // 한 틱 내에 다중 방향 전환 방지
    
    private int score = 0;
    
    // 60FPS 메인 루프에서 뱀의 이동 속도를 늦추기 위한 타이머
    private int tick = 0;
    private int currentTickRate = 6; // 초당 약 10칸 이동 (초기 속도)

    private final Random random = new Random();

    public SnakeGame() {
        // 초기 뱀 생성 (중앙 부근 3칸)
        snake.add(new SnakeBody(WIDTH / 2, HEIGHT / 2));
        snake.add(new SnakeBody(WIDTH / 2 - 1, HEIGHT / 2));
        snake.add(new SnakeBody(WIDTH / 2 - 2, HEIGHT / 2));
        spawnFood();
    }

    private void spawnFood() {
        while (true) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            SnakeBody newFood = new SnakeBody(x, y);
            
            // 먹이가 뱀의 몸통과 겹치지 않게 생성
            if (!snake.contains(newFood)) {
                food = newFood;
                break;
            }
        }
    }

    public void handleInput(InputHandler input, StateManager stateManager) {
        if (input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            stateManager.switchState(new MenuState());
            return;
        }

        if (!directionChanged) {
            if (input.isKeyPressed(KeyEvent.VK_UP) && dy != 1) {
                dx = 0; dy = -1; directionChanged = true;
            } else if (input.isKeyPressed(KeyEvent.VK_DOWN) && dy != -1) {
                dx = 0; dy = 1; directionChanged = true;
            } else if (input.isKeyPressed(KeyEvent.VK_LEFT) && dx != 1) {
                dx = -1; dy = 0; directionChanged = true;
            } else if (input.isKeyPressed(KeyEvent.VK_RIGHT) && dx != -1) {
                dx = 1; dy = 0; directionChanged = true;
            }
        }
    }

    public void update(StateManager stateManager) {
        tick++;
        if (tick < currentTickRate) return;
        tick = 0;
        directionChanged = false; // 이동 완료 후 방향 전환 허용

        SnakeBody head = snake.getFirst();
        int newX = head.x() + dx;
        int newY = head.y() + dy;

        // 1. 벽 충돌 검사
        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT) {
            stateManager.switchState(new GameOverState(score));
            return;
        }

        SnakeBody newHead = new SnakeBody(newX, newY);

        // 2. 자기 몸 충돌 검사 (가장 끝 꼬리는 이동하면서 비워지므로 제외)
        for (int i = 0; i < snake.size() - 1; i++) {
            if (snake.get(i).equals(newHead)) {
                stateManager.switchState(new GameOverState(score));
                return;
            }
        }

        // 머리 추가
        snake.addFirst(newHead);

        // 3. 먹이 획득 검사
        if (newHead.equals(food)) {
            score += 10;
            
            // 점수가 50점 증가할 때마다 틱 딜레이 감소 -> 속도 증가 (최소 1로 제한)
            currentTickRate = Math.max(1, 6 - (score / 50));
            
            spawnFood();
        } else {
            // 먹이를 먹지 않았다면 꼬리 자르기
            snake.removeLast();
        }
    }

    public void render(Graphics2D g) {
        // 먹이 렌더링 (빨간색 원)
        g.setColor(Color.RED);
        g.fillOval(food.x() * TILE_SIZE, food.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // 뱀 렌더링 (초록색 사각형)
        g.setColor(Color.GREEN);
        for (SnakeBody part : snake) {
            // 타일 사이에 미세한 간격을 주어 마디를 구분 (TILE_SIZE - 1)
            g.fillRect(part.x() * TILE_SIZE, part.y() * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        // 점수 UI 렌더링
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 20));
        g.drawString("SCORE: " + score, 10, 25);
    }
}

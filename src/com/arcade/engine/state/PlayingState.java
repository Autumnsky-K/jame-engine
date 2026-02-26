package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import com.arcade.games.snake.SnakeGame;
import java.awt.Graphics2D;

/**
 * 실제 게임 플레이(테트리스, 스네이크 등)가 진행 중인 상태.
 * 내부에 구체적인 게임 로직(SnakeGame)을 캡슐화하여 실행합니다.
 */
public record PlayingState(SnakeGame game) implements GameState {
    
    public PlayingState() {
        this(new SnakeGame());
    }

    @Override
    public void handleInput(InputHandler input, StateManager stateManager) {
        game.handleInput(input, stateManager);
    }

    @Override
    public void update(StateManager stateManager) {
        game.update(stateManager);
    }

    @Override
    public void render(Graphics2D g) {
        game.render(g);
    }
}

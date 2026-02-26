package com.arcade.engine.state;

import com.arcade.engine.GameLogic;
import com.arcade.engine.input.InputHandler;
import java.awt.Graphics2D;

/**
 * 실제 게임 플레이가 진행 중인 상태.
 * 내부에 구체적인 게임 로직(GameLogic)을 캡슐화하여 실행합니다.
 * Strategy Pattern을 통해 새로운 게임을 추가해도 이 클래스를 수정할 필요가 없습니다.
 */
public record PlayingState(GameLogic game) implements GameState {
    
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

package com.arcade.engine;

import com.arcade.engine.input.InputHandler;
import com.arcade.engine.state.StateManager;
import java.awt.Graphics2D;

/**
 * 프레임워크에 추가될 개별 게임들이 공통으로 구현해야 할 인터페이스.
 * Strategy Pattern의 핵심 요소로, PlayingState는 구체적인 게임에 의존하지 않고 이 인터페이스에 의존합니다.
 */
public interface GameLogic {
    void handleInput(InputHandler input, StateManager stateManager);
    void update(StateManager stateManager);
    void render(Graphics2D g);
}

package com.arcade.engine.state;

import com.arcade.games.snake.SnakeGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StateManagerTest {

    @Test
    public void testInitialStateIsMenuState() {
        StateManager manager = new StateManager();
        Assertions.assertInstanceOf(MenuState.class, manager.getCurrentState(), "초기 상태는 MenuState여야 합니다.");
    }

    @Test
    public void testStateTransitionToPlayingState() {
        StateManager manager = new StateManager();
        
        // 상태를 스네이크 게임(PlayingState)으로 전환
        manager.switchState(new PlayingState(new SnakeGame()));
        
        Assertions.assertInstanceOf(PlayingState.class, manager.getCurrentState(), "상태가 PlayingState로 전환되어야 합니다.");
    }
    
    @Test
    public void testStateTransitionToGameOverState() {
        StateManager manager = new StateManager();
        manager.switchState(new GameOverState(100));
        
        Assertions.assertInstanceOf(GameOverState.class, manager.getCurrentState(), "상태가 GameOverState로 전환되어야 합니다.");
    }
}

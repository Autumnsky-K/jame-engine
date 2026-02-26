package com.arcade.games.snake;

import com.arcade.engine.state.GameOverState;
import com.arcade.engine.state.StateManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class SnakeGameTest {

    @Test
    public void testCollisionWithWallTransitionsToGameOver() throws Exception {
        SnakeGame game = new SnakeGame();
        StateManager stateManager = new StateManager();

        // 리플렉션을 사용하여 뱀의 위치를 벽 밖으로 조작
        Field snakeField = SnakeGame.class.getDeclaredField("snake");
        snakeField.setAccessible(true);
        @SuppressWarnings("unchecked")
        LinkedList<SnakeBody> snake = (LinkedList<SnakeBody>) snakeField.get(game);
        
        snake.clear();
        // X축 범위를 벗어나는 좌표. dx가 1이므로 한 번 이동해도 -9가 되어 벽 충돌 처리가 되도록 설정.
        snake.add(new SnakeBody(-10, 0));
        
        // 틱을 건너뛰기 위해 타이머를 임의로 조작하거나 그냥 여러 번 update 호출
        // currentTickRate 초기값이 6이므로 6번 update를 호출하면 실제 로직이 1번 수행됨
        for (int i = 0; i < 6; i++) {
            game.update(stateManager);
        }

        Assertions.assertInstanceOf(GameOverState.class, stateManager.getCurrentState(), "뱀이 벽에 부딪히면 GameOverState로 전환되어야 합니다.");
    }
}

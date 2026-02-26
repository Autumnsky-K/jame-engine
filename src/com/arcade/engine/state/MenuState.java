package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 * 게임의 메인 메뉴 상태를 나타내는 Record.
 * 상태 자체가 불변(Immutable)일 경우 record를 사용하여 간결하게 선언합니다.
 */
public record MenuState() implements GameState {

    @Override
    public void handleInput(InputHandler input, StateManager stateManager) {
        // 스페이스바를 누르면 PlayingState로 전환
        if (input.isKeyPressed(KeyEvent.VK_SPACE)) {
            stateManager.switchState(new PlayingState());
        }
    }
    
    @Override
    public void update(StateManager stateManager) {
        // TODO: 메뉴에서의 사용자 입력 대기, UI 애니메이션 업데이트 로직
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(java.awt.Color.WHITE);
        g.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 24));
        String text = "PRESS SPACE TO START";
        
        // 화면 중앙 부근에 텍스트를 출력하기 위한 임시 위치값 (창 크기 800x600 가정)
        int x = 400 - g.getFontMetrics().stringWidth(text) / 2;
        int y = 300;
        
        g.drawString(text, x, y);
    }
}

package com.arcade.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 다중 키 입력을 처리하는 상태 기반 InputHandler.
 * 이벤트 기반 처리를 넘어 게임 루프에서 현재 눌려있는 키를 즉시 확인할 수 있게 합니다.
 */
public class InputHandler implements KeyListener {

    // ASCII 및 주요 키보드 입력 코드를 커버하기 위한 크기
    private final boolean[] keys = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {
        // 게임 루프에서는 일반적으로 무시됨
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = false;
        }
    }

    /**
     * 특정 키가 현재 눌려있는지 확인합니다.
     * @param keyCode KeyEvent.VK_* 상수
     * @return 키가 눌려있으면 true 반환
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return keys[keyCode];
        }
        return false;
    }
}

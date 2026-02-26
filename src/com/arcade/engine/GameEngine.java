package com.arcade.engine;

/**
 * 게임 엔진의 핵심 라이프사이클을 정의하는 인터페이스.
 * GameLoop는 구체적인 게임 로직과 렌더링 방식에 의존하지 않고 (DIP 원칙),
 * 이 인터페이스를 통해 상태 업데이트와 화면 그리기를 위임합니다.
 */
public interface GameEngine {
    /**
     * 비즈니스 로직(물리, 입력 처리 등)을 업데이트합니다.
     */
    void update();

    /**
     * 현재 게임 상태를 화면에 렌더링합니다.
     */
    void render();
}

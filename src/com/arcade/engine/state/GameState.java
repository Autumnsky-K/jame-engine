package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import java.awt.Graphics2D;

/**
 * 게임 상태를 정의하는 sealed interface.
 * 외부 패키지나 허용되지 않은 클래스에서 임의로 상태를 추가하는 것을 방지하여 
 * 패턴 매칭(switch)의 타입 안전성을 보장합니다.
 */
public sealed interface GameState permits MenuState, PlayingState, GameOverState {
    
    /**
     * 상태에 따른 키보드 입력을 처리합니다.
     * 
     * @param input 현재 눌려있는 키 상태를 확인할 수 있는 핸들러
     * @param stateManager 상태 전환을 수행하기 위한 매니저
     */
    void handleInput(InputHandler input, StateManager stateManager);

    /**
     * 상태에 종속된 비즈니스 로직(입력 처리, 물리 계산 등) 업데이트.
     * @param stateManager 상태 전환을 수행하기 위한 매니저
     */
    void update(StateManager stateManager);

    /**
     * 상태에 종속된 화면을 그리기.
     * 
     * @param g 그래픽 렌더링 컨텍스트
     */
    void render(Graphics2D g);
}

package com.arcade.engine.state;

import com.arcade.engine.input.InputHandler;
import java.awt.Graphics2D;

/**
 * State Pattern의 Context 역할로, 현재의 상태를 보관하고 실행을 위임합니다.
 */
public class StateManager {

    private GameState currentState;

    public StateManager() {
        // 애플리케이션 시작 시 초기 상태를 MenuState로 지정 (메뉴에서 게임 선택 가능)
        this.currentState = new MenuState();
    }

    /**
     * 현재 상태의 입력 처리 로직을 실행하도록 위임합니다.
     */
    public void handleInput(InputHandler input) {
        if (currentState != null) {
            currentState.handleInput(input, this);
        }
    }

    /**
     * 현재 상태의 update 로직을 실행하도록 위임(Delegation)합니다.
     */
    public void update() {
        if (currentState != null) {
            currentState.update(this);
        }
    }

    /**
     * 현재 상태의 render 로직을 실행하도록 위임(Delegation)합니다.
     * 
     * @param g 그래픽 컨텍스트
     */
    public void render(Graphics2D g) {
        if (currentState != null) {
            currentState.render(g);
        }
    }

    /**
     * Java 21의 Pattern Matching for switch를 사용하여 안전하고 명확하게 상태를 전환합니다.
     * sealed interface 구조이므로 모든 자식 타입에 대해 누락 없이(exhaustive) 처리를 보장할 수 있습니다.
     * 
     * @param newState 변경할 새로운 게임 상태 객체
     */
    public void switchState(GameState newState) {
        // 1. 현재 상태를 떠날 때 필요한 작업 (로그, 정리 등)
        switch (this.currentState) {
            case MenuState m -> System.out.println("[State Exit] 메뉴 화면 종료");
            case PlayingState p -> System.out.println("[State Exit] 게임 플레이 종료");
            case GameOverState g -> System.out.println("[State Exit] 게임 오버 화면 종료 (이전 최종 점수: " + g.finalScore() + ")");
            case null -> System.out.println("[State Exit] 초기 상태 진입 준비...");
        }

        // 2. 상태 변경
        this.currentState = newState;

        // 3. 새로운 상태에 진입할 때 필요한 작업 (로그, 초기 설정 등)
        switch (newState) {
            case MenuState m -> System.out.println("[State Enter] 메뉴 화면으로 진입했습니다.");
            case PlayingState p -> System.out.println("[State Enter] 게임을 시작합니다!");
            case GameOverState g -> System.out.println("[State Enter] 게임 오버! 최종 점수 확인 중...");
        }
    }
    
    /**
     * 현재 상태 객체를 반환합니다.
     */
    public GameState getCurrentState() {
        return currentState;
    }
}

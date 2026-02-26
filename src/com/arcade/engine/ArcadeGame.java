package com.arcade.engine;

import com.arcade.engine.display.GameDisplay;
import com.arcade.engine.display.GamePanel;
import com.arcade.engine.input.InputHandler;
import com.arcade.engine.state.StateManager;

/**
 * 프레임워크의 코어 조립 및 실행을 담당하는 실제 엔진 구현체.
 * GameLoop, StateManager, GamePanel을 연결하여 생명주기를 관리합니다.
 */
public class ArcadeGame implements GameEngine {

    private final StateManager stateManager;
    private final GamePanel gamePanel;
    private final GameDisplay gameDisplay;
    private final GameLoop gameLoop;
    private final InputHandler inputHandler;

    public ArcadeGame(String title, int width, int height) {
        // 1. 상태 관리자 초기화
        this.stateManager = new StateManager();

        // 2. 화면 렌더러(Panel) 초기화 및 상태 관리자 연결
        this.gamePanel = new GamePanel(stateManager);

        // 3. 입력 핸들러 초기화 및 패널에 부착
        this.inputHandler = new InputHandler();
        this.gamePanel.addKeyListener(inputHandler);

        // 4. 메인 윈도우(Frame) 초기화
        this.gameDisplay = new GameDisplay(title, width, height, gamePanel);

        // 5. 메인 게임 루프 초기화
        this.gameLoop = new GameLoop(this);
    }

    /**
     * 엔진 생명주기: 로직 업데이트 위임
     */
    @Override
    public void update() {
        stateManager.handleInput(inputHandler);
        stateManager.update();
    }

    /**
     * 엔진 생명주기: 화면 갱신(렌더링)
     * GameLoop에서 1초에 60번 호출되어 GamePanel의 paintComponent를 트리거합니다.
     */
    @Override
    public void render() {
        // Swing의 repaint()를 호출하여 비동기적으로 UI 갱신 (더블 버퍼링 적용됨)
        gamePanel.repaint();
    }

    /**
     * 엔진 구동 시작.
     */
    public void start() {
        gameDisplay.show();
        gameLoop.start();
    }
    
    // 외부에 StateManager를 노출하여 필요시 상태 전환을 제어할 수 있게 함
    public StateManager getStateManager() {
        return stateManager;
    }
}

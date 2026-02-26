package com.arcade.engine;

/**
 * 60 FPS를 안정적으로 유지하는 핵심 메인 루프 클래스.
 * UI 스레드(EDT)와 분리되어 백그라운드에서 게임 로직과 렌더링 주기를 관리합니다.
 * 
 * - Separation of Concerns (SoC): GameLoop는 "언제" 그릴지만 결정하고, 
 *   "무엇을" 그릴지는 GameEngine 구현체에 위임합니다.
 */
public class GameLoop implements Runnable {

    private final GameEngine engine;
    private volatile boolean running;
    
    // 60 FPS 유지를 위한 시간 상수 (1초 = 1,000,000,000 나노초)
    private static final int TARGET_FPS = 60;
    private static final double TIME_PER_TICK = 1_000_000_000.0 / TARGET_FPS;

    public GameLoop(GameEngine engine) {
        this.engine = engine;
        this.running = false;
    }

    /**
     * 루프를 시작합니다. 
     * Java 21의 Thread.ofPlatform() 빌더를 사용하여 명시적으로 플랫폼 스레드를 생성합니다.
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        
        Thread.ofPlatform()
              .name("GameLoop-Thread")
              .start(this);
    }

    /**
     * 루프를 안전하게 종료합니다.
     */
    public synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        
        // 성능 측정을 위한 변수 (1초마다 FPS 출력)
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / TIME_PER_TICK;
            lastTime = now;

            // delta가 1 이상이면 1 프레임 진행 (고정 시간 단계)
            while (delta >= 1) {
                engine.update();
                engine.render();
                delta--;
                frames++;
            }

            // 1초마다 FPS 측정 후 출력
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                // 콘솔에 FPS를 출력합니다.
                System.out.println("FPS: " + frames);
                frames = 0;
            }

            // 자원 낭비(CPU 100% 점유)를 방지하기 위해 스레드 양보
            Thread.yield();
        }
    }
}

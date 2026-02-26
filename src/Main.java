import com.arcade.engine.ArcadeGame;

public class Main {
    public static void main(String[] args) {
        // 엔진 초기화: 타이틀과 해상도 설정
        ArcadeGame game = new ArcadeGame("Retro Arcade", 800, 600);
        
        // 엔진(게임 루프 및 윈도우) 시작
        game.start();
        
        System.out.println("Arcade Game Engine started...");
    }
}

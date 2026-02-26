package com.arcade.engine.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SaveSystemTest {

    private static final String TEST_SAVE_FILE = "test_arcade_highscore.dat";

    @BeforeEach
    public void setUp() {
        // 테스트 전 임시 파일 경로로 설정
        SaveSystem.setSaveFileName(TEST_SAVE_FILE);
        
        // 기존 테스트 파일이 있다면 삭제
        File file = new File(TEST_SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        // 테스트 후 임시 파일 정리
        File file = new File(TEST_SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testLoadHighScoreWhenFileDoesNotExist() {
        // 파일이 없을 때는 0을 반환해야 함
        int score = SaveSystem.loadHighScore();
        Assertions.assertEquals(0, score, "파일이 존재하지 않으면 최고 점수는 0이어야 합니다.");
    }

    @Test
    public void testSaveAndLoadHighScore() {
        // 새로운 최고 점수 저장
        SaveSystem.saveHighScore(500);
        
        // 다시 불러와서 점수가 일치하는지 확인
        int loadedScore = SaveSystem.loadHighScore();
        Assertions.assertEquals(500, loadedScore, "저장된 최고 점수(500)를 정확히 불러와야 합니다.");
    }
    
    @Test
    public void testDoNotOverwriteWithLowerScore() {
        // 먼저 500점 저장
        SaveSystem.saveHighScore(500);
        
        // 더 낮은 점수인 300점 저장 시도
        SaveSystem.saveHighScore(300);
        
        // 불러온 점수는 여전히 500점이어야 함
        int loadedScore = SaveSystem.loadHighScore();
        Assertions.assertEquals(500, loadedScore, "기존 최고 점수보다 낮은 점수는 덮어쓰지 않아야 합니다.");
    }
}

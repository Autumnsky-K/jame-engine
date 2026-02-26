package com.arcade.engine.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 게임 진행 데이터를 .dat 파일로 로컬 스토리지에 저장하고 불러오는 SaveSystem 클래스.
 * Object I/O Streams를 사용하여 Java 객체(Record) 자체를 바이너리 형태로 안전하게 기록합니다.
 */
public class SaveSystem {
    private static final String SAVE_FILE_NAME = "arcade_highscore.dat";

    /**
     * 파일에서 최고 점수를 불러옵니다. 파일이 없거나 에러 시 0을 반환합니다.
     */
    public static int loadHighScore() {
        File file = new File(SAVE_FILE_NAME);
        if (!file.exists()) {
            return 0; // 세이브 파일이 없으면 초기 점수 0
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ScoreData data = (ScoreData) ois.readObject();
            return data.highScore();
        } catch (Exception e) {
            System.err.println("[SaveSystem] 최고 점수를 불러오는 중 오류가 발생했습니다: " + e.getMessage());
            return 0;
        }
    }

    /**
     * 새로운 점수가 기존 점수보다 높을 경우에만 직렬화하여 파일에 기록합니다.
     */
    public static void saveHighScore(int currentScore) {
        int savedHighScore = loadHighScore();

        if (currentScore > savedHighScore) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME))) {
                oos.writeObject(new ScoreData(currentScore));
                System.out.println("[SaveSystem] 최고 점수 갱신 완료! 점수: " + currentScore);
            } catch (Exception e) {
                System.err.println("[SaveSystem] 최고 점수를 저장하는 중 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }
}

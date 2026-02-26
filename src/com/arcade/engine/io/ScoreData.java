package com.arcade.engine.io;

import java.io.Serializable;

/**
 * 게임의 최고 점수 데이터를 보관하는 불변 레코드.
 * Java의 ObjectOutputStream을 통해 파일에 직렬화하기 위해 Serializable 인터페이스를 구현합니다.
 * Java 21의 record는 자동으로 직렬화/역직렬화에 필요한 보일러플레이트 코드를 줄여줍니다.
 */
public record ScoreData(int highScore) implements Serializable {
    // 직렬화 버전 관리를 위한 serialVersionUID 권장
    private static final long serialVersionUID = 1L;
}

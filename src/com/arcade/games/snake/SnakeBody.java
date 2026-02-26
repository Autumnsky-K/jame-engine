package com.arcade.games.snake;

/**
 * 뱀의 몸통 한 칸이나 먹이의 좌표를 나타내는 불변 레코드.
 * Java 21의 record를 활용하여 데이터를 명확하고 간결하게 표현합니다.
 */
public record SnakeBody(int x, int y) {
}

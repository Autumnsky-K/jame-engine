# 🕹️ JameEngine: Java Retro Arcade Framework

**JameEngine**은 순수 Java 21 LTS와 Swing(`Graphics2D`)을 활용하여 바닥부터 구축한 경량 2D 아케이드 게임 프레임워크입니다. 

단순히 하나의 게임을 만드는 것을 넘어, **"구조의 미학(Aesthetics of Structure)"**을 추구하며 여러 아케이드 게임(스네이크, 벽돌 깨기 등)을 손쉽게 확장하고 플러그인할 수 있는 견고한 뼈대를 설계하는 데 목적을 두었습니다.

---

## 🎯 Project Highlights

- **Pure Java 21**: 외부 그래픽 라이브러리 없이 Java 표준 라이브러리(AWT/Swing)만을 사용하여 구현.
- **Robust Game Loop**: UI 스레드(EDT)와 분리된 플랫폼 스레드 기반의 **60 FPS 고정 메인 루프**.
- **Multi-Game Architecture (OCP)**: 새로운 게임을 추가할 때 기존 엔진 코드를 수정할 필요가 없는 구조(**Open-Closed Principle**). `GameLogic` 인터페이스를 구현하기만 하면 어떤 게임이든 프레임워크에 동적으로 주입됩니다.
- **Persistent Data**: Java Object Serialization을 활용한 최고 점수 로컬 저장 시스템.

---

## 🏗️ Architecture & Design Patterns

이 프로젝트는 객체지향의 핵심 원칙(SOLID)과 검증된 디자인 패턴을 적극적으로 도입했습니다.

### 1. State Pattern (상태 패턴)
게임의 흐름을 `Menu`, `Playing`, `GameOver` 등의 상태 객체로 분리했습니다.
- `StateManager`가 컨텍스트 역할을 수행하며, 메인 엔진은 현재 상태 객체에게 `update()`, `render()`, `handleInput()`을 위임(Delegation)합니다.
- 각 상태는 자신의 로직에만 집중하며, 다음 상태로의 전환을 스스로 결정합니다.

### 2. Strategy Pattern (전략 패턴)
게임 루프(`GameLoop`)는 "무엇을" 실행할지 알지 못합니다. 단지 `GameEngine` 인터페이스에 의존하여 "언제" 실행할지만 통제합니다. (의존성 역전 원칙 적용)
- 실제 화면을 그리는 `GamePanel` 역시 구체적인 게임에 의존하지 않고 현재의 상태(State)를 그리도록 분리되어 있습니다.
- `PlayingState`는 `GameLogic` 인터페이스(Strategy)를 주입받아, 스네이크나 벽돌 깨기 등 다양한 게임 로직을 유연하게 실행합니다.

### 3. Separation of Concerns (SoC)
- **Input System**: 다중 키 입력을 O(1)로 처리할 수 있는 `InputHandler`를 독립적으로 구축.
- **IO System**: `ScoreData`를 `.dat` 파일로 읽고 쓰는 직렬화 로직을 `SaveSystem`으로 캡슐화.

---

## 🚀 Java 21 Modern Features

기존의 구식 Java 문법에서 탈피하여 Java 21의 최신 기능들을 프레임워크 전반에 녹여냈습니다.

*   **`sealed interface`**: 
    상태(`GameState`)를 `sealed`로 선언하여 허용된 클래스(`MenuState`, `PlayingState`, `GameOverState`)만 상속할 수 있도록 강제했습니다.
*   **Pattern Matching for `switch`**:
    `StateManager`의 상태 전환 로그 처리 시, 위에서 정의한 `sealed` 구조 덕분에 `default` 브랜치 없이 타입 안전성을 보장하는 직관적인 스위치 문을 사용했습니다.
*   **`record` Classes**:
    불변 데이터 객체인 `SnakeBody`(좌표), `ScoreData`(점수 직렬화)뿐만 아니라, `PlayingState`와 같은 상태 컨테이너 자체도 `record`로 선언하여 보일러플레이트 코드를 완벽히 제거했습니다.
*   **`Thread.ofPlatform()`**:
    메인 게임 루프 생성 시 명시적인 스레드 빌더를 사용하여 가독성과 목적을 명확히 했습니다.

---

## 🎮 Included Games

### 1. Snake (스네이크)
- **위치**: `com.arcade.games.snake`
- **특징**: `GameLogic` 인터페이스를 구현하여 `PlayingState` 내부에 캡슐화되어 동작.
- **기능**:
  - 충돌 감지 로직 (벽, 자기 자신)
  - 동적 난이도 조절 시스템 (50점 획득 시 60FPS 루프 내 틱 속도 단계적 증가)
  - 최고 점수 파일 저장 연동

### 2. Brick Breaker (벽돌 깨기)
- **위치**: `com.arcade.games.breakout`
- **특징**: 독립된 객체(Ball, Paddle, Brick)들의 상호작용으로 이루어진 물리 기반 아케이드 게임.
- **기능**:
  - **정교한 물리 충돌 로직**: 공이 패들의 어느 부위(중앙, 가장자리)에 부딪히느냐에 따라 반사 각도(수평 가속도)가 동적으로 변화.
  - **객체 리스트 관리**: 생성된 다수의 벽돌(`Brick`) 객체들을 `List`로 관리하며, 매 프레임 충돌 여부를 검사하고 파괴된 객체는 렌더링에서 제외.
  - 스테이지 클리어 시 다음 레벨 진행 및 난이도(벽돌 수) 증가.

---

## ⚙️ How to Run

1. JDK 21 이상이 설치되어 있어야 합니다.
2. 프로젝트를 클론한 뒤 소스 코드를 컴파일합니다.
   ```bash
   javac -d out/production/JameEngine $(find src -name "*.java")
   ```
3. 메인 클래스를 실행합니다.
   ```bash
   java -cp out/production/JameEngine Main
   ```

---

## 📂 Directory Structure

```text
src/
├── Main.java
└── com.arcade/
    ├── engine/
    │   ├── ArcadeGame.java      # 엔진 조립 (Core)
    │   ├── GameLogic.java       # 다중 게임 구현을 위한 Strategy 인터페이스
    │   ├── GameLoop.java        # 60 FPS 스레드 
    │   ├── GameEngine.java      # 렌더/업데이트 인터페이스
    │   ├── display/             # JFrame, JPanel (Double Buffering)
    │   ├── input/               # 다중 키 입력 핸들링
    │   ├── io/                  # 파일 직렬화 (SaveSystem)
    │   └── state/               # State Pattern 코어 (StateManager, GameState)
    └── games/
        ├── snake/               # 스네이크 게임 구현체
        └── breakout/            # 벽돌 깨기 게임 구현체 (Ball, Paddle, Brick 등)
```
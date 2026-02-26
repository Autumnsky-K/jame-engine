# 🕹️ Project: Java Retro Arcade Framework (Java 21)

## 🎯 Project Goal
- **핵심 목표**: 디자인 패턴을 활용한 확장성 높은 게임 프레임워크 구축.
- **기술 스택**: Java 21 LTS (Swing, Graphics2D, Multi-threading).
- **데이터 저장**: Java Serialization 또는 Text File I/O를 활용한 로컬 저장.

## 🏗️ Architecture Philosophy (구조의 미학)
1. **Separation of Concerns (SoC)**: 렌더링 로직과 비즈니스 로직(게임 규칙)을 엄격히 분리한다.
2. **State Pattern**: `Menu`, `Playing`, `GameOver` 상태를 명확히 분리하여 관리한다.
3. **Strategy Pattern**: 새로운 게임(테트리스, 스네이크 등) 추가 시 기존 엔진 코드를 수정하지 않도록 인터페이스 기반으로 설계한다.

## 🛠️ Java 21 Modern Features to Use
- **Records**: `Point`, `ScoreData` 등 불변 데이터 모델에 사용.
- **Sealed Interfaces**: `GameState` 등을 정의하여 타입 안전성 확보.
- **Pattern Matching for switch**: 상태 전환 로직 가독성 향상.

## 📂 Project Structure (Expected)
- `com.arcade.engine`: 메인 루프, 윈도우 관리, 입력 시스템.
- `com.arcade.games`: 개별 게임 구현체 (Tetris, Snake 등).
- `com.arcade.model`: Record 및 공통 데이터 객체.
- `com.arcade.util`: 파일 저장 및 리소스 로더.

## 📝 Gemini Collaboration Rules
- **코드 생성 시**: 항상 객체지향 원칙(SOLID)을 준수할 것.
- **주석**: 핵심 로직과 디자인 패턴 적용 이유를 주석으로 설명할 것.
- **Java 21 전용**: 구식 문법보다 Java 21의 최신 기능을 우선적으로 제안할 것.
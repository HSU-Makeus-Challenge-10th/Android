# Codex Project Guide

## 파일 인코딩 기준

- 현재 프로젝트의 Kotlin, XML, Markdown 파일은 BOM 없는 UTF-8을 기준으로 작성한다.
- 새 파일을 만들거나 기존 파일을 수정할 때도 BOM 없는 UTF-8을 유지한다.
- 터미널, 에디터, 도구 출력에서 한글이 깨져 보이더라도 파일 자체가 UTF-8로 정상 저장되어 있을 수 있다.
- 한글이 깨져 보이는 경우 먼저 파일 인코딩, 에디터 표시 인코딩, 터미널 출력 인코딩을 확인한다.
- 인코딩 확인 없이 한글 문자열을 임의로 다시 입력하거나 변환하지 않는다.

## 프로젝트 방향

현재 프로젝트의 아키텍처 구조는 MVI(Model-View-Intent)를 기준으로 맞춘다.
UI 구현 기술 스택은 Jetpack Compose를 사용한다.

## 아키텍처 규칙

- `Model`은 화면 상태와 비즈니스 데이터를 표현한다.
- `View`는 Jetpack Compose 기반 UI를 담당하며, 상태를 표시하고 사용자 입력을 Intent로 전달한다.
- `Intent`는 사용자의 행동이나 화면에서 발생한 이벤트를 표현한다.
- `ViewModel`은 Intent를 처리하고, 상태를 갱신하며, View에 단방향 데이터 흐름을 제공한다.
- 화면 상태는 가능하면 하나의 immutable state 객체로 관리한다.
- UI에서는 직접 비즈니스 로직을 처리하지 않는다.
- 상태 변경은 ViewModel 또는 명확히 분리된 상태 관리 계층에서 수행한다.

## 기술 스택

- Android
- Kotlin
- Jetpack Compose
- ViewModel
- Kotlin Coroutines / Flow
- MVI Architecture

## 코드 컨벤션

- 변수명, 함수명, 프로퍼티명은 camelCase를 준수한다.
- 클래스명, 인터페이스명, sealed class명은 PascalCase를 사용한다.
- 상수는 프로젝트의 기존 규칙을 우선 따르되, 별도 규칙이 없다면 `UPPER_SNAKE_CASE`를 사용한다.
- 함수는 하나의 역할에 집중하도록 작성한다.
- 중복 로직은 공통 함수나 확장 함수로 분리한다.
- Compose 함수는 상태를 직접 생성하기보다 상위에서 전달받는 구조를 우선한다.
- 파일과 패키지 구조는 기존 프로젝트 구조를 우선 존중한다.

## 함수 주석 규칙

각 함수 위에는 간단한 한글 설명을 작성한다.

예시:

```kotlin
// 사용자의 저장 요청을 처리한다.
fun handleSaveClick() {
    ...
}
```

- 주석은 함수의 목적을 짧고 명확하게 설명한다.
- 코드만 보아도 명확한 경우에도 프로젝트 일관성을 위해 간단한 설명을 유지한다.
- 불필요하게 긴 설명이나 구현 내용을 반복하는 주석은 피한다.

## Jetpack Compose 작성 기준

- 화면은 가능한 작은 Composable 단위로 분리한다.
- Composable 함수명은 PascalCase를 사용한다.
- 상태는 상위 Composable 또는 ViewModel에서 관리하고, 하위 Composable에는 필요한 값과 이벤트 콜백만 전달한다.
- Preview가 필요한 UI는 `@Preview`를 적극 활용한다.
- UI 이벤트는 ViewModel의 Intent 처리 함수로 전달한다.
- Compose 내부에서 직접 네트워크, DB, 복잡한 비즈니스 로직을 실행하지 않는다.

## MVI 예시 구조

```kotlin
data class ExampleState(
    val isLoading: Boolean = false,
    val title: String = "",
    val errorMessage: String? = null
)

sealed interface ExampleIntent {
    data object Load : ExampleIntent
    data class TitleChanged(val title: String) : ExampleIntent
    data object SaveClicked : ExampleIntent
}
```

## 한글 처리 주의사항

- 프로젝트에 작성된 한글이 깨져 보일 수 있으나, 실제로는 정상적으로 작성된 한글일 수 있다.
- 한글 텍스트를 수정, 삭제, 추가할 때는 반드시 한 번 더 확인한다.
- 기존 한글 문구가 깨져 보인다는 이유만으로 임의 수정하지 않는다.
- 한글 리소스, 주석, UI 문구를 변경해야 하는 경우 사용자의 확인을 받은 뒤 반영한다.
- 문자열 인코딩 문제로 의심되는 경우 파일 인코딩과 실제 표시 환경을 먼저 확인한다.

## 작업 시 주의사항

- 기존 코드 스타일과 폴더 구조를 우선 따른다.
- 사용자가 명시하지 않은 대규모 리팩터링은 진행하지 않는다.
- 기능 변경 시 관련 상태, Intent, ViewModel, UI 흐름이 함께 맞는지 확인한다.
- UI 문구 변경은 사용자 경험에 영향을 줄 수 있으므로 신중하게 처리한다.
- 테스트 또는 빌드가 가능한 변경이라면 가능한 범위에서 검증한다.
- 사용자의 기존 변경사항을 임의로 되돌리지 않는다.

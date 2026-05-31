# 10주차 릴리즈 AAB

## 빌드 명령

```bash
.\gradlew.bat bundleRelease
```

## AAB 파일 경로 (PR에 기재)

```
app/build/outputs/bundle/release/app-release.aab
```

## 서명 설정

1. `keystore.properties.example` → `keystore.properties` 복사 후 값 입력
2. Android Studio: **Build → Generate Signed App Bundle / APK** 로 Keystore 생성 가능
3. `local.properties`에 `REQRES_API_KEY` 설정

## 코드 리뷰 반영 사항 (Week10)

| 항목 | 개선 내용 |
| --- | --- |
| UiState | `data class` → `sealed interface` (Loading/Success/Error) |
| 생명주기 | `collectAsState` → `collectAsStateWithLifecycle` |
| 네트워크 | 프로필·팔로잉 `async` 병렬 로딩 |
| 보안 | API 키를 `BuildConfig` + `local.properties`로 분리 |
| UX | 에러 화면에 **다시 시도** 버튼 추가 |

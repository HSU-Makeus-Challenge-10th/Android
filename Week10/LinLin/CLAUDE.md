# 프로젝트 개요

UMC 10주차 Nike 스타일 **마이페이지** Android 앱입니다.  
9주차에서 구현한 Jetpack Compose UI(프로필, 팔로잉 HorizontalPager, ModalBottomSheet)를 기반으로, 10주차에서는 **Play Store 배포용 AAB 빌드**와 **AI 개발 워크플로우**를 적용합니다.

# 아키텍처

실무형 Clean Architecture의 축소 버전을 따릅니다.

- **data**: `NetworkClient`, Retrofit `ReqResService`, `UserRepository`, DTO(`UserData`)
- **ui/mypage**: `MyPageViewModel`, `MyPageScreen` 및 하위 Composable
- **ui/theme**: Material3 테마

의존성 방향: `ui` → `data` (domain 레이어는 미션 범위에서 생략)

# 기술 스택

- UI: Jetpack Compose (Material3), HorizontalPager, ModalBottomSheet
- 상태 관리: ViewModel + `StateFlow` + `collectAsStateWithLifecycle`
- 네트워크: Retrofit2 + OkHttp3 + Gson (ReqRes API)
- 이미지: Coil Compose
- 비동기: Kotlin Coroutines (`async` 병렬 호출)
- 빌드: AGP 9.x, release AAB 서명 (`keystore.properties`)

# 코드 컨벤션

- ViewModel UI 상태는 **sealed interface**로 관리 (`Loading` / `Success` / `Error`)
- Composable 함수명은 PascalCase, 파일명과 일치
- Repository는 `suspend` + `Result<T>` (`runCatching`) 패턴
- API 키·Keystore 정보는 `local.properties` / `keystore.properties`에만 두고 Git에 올리지 않음
- 릴리즈 빌드에서는 OkHttp 로깅 비활성화 (`BuildConfig.DEBUG` 기준)

# 빌드 & 배포

```bash
# Windows (프로젝트 루트 Week10)
.\gradlew.bat bundleRelease
```

AAB 출력 경로:

```
app/build/outputs/bundle/release/app-release.aab
```

# 주의사항

- `*.jks`, `keystore.properties`, `local.properties`, `signing/` 디렉터리는 **절대 커밋하지 말 것**
- Keystore 분실 시 동일 앱 업데이트 불가 — 안전한 로컬/클라우드 백업 필수
- 미션 브랜치: `feature/week10-{기능명}` 권장
- Play Store 업로드는 **AAB**만 사용 (APK는 사이드로딩·테스트용)

# AI 협업 원칙 (Harness Engineering)

- 불확실하면 추측하지 말고 확인할 것
- 요청 범위만 최소 변경으로 구현할 것
- 관련 없는 파일은 수정하지 말 것
- 새 화면 추가 시 Manifest·네비게이션·ViewModel 상태 패턴을 함께 점검할 것

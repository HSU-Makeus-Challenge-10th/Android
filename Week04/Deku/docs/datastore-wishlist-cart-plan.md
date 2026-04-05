# DataStore 전환 구현 가이드

## 현재 구조 진단

- 상품 원본은 `app/src/main/java/com/example/week02/data/ItemData.kt`의 하드코딩 리스트입니다.
- 상태 원본은 `app/src/main/java/com/example/week02/shop/ProductViewModel.kt`의 `MutableLiveData(ItemData.products)` 하나뿐입니다.
- 위시리스트는 이미 `isWish` 기반으로 동작합니다.
  - 목록 하트: `ShopProductAdapter` -> `toggleWish()`
  - 상세 하트 버튼: `DetailFragment` -> `toggleWish()`
  - 위시리스트 화면: `WishListFragment`에서 `products.filter { it.isWish }`
- 장바구니는 아직 데이터 계층이 없습니다.
  - `fragment_detail.xml`에는 `btnAddToCart`가 있지만 클릭 로직이 없습니다.
  - `CartFragment`는 비어 있는 화면만 보여줍니다.
- 홈은 `HomeFragment`가 `ItemData.latestProducts()`를 직접 사용해서 ViewModel/DataStore 흐름을 우회합니다.

## 먼저 정리해야 할 구조 문제

### 1. `ItemList` 모델이 영속화용으로 바로 쓰기 애매함

현재 `ItemList`는 다음 문제가 있습니다.

- `price`가 `String`이라 장바구니 수량/합계 계산이 어렵습니다.
- `imageResId`는 앱 내부 리소스 정수값이라 저장 데이터로 오래 들고 있기엔 안정적이지 않습니다.
- `isWish`가 상품 자체 속성처럼 들어가 있는데, 실제로는 사용자 상태입니다.

### 2. DataStore에 "무엇"을 저장할지 분리해야 함

권장 구조는 아래입니다.

- 상품 카탈로그: 앱 기본 데이터
- 위시리스트/장바구니: 사용자 상태

즉, DataStore에는 전부를 넣기보다 "사용자 상태"를 넣는 쪽이 더 맞습니다.

추천 저장 대상:

- `wishProductIds: Set<Int>` 또는 `List<Int>`
- `cartItems: List<CartItem>`

상품 자체는 아래 둘 중 하나로 두는 편이 안전합니다.

1. 지금처럼 앱 내부 seed 데이터로 유지
2. `assets` 또는 `raw`의 JSON 파일로 이동 후 최초 로드

과제 요구 때문에 "더미 데이터를 DataStore로 대체"해야 한다면:

- 최초 1회 seed JSON을 읽어서 DataStore에 넣고
- 이후에는 DataStore에서 읽는 방식으로 구현하면 됩니다.

단, 그 경우에도 `imageResId` 대신 `imageName` 같은 키 문자열을 저장하고, UI에서 `R.drawable`로 매핑하는 편이 안전합니다.

## 추천 데이터 모델

### 상품 모델

`ItemList`를 그대로 쓰기보다 `Product` 성격으로 정리하는 편이 좋습니다.

- `id: Int`
- `name: String`
- `price: Int` 또는 `Long`
- `imageKey: String`
- `category: String`
- `colorCount: Int`
- `description: String`

표시용 가격 문자열은 UI에서 포맷팅합니다.

### 장바구니 모델

- `productId: Int`
- `quantity: Int`
- `selectedSize: String?`

지금 상세 화면에 사이즈 버튼이 있지만 실제 선택 상태가 없어서, 사이즈를 쓸 거면 이 모델이 필요합니다.
사이즈를 아직 안 할 거면 일단 `selectedSize` 없이 시작해도 됩니다.

### 사용자 상태 모델

- `wishProductIds: List<Int>`
- `cartItems: List<CartItem>`

또는 한 번에 저장하려면 아래처럼 묶어도 됩니다.

- `AppUserState`
  - `wishProductIds`
  - `cartItems`

## 기술 선택

가장 단순한 방향은 아래입니다.

- `Preferences DataStore`
- `kotlinx.serialization`
- JSON 문자열로 데이터 클래스 직렬화/역직렬화

필요한 추가 항목:

- DataStore dependency
- kotlinx serialization JSON dependency
- serialization plugin
- lifecycle runtime/viewmodel ktx

## 새로 필요할 계층

### 1. DataStore 래퍼

예상 책임:

- 위시리스트 JSON 읽기/쓰기
- 장바구니 JSON 읽기/쓰기
- 필요하면 상품 목록 JSON 읽기/쓰기

예상 파일 예시:

- `data/local/AppPreferencesDataStore.kt`
- `data/local/JsonStorage.kt`

### 2. Repository

지금 `ProductViewModel`이 직접 상태를 들고 있는데, 이 역할은 Repository로 내려야 합니다.

예상 책임:

- 상품 목록 로드
- 위시리스트 토글
- 장바구니 추가/삭제/수량 변경
- 앱 시작 시 저장된 상태 복원
- 상품 목록과 사용자 상태를 합쳐 UI 모델 생성

예상 파일 예시:

- `data/repository/ProductRepository.kt`

### 3. ViewModel

`ProductViewModel`은 메모리 리스트 소유자에서 "UI 상태 중계자"로 바뀌어야 합니다.

예상 책임:

- `products`
- `wishlistProducts`
- `cartItems`
- `cartTotalPrice`
- `toggleWish(productId)`
- `addToCart(productId)`
- `removeFromCart(productId)`
- `changeQuantity(productId, quantity)`

LiveData로 계속 가도 되지만, DataStore를 붙이면 `Flow/StateFlow`가 더 자연스럽습니다.

## 파일별로 구현해야 할 것

### `app/build.gradle.kts`

- DataStore dependency 추가
- JSON serialization dependency 추가
- 필요시 Kotlin serialization plugin 적용
- coroutine/lifecycle ktx 보강

### `app/src/main/java/com/example/week02/data/ItemList.kt`

- 직렬화 가능한 형태로 재설계
- `price`를 숫자형으로 변경
- `imageResId` 대신 저장 가능한 키로 교체하는지 결정
- `isWish`를 상품 모델에서 제거할지 결정

추천:

- 상품 모델에는 `isWish`를 두지 않고
- UI 조합 단계에서 `isWish`를 붙여서 보여주기

### `app/src/main/java/com/example/week02/data/ItemData.kt`

- 더 이상 런타임 상태 원본으로 쓰지 않기
- 역할을 아래 중 하나로 축소
  - 최초 seed 제공자
  - asset/raw JSON 로더

### `app/src/main/java/com/example/week02/shop/ProductViewModel.kt`

- `MutableLiveData(ItemData.products)` 제거
- Repository 주입 또는 생성
- 저장소에서 읽은 데이터로 화면 상태 노출
- `toggleWish()`에서 DataStore까지 저장
- `addToCart()` 신규 추가

### `app/src/main/java/com/example/week02/home/HomeFragment.kt`

- `ItemData.latestProducts()` 직접 호출 제거
- ViewModel/Repository 기준 최신 상품 사용

지금 이 파일은 저장 구조를 우회하고 있어서, 그대로 두면 앱 재시작 후에도 홈만 다른 데이터를 볼 수 있습니다.

### `app/src/main/java/com/example/week02/shop/AllFragment.kt`
### `app/src/main/java/com/example/week02/shop/TopsFragment.kt`
### `app/src/main/java/com/example/week02/shop/ShoesFragment.kt`

- 현재는 그대로 재사용 가능
- 단, ViewModel 데이터 소스를 DataStore 기반으로 바꿔야 함

### `app/src/main/java/com/example/week02/shop/DetailFragment.kt`

- `btnWishList`는 현재 토글만 하므로 유지 가능
- `btnAddToCart` 클릭 연결 필요
- 장바구니 추가 후 버튼 상태/토스트/중복 처리 규칙 정하기

정해야 하는 정책:

- 같은 상품 재추가 시 수량 증가 여부
- 사이즈 미선택이면 추가 막을지 여부

### `app/src/main/java/com/example/week02/wishList/WishListFragment.kt`

- 지금 구조는 이미 거의 맞습니다
- 변경 포인트는 "메모리 리스트 필터"가 아니라 "저장소 기반 상태"를 관찰하도록 바꾸는 것

현재는 이미 하트 누르면 위시리스트 화면에 보이는 구조 자체는 맞습니다.
문제는 앱 종료 후 유지가 안 된다는 점뿐입니다.

### `app/src/main/java/com/example/week02/cart/CartFragment.kt`

가장 많이 바뀌어야 합니다.

- 빈 화면만 있는 상태에서 실제 장바구니 리스트 UI 추가
- `RecyclerView`와 cart adapter 추가
- 빈 상태 / 리스트 상태 분기
- 총 금액 표시
- 수량 변경/삭제 버튼 연결
- 저장된 장바구니 복원

## 실제 구현 순서

1. 모델 분리
2. DataStore + JSON 저장소 구현
3. Repository 구현
4. `ProductViewModel`을 저장소 기반으로 변경
5. `DetailFragment`에 `addToCart()` 연결
6. `WishListFragment`를 저장 상태 기반으로 연결
7. `CartFragment` RecyclerView UI 구현
8. `HomeFragment`의 직접 더미 접근 제거
9. 앱 재실행 테스트

## 최소 기능 기준

이 정도가 되면 목표 달성입니다.

- 하트 버튼을 누르면 위시리스트에 즉시 반영됨
- 앱 종료 후 재실행해도 위시리스트가 유지됨
- 상세의 장바구니 추가 버튼이 실제로 저장됨
- 장바구니 화면에서 저장된 상품 목록이 보임
- 앱 종료 후 재실행해도 장바구니가 유지됨

## 구현 시 주의할 점

### 1. DataStore는 작은 데이터에 적합함

상품 카탈로그 전체를 계속 JSON으로 저장하는 건 가능은 하지만 이상적인 구조는 아닙니다.
과제 요구가 아니라면 DataStore에는 사용자 상태만 넣는 편이 낫습니다.

### 2. `imageResId`는 저장용 값으로 쓰지 않는 편이 안전함

리소스 정수값은 앱 내부 참조값이라 장기 저장 모델에 넣기보다 `imageKey`를 두고 매핑하는 편이 좋습니다.

### 3. 가격은 숫자로 바꾸는 게 맞음

장바구니 합계, 수량 계산, 할인 계산 때문에 `String` 가격은 유지비가 큽니다.

### 4. 홈도 같은 데이터 소스를 써야 함

지금 홈만 `ItemData`를 직접 쓰므로, 나중에 한 화면만 데이터가 안 맞는 문제가 생길 수 있습니다.

## 지금 기준으로 우선순위

우선순위 1:

- DataStore + JSON 저장 구조 만들기
- 위시리스트 토글 영속화
- 장바구니 모델 만들기
- 상세의 장바구니 추가 연결

우선순위 2:

- CartFragment 리스트 UI
- 수량/삭제 기능
- 홈 화면 데이터 소스 통일

우선순위 3:

- 최초 seed 전략 정리
- 사이즈 선택 저장
- 테스트 코드 추가

## 한 줄 결론

지금 구조에서 핵심은 "더미 리스트를 바로 DataStore로 바꾸는 것"보다,
"상품 카탈로그"와 "사용자 상태(위시리스트/장바구니)"를 분리하고,
후자를 DataStore JSON으로 저장하도록 `Repository + ViewModel` 구조를 세우는 것입니다.

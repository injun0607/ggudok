# 꾸독 : 나만의 구독 비서

**꾸독**은 사용자들에게 다양한 구독 서비스를 손쉽게 찾아보고 관리할 수 있는 플랫폼입니다. 사용자는 메인페이지에서 이벤트 배너, 전체 카테고리, 추천 구독 서비스 리스트를 확인하며, 각종 필터와 통합검색을 통해 원하는 구독 서비스를 찾을 수 있습니다. 또한, 회원가입 및 로그인을 통해 개인화된 마이페이지에서는 관심 상품, 구독내역, 그리고 구독 서비스 비교 기능을 활용할 수 있습니다. 또한, 관리자 페이지는 효율적으로 구독 서비스와 태그, 카테고리를 관리하고 이벤트를 운영할 수 있습니다.



## 👨‍💼👩‍💼 팀원

- **프론트엔드**: [최서희]
- **백엔드**: [여인준]

## 🔎 기술 스택

- **프론트엔드**: React, JavaScript, CSS
- **백엔드**: Java, Spring Boot



## 📁 프로젝트 구조

### 프론트엔드 구조

프로젝트의 프론트엔드는 React로 개발되었으며, 주요 컴포넌트들은 `components` 디렉토리에 위치합니다. 각 페이지는 `pages` 디렉토리에서 모듈화되어 관리되고, Redux를 통한 전역 상태 관리와 모듈화된 스타일은 `redux` 및 `styles` 디렉토리에서 각각 관리됩니다.

```plaintext
frontend
└─ src
   ├─ App.js
   ├─ index.js
   ├─ components
   │  ├─ Admin
   │  │  ├─ AdminFooter.js
   │  │  ├─ AdminHeader.js
   │  │  └─ AdminLayout.js
   │  ├─ dataItem.json
   │  ├─ dataItemDetail.json
   │  ├─ dataMyReview.json
   │  ├─ Error.js
   │  ├─ ErrorItem.js
   │  ├─ ErrorLogin.js
   │  ├─ Filteraside.js
   │  ├─ Footer.js
   │  ├─ Header.js
   │  ├─ Layout.js
   │  ├─ Loading.js
   │  ├─ Paging.js
   │  ├─ Quit.js
   │  ├─ Scrolltop.js
   │  ├─ Slider.js
   │  └─ Slider_legacy.js
   ├─ pages
   │  ├─ Admin
   │  │  ├─ AdminHome.js
   │  │  ├─ Category.js
   │  │  ├─ CategoryCreate.js
   │  │  ├─ CategoryEdit.js
   │  │  ├─ EventCreate.js
   │  │  ├─ EventEdit.js
   │  │  ├─ Events.js
   │  │  ├─ Items.js
   │  │  ├─ ItemsCreate.js
   │  │  ├─ ItemsEdit.js
   │  │  ├─ TagCreate.js
   │  │  └─ Tags.js
   │  ├─ Auth
   │  │  ├─ EditProfile.js
   │  │  ├─ Join.js
   │  │  ├─ JoinAfter.js
   │  │  ├─ JoinEmail.js
   │  │  └─ Login.js
   │  ├─ Compare.js
   │  ├─ Contactus.js
   │  ├─ Event.js
   │  ├─ FeaturedItemlist.js
   │  ├─ Home.js
   │  ├─ ItemDetail.js
   │  ├─ Itemlist.js
   │  ├─ Mypage
   │  │  ├─ MyLike.js
   │  │  ├─ Mypage.js
   │  │  ├─ MyReview.js
   │  │  └─ MySubscribe.js
   │  ├─ SearchItemlist.js
   │  └─ Subscribe
   │     ├─ AddSubs.js
   │     └─ DelSubs.js
   ├─ redux
   │  ├─ actions
   │  │  ├─ admin
   │  │  │  ├─ adminCategoryActions.js
   │  │  │  ├─ adminEventsActions.js
   │  │  │  ├─ adminItemsActions.js
   │  │  │  └─ adminTagsActions.js
   │  │  ├─ adminLayoutActions.js
   │  │  ├─ categoryActions.js
   │  │  ├─ compareActions.js
   │  │  ├─ cookieActions.js
   │  │  ├─ darkModeActions.js
   │  │  ├─ eventActions.js
   │  │  ├─ filterActions.js
   │  │  ├─ itemActions.js
   │  │  ├─ paymentActions.js
   │  │  ├─ reviewActions.js
   │  │  ├─ searchActions.js
   │  │  ├─ subscribeActions.js
   │  │  └─ userActions.js
   │  ├─ reducers
   │  │  ├─ admin
   │  │  │  ├─ adminCategoryReducer.js
   │  │  │  ├─ adminEventsReducer.js
   │  │  │  ├─ adminItemsReducer.js
   │  │  │  └─ adminTagsReducer.js
   │  │  ├─ adminLayoutReducer.js
   │  │  ├─ categoryReducer.js
   │  │  ├─ compareReducer.js
   │  │  ├─ darkModeReducer.js
   │  │  ├─ eventReducer.js
   │  │  ├─ filterReducer.js
   │  │  ├─ itemReducer.js
   │  │  ├─ paymentReducer.js
   │  │  ├─ reviewReducer.js
   │  │  ├─ rootReducer.js
   │  │  ├─ searchReducer.js
   │  │  ├─ subscribeReducer.js
   │  │  └─ userReducer.js
   │  └─ store.js
   └─ styles
      ├─ Admin
      │  ├─ AdminFooter.module.css
      │  ├─ AdminHeader.module.css
      │  └─ AdminLayout.module.css
      ├─ Admin.module.css
      ├─ Auth.module.css
      ├─ Compare.module.css
      ├─ Filter.module.css
      ├─ Footer.module.css
      ├─ global.css
      ├─ globalResponsive.css
      ├─ Header.module.css
      ├─ Home.module.css
      ├─ Item.module.css
      ├─ Layout.module.css
      ├─ Mypage.module.css
      └─ reset.css
```



## 💡 기능

### 사용자 페이지

1. 메인페이지

- 이벤트 배너
- 전체 카테고리 표시
- 추천 구독 서비스 리스트
  - 성별, 나이
  - 많이 구독 중인 태그

2. 카테고리 페이지

- 카테고리별 구독 서비스 리스트 표시
- 필터 옵션
  - 가격
  - 평점
  - 태그

3. 검색 페이지

- 통합 검색 기능
- 검색 결과에 따른 필터 옵션

4. 베스트 페이지

- 인기 있는 구독 서비스 표시

5. Contact Us 페이지

- 이메일로 메일 보내기 기능 구현

6. 회원가입 / 로그인 페이지

- 간편 회원가입 / 로그인 구현 (소셜로그인: 카카오톡, 네이버)
- 기본 회원가입 / 로그인 구현

7. 마이페이지

- 회원정보 수정 기능
- 관심 상품 목록 표시
- 구독내역 페이지
  - 큰 형식: 받고 있는 상품 (ex. 셔츠 5개, OTT)
  - 작은 형식: 서비스명 / 요금제(가격) _무료이용중
  - 구독 중인 요금 정보 표시 (총 가격) 한 달 기준

8. 구독 서비스 비교 페이지

- 선택한 구독 서비스 비교 기능

### 관리자 페이지
*어드민 계정만 접근 가능*

1. 구독 서비스 관리

- 구독 서비스 목록 조회
- 각 구독 서비스의 세부 정보 조회

2. 카테고리 관리

- 카테고리 목록 조회 및 추가

3. 태그 관리

- 태그 목록 조회 및 추가

4. 이벤트 관리

- 이벤트 배너 관리 페이지
- 이벤트 정보 조회 및 추가



## ✨ 설치 및 실행

프로젝트를 실행하기 위한 간단한 가이드를 작성하세요.

1. **프론트엔드:**

```bash
cd frontend
npm install
npm start
```

2. **백엔드:**

```bash
cd backend
./mvnw spring-boot:run
```


## 📑 소스 출처

> Icons by Orion Icon Library - https://orioniconlibrary.com,
> Images by Unsplash - https://unsplash.com/
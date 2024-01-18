# 올x브영
--- 간단한 콘솔 프로그램을 개발하는 프로젝트입니다.

## 📍 프로젝트 소개
화장품 하나 알아보기 위해 제조사/배급사 사이트를 일일이 찾아가며 들어가는게 불편한 사람들을 위해,\
화장품의 특정 성분이 안맞는 사람들을 위해,\
모든 브랜드의 상품들을 모아놓고 성분도 볼 수 있는 손쉬운 프로그램을 만들었습니다.

## 📍 주요 기능

### 🙍‍♂️ UserMenu
	* 로그인을 할 수 있습니다.
			- 아이디와 비밀번호가 일치해야 합니다
			- 아이디 : admin, 비밀번호 : admin123!으로 입력할 경우 관리자 메뉴로 이동합니다.

	* 회원가입을 할 수 있습니다.
			- 아이디 중복이 검사 기능이 있습니다
			- 비밀번호 유효성 검사(8자 이상, 16자 이하 / 영문, 숫자, 특수문자 1자 사용)

### 🙍‍♂️ MainMenu
	* 상세 기능으로 넘어가는 분기 메뉴입니다.

	* 제품 목록보기로 넘어갑니다.

	* 제품 검색하기로 넘어갑니다.

	* 리뷰 게시판으로 넘어갑니다.

	* 마이 페이지로 넘어갑니다.

	* 로그아웃을 할 수 있습니다.
				- 로그인 메뉴 화면(UserMenu)으로 돌아갑니다.

### 🙍‍♂️ ProductMenu
	* 제품을 목록별로 볼 수 있습니다.
			- 전체 제품을 볼 수 있습니다.

			- 카테고리별로 제품을 볼 수 있습니다.
					- 스킨, 로션, 클렌징, 바디워시, 샴푸로 나눠져 있습니다.

			- 피부타입별로 제품을 볼 수 있습니다.
					- 지성, 건성, 민감성 추천/비추천으로 나눠져 있습니다.

	* 검색기능으로 이동할 수 있습니다
	
	* 메인화면으로 돌아갈 수 있습니다.

### 🙍‍♂️ SearchMenu
	* 제품을 제품명으로 검색할 수 있습니다.
			- 입력한 키워드를 포함하고 있는 제품을 모두 출력합니다.

	* 제품을 성분명으로 검색할 수 있습니다.
			- 입력한 키워드와 일치하는 성분을 가지고 있는 제품만을 출력합니다.

	* 브랜드명으로 검색할 수 있습니다.
			- 입력한 키워드와 일치하는 브랜드의 제품만을 출력합니다.

	* 가격대를 지정해 검색할 수 있습니다.
			- 최소값과 최대값을 정하고 그 사이의 제품들을 출력합니다.

### 🙍‍♂️ CartMenu
	* 제품 목록에서 장바구니에 담은 제품을 확인할 수 있습니다.
			- 장바구니 번호, 제품명, 수량을 확인할 수 있습니다.

	* 장바구니에 담은 제품을 바로 구매할 수 있습니다.
			- 구매 시에 적립금을 사용할 수 있습니다.	

### 🙍‍♂️ ReviewMenu
	* 전체 리뷰, 해당 사용자 리뷰, 리뷰작성, 리뷰 검색(제품 키워드, 리뷰 제목 키워드) 기능을
		이용할 수 있습니다.

	* 해당 사용자의 구매내역에 따라서 리뷰를 작성할 수 있습니다.
			- 이미 리뷰가 작성된 구매내역에는 더 이상 리뷰를 작성할 수 없습니다.

### 🙍‍♂️ AdminMenu
	* 전체 회원 목록을 조회할 수 있습니다.

	* 탈퇴한 회원들의 목록을 조회할 수 있습니다.

	* 전체 매출을 볼 수 있습니다.

	* 판매 제품을 추가, 수정, 삭제할 수 있습니다.
			- 추가 : 제품명, 제품코드, 카테고리, 브랜드, 가격, 수량, 성분을 입력해야합니다.
					- 제품코드, 성분에 대한 유효성 검사(중복 유무)가 이루어집니다.
			- 수정 : 제품명, 제품코드, 카테고리, 브랜드명, 가격, 수량을 변경할 수 있습니다.

## 📍 Team 소개
	* 팀장 이민정
			- UserMenu, CartMenu, ReviewMenu 구현 담당
			- 코드 파일 통합 및 오류 수정
			- 프로젝트 업무 할당 및 스케쥴 관리

	* 팀원 이재준
			- ProductMenu, SearchMenu, adminMenu 구현 담당
			- Test 데이터 수집

## 📍 기술 스택
#### Environment
![Eclipse](https://img.shields.io/badge/Eclpse-orange?style=flat&logo=eclipse)

#### Development
![Java](https://img.shields.io/badge/java-lightgray?style=flat&logo=data:image/png;base64,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)

#### Database
![Oracle](https://img.shields.io/badge/Oracle-4479A1?style=flat&logo=oracle&logoColor=red)

#### Communication
![Discord](https://img.shields.io/badge/discord-5865F2?style=flat&logo=discord&logoColor=white)
![Notion](https://img.shields.io/badge/notion-000000?style=flat&logo=notion&logoColor=white)

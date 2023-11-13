package com.sh.user.view;

import java.util.Scanner;

import com.sh.product.view.ProductListMenu;
import com.sh.product.view.SearchMenu;
import com.sh.review.view.ReviewMenu;
/**
 * 로그인 후 이동하는 메인메뉴
 *
 */
public class MainMenu {
	private Scanner sc = new Scanner(System.in);
	private MyPageMenu myPageMenu = new MyPageMenu();
	private ReviewMenu reviewMenu = new ReviewMenu();
	private ProductListMenu productListMenu = new ProductListMenu();
	private SearchMenu searchMenu = new SearchMenu();
	
	private String mainMenu = """
							======== 📌 메인 메뉴 📌 ========
							1. 제품 목록보기
							2. 제품 검색하기
							3. 리뷰 게시판
							4. 마이페이지
							0. 로그아웃
							===============================
							[선택] : """;
	/**
	 * switch문으로 화면 이동
	 */
	public void mainMenu(String id) {
		while (true) {
			System.out.println();
			System.out.print(mainMenu);
			String choice = sc.next();

			switch (choice) {
			case "1": productListMenu.productListMenu(id); break;
			case "2": searchMenu.searchMenu(id); break;
			case "3": reviewMenu.displayReviewMenu(id); break;
			case "4": myPageMenu.displayMyPageMenu(id); break;
			case "0": return; // 로그인 화면으로 돌아가기
			default: System.out.println("🚨 잘못 입력하셨습니다."); break;
			}
		}
	}
}

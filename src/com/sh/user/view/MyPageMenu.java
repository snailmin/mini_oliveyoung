package com.sh.user.view;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.sh.product.model.entity.Cart;
import com.sh.product.view.CartMenu;
import com.sh.user.controller.UserController;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;
/**
 * - 마이페이지 화면
 *
 */
public class MyPageMenu {
	private Scanner sc = new Scanner(System.in);
	private UserController userController = new UserController();
	private UserMenu userMenu = new UserMenu();
	private CartMenu cartMenu = new CartMenu();
	
	private String myPageMenu = """
							======== 🙂 마이페이지 🙂 ========
							1. 회원정보 조회/수정하기
							2. 장바구니 확인하기
							3. 구매내역 확인하기
							4. 회원탈퇴하기
							0. 이전 메뉴로 돌아가기
							===============================
							[선택] :  """;
	
	/**
	 * switch문으로 사용자의 선택번호에 따라 메소드 실행
	 */
	public void displayMyPageMenu(String id) {
		while(true) {
			// 회원이 존재하는지, 로그인이 되어있는지 확인
			if(userController.findById(id) == null) {
				System.out.println();
				System.out.println("+—————————————————————+");
				System.out.println("|🔊 로그아웃되었습니다.   |\n|🏠 홈화면으로 돌아갑니다. |");
				System.out.println("+—————————————————————+");
				userMenu.loginMenu();
			}
			
			System.out.println();
			System.out.print(myPageMenu);
			String choice = sc.next();
			
			switch (choice) {
			case "1": displayUserInfoMenu(id) ;break;
			case "2": displayUserCart(id); break;
			case "3": displayUserPurchaseList(id);break;
			case "4": displayUserDeleteMenu(id); break;
			case "0": return;

			default: System.out.println("🚨 잘못 입력하셨습니다."); break;
			}
		}
	}

	/**
	 * 장바구니에 담긴 제품을 for문으로 반복해서 출력
	 */
	private void displayUserCart(String id) {
		List<Cart> carts = userController.findCartById(id);
		
		if(carts == null || carts.isEmpty()) {
			System.out.println("🔊 장바구니에 상품이 없습니다.");
			return;
		}
		
		System.out.println();
		System.out.println("======== 🛒 장바구니 🛒 ========");
		for(int i = carts.size()-1; i >= 0; i--) {
			System.out.println("--------------------");
			System.out.println("| 장바구니 번호 : " + (i + 1) + "   |");
			System.out.println("--------------------");
			System.out.printf("[%s] %s \n(수량 : %s | 총 가격 : %s원)\n", 
								carts.get(i).getBrandName(),
								carts.get(i).getProductName(),
								carts.get(i).getCount(),
								carts.get(i).getPrice() * carts.get(i).getCount());
		}
		System.out.println("===============================");
		System.out.print("✅ 제품 구매 여부를 입력하세요(Y/N) : ");
		// 구매여부가 N일 때, 조기리턴
		if("N".equals(sc.next().toUpperCase()))
			return;
		
		cartMenu.purchaseInCart(carts, id);
	}

	/**
	 * 구매목록을 출력
	 */
	public void displayUserPurchaseList(String id) {
		List<Purchase> purchases = userController.findPurchaseById(id);
		
		if(purchases == null || purchases.isEmpty()) {
			System.out.println("🔊 구매내역이 없습니다.");
			return;
		}
		
		System.out.println("======== 🛍️ 구매내역 🛍️ ========");
		for(Purchase purchase : purchases) {
			System.out.println("-------------------------------");
			System.out.printf("구매번호 : %s\n", purchase.getNo());
			System.out.printf("제품명 : [%s] %s (수량:%s)\n", purchase.getBrandName(), purchase.getProductName(), purchase.getCount());
			System.out.printf("구매금액 : %s원\n", purchase.getPayPrice());
			System.out.printf("적립액 : %.2f원\n", purchase.getPayPrice() * 0.05);
			System.out.printf("구매일시 : %s\n", purchase.getPurchasedAt());
			System.out.println("-------------------------------");
		}
		System.out.println("===============================");
	}

	/**
	 * 회원탈퇴 여부 확인 -> 비밀번호 확인 -> 로그아웃 처리
	 */
	private void displayUserDeleteMenu(String id) {
		String userDelete = """
							====== 🔐🔐 회원 탈퇴 🔐🔐 ======
							✅ 정말 탈퇴하시겠습니까?(Y/N) : """;
		String yn = null;
		String password = null;
		int result = 0;
		
		System.out.println();
		System.out.print(userDelete);
		yn = sc.next().toUpperCase();

		switch (yn) {
		case "Y":
			while(true) {
				System.out.print("✅ 비밀번호 확인 : ");
				password = sc.next();
				if (userController.findByUser(id, password) == null) {
					System.out.println("🔊 비밀번호가 일치하지 않습니다. 😭");
				}
				else
					break;
			}; 
			result = userController.deleteById(id);
			System.out.println();
			displayResult("[ 회원 탈퇴 ]", result);
				return;
		case "N":
			System.out.println("🔊 탈퇴를 취소하셨습니다.");
			return;	
		default:
			System.out.println("🚨 잘못 입력하셨습니다.");
			break;
		}
	}

	/**
	 * 회원정보 출력
	 */
	private void displayUserInfo(User user) {
		String userInfo = """
				======== ⚙️ 회원 정보 ⚙️ ========
				""";
		// select * from tb_user where id = ?
		System.out.println();
		System.out.print(userInfo);
		System.out.printf("아이디 : %s\n"
						+ "이름 : %s\n"
						+ "생일 : %s\n"
						+ "피부타입 : %s\n"
						+ "가용적립액 : %.1f원\n",
						user.getId(),
						user.getName(),
						user.getBirthday(),
						user.getSkinType(),
						user.getMileage());
		System.out.println("===============================");
	}
	
	// 회원정보 출력 후 생일 및 피부타입 변경
	private void displayUserInfoMenu(String id) {
		User user = userController.findById(id);
		displayUserInfo(user);
		
		String userInfoUpdate = """
								~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
								1. 생일 변경하기
								2. 피부타입 변경하기
								3. 비밀번호 변경하기
								0. 이전 메뉴로 돌아가기
								~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
								[선택] : """;
		
		while(true) {
			System.out.print(userInfoUpdate);
			String title = null;
			String choice = sc.next();
			int result = 0;
			Object newValue = null;
			
			// update user set # = ? where id = ?
			// update user set # = ? where id = ?
			switch (choice) {
			case "1":
				choice = "birthday";
				title = "생일 변경";
				System.out.print("✅ 변경할 생일 (19990909) : ");
				LocalDate temp = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("yyyyMMdd"));
				newValue = Date.valueOf(temp); // java.time.LocalDate -> java.sql.Date
				break;
			case "2":
				choice = "skin_type";
				title = "피부타입 변경";
				System.out.print("✅ 변경할 피부타입(1.건성, 2.지성, 3.민감성) : ");

				switch (sc.next()) {
				case "1":
					newValue = "건성";
					break;
				case "2":
					newValue = "지성";
					break;
				case "3":
					newValue = "민감성";
					break;
				default:
					System.out.println("🚨 잘못 입력하셨습니다.");
					break;
				}
				break;
			case "3":
				choice = "password";
				title = "비밀번호 변경";
				newValue = "";
				
				while(true) {
					System.out.print("✅ 기존 비밀번호 확인 : ");
					String password = sc.next();
					User tmpUser = userController.findByUser(id, password);
					
					if (tmpUser == null) {
						System.out.println("🚨 비밀번호가 일치하지 않습니다. 😭");
					}
					else {
						while(true) {
						newValue = userMenu.passwordValid("✅ 새로운 비밀번호 : ");
						if(tmpUser.getPassword().equals(newValue))
							System.out.println("🚨동일한 비밀번호로는 변경할 수 없습니다.");
						else
							break;
						}
					}
					userMenu.passwordVerify((String) newValue);
					break;
				}
				
				break; 
			case "0":
				return; // 마이페이지로 돌아가기
			default:
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}

			result = userController.updateUserInfo(user, choice, newValue); 
			displayResult(title, result);
		}
	}

	// DML result 출력문
	private void displayResult(String menu, int result) {
		if(result > 0) {
			System.out.println(menu + " 성공 🆗🆗");
		}
		else {
			System.out.println(menu + " 실패 🆖🆖");
		}
	}
}

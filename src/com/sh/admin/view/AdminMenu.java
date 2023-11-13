package com.sh.admin.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sh.admin.controller.AdminController;
import com.sh.admin.model.entity.UserDel;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;

public class AdminMenu {
	private AdminController adminController = new AdminController();
	private Scanner sc = new Scanner(System.in);
	
	private String adminMenu = """
			========== 관리자 메뉴 ==========
			1. 회원 목록
			2. 탈퇴 회원 목록
			3. 전체 매출 보기
			4. 판매 제품 추가
			5. 판매 제품 수정
			6. 판매 제품 삭제
			0. 로그아웃
			==============================
			[선택] : """;
	
	public void adminMenu() {
		while(true) {
			System.out.println();
			System.out.print(adminMenu);
			String choice = sc.next();
			
			List<User> users = null;
			List<UserDel> usersDel = null;
			List<Purchase> purchases = null;
			Product product = null;
			int result = 0;
			List<String> ingredients = null;
			
			switch (choice) {
			case "1":
				users = adminController.findUserAll();
				displayUsers(users);
				break;
			case "2":
				usersDel = adminController.findUserDel();
				displayUsersDel(usersDel);
				break;
			case "3":
				purchases = adminController.findPurchaseListAll();
				displayPurchases(purchases);
				break;
			case "4":
				product = inputProduct();
				result = adminController.addProduct(product);
				displayResult("[ 제품 정보 입력 ]", result);
				inputIngredient(product.getProductCode());
				break;
			case "5":
				product = searchProduct();
				displayProduct(product);
				productInfoUpdate(product);
				break;
			case "6":
				product = searchProduct();
				displayProduct(product);
				if (answer() == 'Y') {
					result = adminController.deleteProduct(product);
					displayResult("[ 제품 삭제 ]", result);
				}
				break;
			case "0": return;
			default:
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}

	private void displayUsers(List<User> users) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.printf("%-12s %s \t%-5s %-6s %10s %5s %7s \n", 
				"회원 아이디", "회원명", "비밀번호", "생년월일", "피부타입", "생성일자", "적립금");
		System.out.println("--------------------------------------------------------------------------------------");
		if(users == null || users.isEmpty()) {
			System.out.println("\t\t 조회된 결과가 없습니다.");
		} else {
			for(User user : users) {
				System.out.printf("%-12s %s \t%-5s %-6s %10s %5s %7s \n", 
						user.getId(), 
						user.getName(), 
						user.getPassword(), 
						user.getBirthday(), 
						user.getSkinType(),
						user.getCreatedAt(),
						user.getMileage());
			}
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
	
	private void displayUsersDel(List<UserDel> usersDel) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.printf("%s\t%s\t%s\t%s\t\t      %s\t\n", 
				"No", "회원 아이디", "회원명", "생성일", "탈퇴일");
		System.out.println("--------------------------------------------------------------------------------------");
		if(usersDel == null || usersDel.isEmpty()) {
			System.out.println("\t\t 조회된 결과가 없습니다.");
		} else {
			for(UserDel userDel : usersDel) {
				System.out.printf("%s\t%s\t\t%s\t%s\t%s\t\n", 
						userDel.getNo(), 
						userDel.getUserId(), 
						userDel.getUserName(), 
						userDel.getCreatedAt(), 
						userDel.getDelAt());
			}
		}
		System.out.println("-------------------------------------------------------------------------------------------");
	}
	
	private void displayPurchases(List<Purchase> purchases) {
		System.out.println("-------------------------------------------------------------------------------------------");
		System.out.printf("%s\t\t%s\t%s\t\t%s\t%s\t\t\t%s\t%s\n", 
				"No", "아이디", "제품명", "판매수량", "판매일자", "매출액", "적립금사용액");
		System.out.println("-------------------------------------------------------------------------------------------");
		if(purchases == null || purchases.isEmpty()) {
			System.out.println("\t\t 조회된 결과가 없습니다.");
		} else {
			for(Purchase purchase : purchases) {
				System.out.printf("%s\t%s\t%s\t%s\t%s\t%.0f원\t%.2f원\n",
						purchase.getNo(), 
						purchase.getId(), 
						purchase.getProductName(),
						purchase.getCount(), 
						purchase.getPurchasedAt(),
						purchase.getPayPrice(),
						purchase.getMileage());
			}
		}
		System.out.println("-------------------------------------------------------------------------------------------");
	}
	
	private Product inputProduct() {
        Product product = new Product();
        System.out.println("✅ 제품정보를 입력하세요.");
        System.out.print("✅ 제품명 : ");
        sc.nextLine();
        product.setProductName(sc.nextLine());
        for (;;) {
            System.out.print("✅ 제품코드 : ");
            String code = sc.next();
            if (code.equals(adminController.findByProductCode(code).getProductCode())) {
                System.out.println("🚨 이미 등록된 제품입니다");
            } else {
                System.out.println("🔊 새로운 제품입니다.");
                product.setProductCode(code);
                break;
            }
        }
        System.out.print("✅ 카테고리 : ");
        product.setCategoryCode(sc.next());
        System.out.print("✅ 브랜드 : ");
        product.setBrandName(sc.next());
        System.out.print("✅ 가격 : ");
        product.setPrice(sc.nextInt());
        System.out.print("✅ 수량 : ");
        product.setStock(sc.nextInt());
        return product;
    }
	
	private void inputIngredient(String productCode) {
		List<String> ingredients = new ArrayList<>();
		int result = 0;
		for (;;) {
			System.out.print("✅ 제품의 성분을 입력하세요 (stop 입력시 마무리) : ");
			String ingredient = sc.next();
			String test = adminController.findByIngredient(ingredient);
			if ("stop".equals(ingredient)) {
				break;
			} else if (!ingredient.equals(test)) {
				System.out.println("🚨 등록되지 않은 성분을 입력했습니다.");
				continue;
			}
			ingredients.add(ingredient);
		}
		for (String ingredient : ingredients) {
			List<String> ingredientsCode = new ArrayList<String>();
			ingredientsCode = adminController.changeToIngredientCode(ingredient);
			for(String ingredientCode : ingredientsCode) {
				result = adminController.addIngredient(productCode, ingredientCode);			
			}
		}
		displayResult("[ 제품 성분 입력 ]", result);
	}
	
	private Product searchProduct() {
		Product product = null;
		String productCode= null;
		
		System.out.print("✅ 제품 코드를 입력해주세요 : ");
		sc.nextLine();
		productCode = sc.nextLine();
		product = adminController.findByProductCode(productCode);
		return product;
	}
	
	private void displayProduct(Product product) {
		if(product == null) {
            System.out.println("🔊 조회된 회원이 없습니다.");
        }
        else {
            System.out.println("--------------------------------------");
            System.out.printf("ID    : %s\n", product.getProductName());
            System.out.printf("Name     : %s\n", product.getProductCode());
            System.out.printf("Code     : %s\n", product.getCategoryCode());
            System.out.printf("Brand	 : %s\n", product.getBrandName());
            System.out.printf("Price     : %s\n", product.getPrice());
            System.out.printf("Stock     : %s\n", product.getStock());
            System.out.println("--------------------------------------");
        }
	}
	
	private void productInfoUpdate(Product product) {
		String productInfoUpdate = """
			====== ✍️ 제품 수정 사항 ✍️ ======
			1. 제품명
			2. 제품 코드
			3. 카테고리 코드
			4. 브랜드명
			5. 가격
			6. 수량
			0. 취소
			===============================
			[선택] : """;
		
		while (true) {
			String title = null;
			int result = 0;
			Object newValue = null;
			System.out.print(productInfoUpdate);
			String choice = sc.next();
			
			switch (choice) {
			case "1":
				choice = "product_name";
				title = "제품명 변경";
				System.out.print("✅ 변경할 제품명 : ");
				sc.nextLine();
				newValue = sc.nextLine();
				break;
			case "2":
				choice = "product_code";
				title = "제품코드 변경";
				System.out.print("✅ 변경할 제품코드 : ");
				newValue = sc.next();
				break;
			case "3":
				choice = "category_code";
				title = "카테고리코드 변경";
				System.out.print("✅ 변경할 카테고리코드 : ");
				newValue = sc.next();
				break;
			case "4":
				choice = "brand_name";
				title = "브랜드명 변경";
				System.out.print("✅ 변경할 브랜드명 : ");
				newValue = sc.next();
				break;
			case "5":
				choice = "price";
				title = "가격 변경";
				System.out.print("✅ 변경할 가격 : ");
				newValue = sc.nextInt();
				break;
			case "6":
				choice = "stock";
				title = "수량 변경";
				System.out.print("✅ 변경할 수량 : ");
				newValue = sc.nextInt();
				break;
			case "0": return;
			default: 
				System.out.println("🚨 잘못 입력하셨습니다."); 
				break;
			}
			result = adminController.productInfoUpdate(product, choice, newValue);
			displayResult("[ " + title + " ]", result);
		}
	}
	
	private char answer() {
		System.out.print("✅ 정말 삭제하시겠습니까(Y/N)? ");
		char ans = sc.next().toUpperCase().charAt(0);
		if (ans == 'N')
			System.out.println("🔊 삭제가 취소되었습니다.");
		return ans;
	}
	
	private void displayResult(String s, int result) {
		if(result > 0) {
			System.out.println(s + " 성공 🆗🆗");
		}
		else {
			System.out.println(s + " 실패 🆖🆖");
		}
	}
}

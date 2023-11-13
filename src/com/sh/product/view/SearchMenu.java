package com.sh.product.view;

import java.util.List;
import java.util.Scanner;

import com.sh.product.controller.ProductController;
import com.sh.product.model.entity.Product;

public class SearchMenu {
	private ProductController productController = new ProductController();
	private Scanner sc = new Scanner(System.in);
	private CartMenu cartMenu = new CartMenu();
	
	public void searchMenu(String id) {
		String menu = """
                ======== 🔍 검색 메뉴 🔍 ========
                1. 제품명으로 검색
                2. 성분명으로 검색
                3. 브랜드명으로 검색
                4. 가격대 지정 검색
                0. 메인화면으로 이동
                ===============================   
                선택 : """;
		
		while (true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			
			switch (choice) {
			case "1": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByName(inputname())), id);
				break;
			case "2": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByIngredient(inputIngredient())), id);
				break;
			case "3": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByBrand(inputBrand())), id);
				break;
			case "4": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByPrice(inputPriceMin(), inputPriceMax())), id);
				break;
			case "0": 
				return;
			default: 
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}

	private String inputname() {
		System.out.print("✅ 제품명 입력 : ");
		sc.nextLine();
		return sc.nextLine();
	}
		
	private String inputIngredient() {
		System.out.print("✅ 성분명 입력 : ");
		sc.nextLine();
		return sc.nextLine();
	}
	
	private String inputBrand() {
		System.out.print("✅ 브랜드명 입력 : ");
		sc.nextLine();
		return sc.nextLine();
	}

	private int inputPriceMin() {
		System.out.print("✅ 최소값 입력 : ");
		return sc.nextInt();
	}
	private int inputPriceMax() {
		System.out.print("✅ 최대값 입력 : ");
		return sc.nextInt();
	}
	
	private List<Product> displayProducts(List<Product> products) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.printf("%-5s%s %-20s\t%10s\t%-15s\t%-10s\t\n", 
				"번호","[브랜드명]", "제품명", "제품 코드", "카테고리",  "가격");
		System.out.println("--------------------------------------------------------------------------------------");
		if(products == null || products.isEmpty()) {
			System.out.println("\t\t 조회된 결과가 없습니다.");
		} else {
			for(int i = 0; i < products.size() ; i++) {
				System.out.printf("%-5s[%-5s] %-20s\t%10s\t%-10s\t%-10s\t\n", 
						i + 1, 
						products.get(i).getBrandName(), 
						products.get(i).getProductName(), 
						products.get(i).getProductCode(), 
						products.get(i).getCategoryCode(), 
						products.get(i).getPrice());
			}
		}
		System.out.println("--------------------------------------------------------------------------------------");
		
		return products;
	}
}

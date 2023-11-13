package com.sh.product.view;

import java.util.List;
import java.util.Scanner;

import com.sh.product.controller.ProductController;
import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;

public class ProductListMenu {
	private Scanner sc = new Scanner(System.in);
	private ProductController productController = new ProductController();
	private SearchMenu searchMenu = new SearchMenu();
	private CartMenu cartMenu = new CartMenu();
	
	public void productListMenu(String id) {
		String menu = """
					====== 📄 제품 목록보기 📄 ======
					1. 전체 제품 보기
					2. 카테고리별 제품보기
					3. 피부타입별 제품보기
					4. 검색으로 이동
					0. 메인화면으로 이동
					==============================
					[선택] : """;
		
		while(true) {
			System.out.println();
			System.out.print(menu);
			String choice = sc.next();
			
			switch (choice) {
			case "1":
				cartMenu.addCartOrPurchase(displayProducts(productController.findProductAll()), id);
				break;
			case "2": 
				displayCategory(id);
				break;
			case "3": 
				displaySkinType(id);
				break;
			case "4": 
				searchMenu.searchMenu(id);
				break;
			case "0": 
				return;
			default: 
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}

	public void displayCategory(String id) {
		String categoryMenu = """
				===== 📄 카테고리별 제품보기 📄 =====
				1. 스킨
				2. 로션
				3. 클렌징
				4. 바디워시
				5. 샴푸
				0. 메인화면으로 이동
				=================================
				선택 :  """;
		
		while(true) {
			System.out.println();
			System.out.print(categoryMenu);
			String choice = sc.next();
			
			switch (choice) {
			case "1": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByCategoryCode("C01")), id);
				break;
			case "2": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByCategoryCode("C02")), id);
				break;
			case "3": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByCategoryCode("C03")), id);
				break;
			case "4": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByCategoryCode("C04")), id);
				break;
			case "5": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findByCategoryCode("C05")), id);
				break;
			case "0": return;
			default: 
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public void displaySkinType(String id) {
		String categoryMenu = """
				===== 👳‍♀️ 피부타입별 제품보기 ‍👳‍♀️ =====
				1. 지성 추천 타입
				2. 건성 추천 타입
				3. 민감성 추천 타입
				4. 지성 비추천 타입
				5. 건성 비추천 타입
				6. 민감성 비추천 타입
				0. 메인화면으로 이동
				================================
				선택 :  """;
		
		while(true) {
			System.out.println();
			System.out.print(categoryMenu);
			String choice = sc.next();
			
			switch (choice) {
			case "1": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("oily_good")), id);
				break;
			case "2": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("dry_good")), id);
				break;
			case "3": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("sensitive_good")), id);
				break;
			case "4": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("oily_bad")), id);
				break;
			case "5": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("dry_bad")), id);
				break;
			case "6": 
				cartMenu.addCartOrPurchase(displayProducts(productController.findBySkinType("sensitive_bad")), id);
				break;
			case "0": return;
			default: 
				System.out.println("🚨 잘못 입력하셨습니다.");
				break;
			}
		}
	}
	
	public List<Product> displayProducts(List<Product> products) {
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

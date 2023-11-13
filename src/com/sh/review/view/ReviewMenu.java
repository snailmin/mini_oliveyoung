package com.sh.review.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sh.review.controller.ReviewController;
import com.sh.review.model.entity.Review;
import com.sh.review.model.vo.ProductReview;
import com.sh.review.model.vo.ProductReview;
import com.sh.user.controller.UserController;
import com.sh.user.model.entity.Purchase;
/**
 * - 리뷰 화면
 *
 */
public class ReviewMenu {
	private Scanner sc = new Scanner(System.in);
	private UserController userController = new UserController();
	private ReviewController reviewController = new ReviewController();

	private String reviewMenu = """
			======== 🏅 리뷰게시판 🏅 ========
			1. 전체 리뷰보기
			2. 내가 쓴 리뷰보기
			3. 리뷰 작성하기
			4. 리뷰 검색하기
			0. 메인화면으로 이동
			===============================
			[선택] :  """;

	/**
	 * switch문으로 선택된 번호로 이동
	 */
	public void displayReviewMenu(String id) {
		String choice = null;

		while (true) {
			System.out.println();
			System.out.print(reviewMenu);
			choice = sc.next();
			switch (choice) {
			case "1":
				displayProductReviews(reviewController.findAllProductReviews(), id);
				break;
			case "2":
				displayProductReviews(reviewController.findUserReviews(id), id);
				break;
			case "3":
				displayReviewInsertMenu(id);
				break;
			case "4":
				displayReviewSearchMenu(id);
				break;
			case "0":
				return;

			default:
				System.out.println("🔊 잘못 입력하셨습니다.");
				break;
			}
		}
	}
	
	// case 4 검색메뉴 출력하기
	private void displayReviewSearchMenu(String id) {
		String searchMenu = """
							~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
							1. 제품명으로 검색하기
							2. 리뷰 키워드로 검색하기
							0. 이전 메뉴로 돌아가기
							~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
							(선택) : """;
		
		while(true) {
		System.out.print(searchMenu);
		String choice = sc.next();
		
		List<ProductReview> reviews = new ArrayList<>();
		String keyword = null;
		
			switch (choice) {
			case "1": 
				System.out.print("✅ 제품명 입력 : "); 
				sc.nextLine(); // 개행제거용
				keyword = sc.nextLine(); 
				reviews = reviewController.reviewSearch("product_name", keyword);
				break;
			case "2": 
				System.out.print("✅ 리뷰 키워드 입력 : "); 
				sc.nextLine(); // 개행제거용
				keyword = sc.nextLine(); 
				reviews = reviewController.reviewSearch("title", keyword);
				break;
			case "0" : return;
			default: System.out.println("🚨 잘못 입력하셨습니다."); break;
			}
			displayProductReviews(reviews, id);
		}
	}
	
	// case 3 작성메뉴 출력하기
	private void displayReviewInsertMenu(String id) {
		int result = 0;
		
		// purchase_list에 존재하는 상품만 리뷰 쓸 수 있도록 하자
		// 구매리스트 보여주고 리뷰 작성할 번호를 누르게 하자
		List<Purchase> purchases = userController.findPurchaseById(id);

		// 작성할 수 있는 리뷰의 수를 카운트
		int reviewCount = 0;
		if(purchases == null || purchases.isEmpty()) {
			System.out.println("🔊 구매내역이 없습니다.");
			return;
		}
		for(int i = 0; i < purchases.size(); i++) {
			if(reviewController.findReviewByPurchaseNo(purchases.get(i).getNo()) != null)
				continue;
			
			System.out.println("----------------------------");
			System.out.println("[ 구매내역 ] : " + (i + 1));
			System.out.println("----------------------------");
			System.out.printf("구매번호 : %s\n", purchases.get(i).getNo());
			System.out.printf("제품명 : [%s] %s (수량 : %s)\n", purchases.get(i).getBrandName(),purchases.get(i).getProductName(), purchases.get(i).getCount());
			System.out.println("----------------------------");
			reviewCount += 1;
		}
		
		if(reviewCount == 0) {
			System.out.println("🔊 모든 구매내역에 리뷰가 작성되었습니다.");
			return;
		}
		
		// 작성된 리뷰의 개수를 카운트
		int reviewWriteCount = 0;
		while(true) {
			
			System.out.print("✅ 리뷰 작성할 구매내역 번호 : ");
			int choice = sc.nextInt();
		
			// insert문 작성
			// 입력받아서 작성할 내용을 찾자
			String reviewTitle = """
									~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
											리뷰 작성하기✒✒
									~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
									제목 : """;
			System.out.print(reviewTitle);
			sc.nextLine(); // 개행제거
			String title = sc.nextLine();
			System.out.print("✅ 별점(1 ~ 5) : ");
			int score = sc.nextInt();
			System.out.print("✅ 내용 : ");
			sc.nextLine(); // 개행제거
			String contents = sc.nextLine();
		
			Review review = new Review();
			review.setId(id);
			review.setProductCode(purchases.get(choice - 1).getProductCode());
			review.setScore(score);
			review.setTitle(title);
			review.setContents(contents);
			review.setPurchaseNo(purchases.get(choice - 1).getNo());
			
			result = reviewController.reviewInsert(review);
			displayResult("[ 리뷰 작성 ] ", result);
			
			reviewWriteCount += 1;
		
			System.out.println();
			
			// 작성할 수 있는 리뷰의 수와 작성된 리뷰의 수가 같을 때는 모든 리뷰가 작성된 것으로 확인하여 리턴 처리
			if(reviewCount == reviewWriteCount) {
				System.out.println("🔊 모든 구매내역에 리뷰가 작성되었습니다.");
				return;
			}
			
			// 같지 않을 때는 더 작성할 수 있는 리뷰가 있으므로 추가 작성 여부를 확인
			System.out.print("🔊 추가로 작성하시겠습니까? (Y/N) : ");
			if("N".equals(sc.next().toUpperCase()))
				break;
		}
	}
	
	/**
	 * 리뷰 반복 출력
	 */
	private void displayProductReviews(List<ProductReview> productReviews, String id) {
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------");
		System.out.println("번호\t제품명\t\t\t게시글 제목\t\t별점\t작성자\t작성일");

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------");
		if (productReviews.isEmpty() || productReviews == null) {
			System.out.println("🔊 작성된 리뷰가 없습니다.");
			return;
		}

		for (int i = 0; i < productReviews.size(); i++) {
			int index = productReviews.size() - i;
			System.out.printf("%-5s%-20s%-25s%-5s%-10s%-10s\n", index,
					productReviews.get(i).getProduct().getProductName(), productReviews.get(i).getReview().getTitle(),
					productReviews.get(i).getReview().getScore(), productReviews.get(i).getReview().getId(),
					productReviews.get(i).getReview().getCreatedAt());
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------");

		// 리뷰 자세히 보기
		reviewChoice: while (true) {
			System.out.print("✅ 게시글 선택 : ");
			int choice = sc.nextInt();

			if (choice < 1) {
				System.out.println("🚨 게시글 번호를 잘못 입력하셨습니다.");
			} else {
				int index = (productReviews.size() - choice);

				int reviewNo = productReviews.get(index).getReview().getNo(); // review pk로 조회하기

				ProductReview reviewDetail = reviewController.findReviewByReviewNo(reviewNo);

				System.out.println("----------------------------------------");
				System.out.println("| 제목 : " + reviewDetail.getReview().getTitle());
				System.out.println("----------------------------------------");
				System.out.print("| 아이디 : " + reviewDetail.getReview().getId());
				System.out.println("  제품명 : " + reviewDetail.getProduct().getProductName());
				System.out.println("----------------------------------------");
				System.out.println("| 내용 : " + reviewDetail.getReview().getContents());
				System.out.println("|");
				System.out.println("|");
				System.out.println("----------------------------------------");
				
				if(reviewDetail.getReview().getId() == null) {
					System.out.println("🔊 탈퇴한 사용자의 리뷰입니다.");
					return;
				}
				
				if(reviewDetail.getReview().getId().equals(id)) {
					System.out.print("✒️ 리뷰를 수정하시겠습니까? (Y/N) :");
					String yn = sc.next().toUpperCase();
					if ("N".equals(yn))
						break;
					displayUpdateReview(reviewDetail.getReview().getNo());
				}
				else {
					if (productReviews.size() > 1) {
						System.out.print("🔍 추가 조회하시겠습니까? (Y/N) : ");
						String yn = sc.next().toUpperCase();
						if ("N".equals(yn))
							break;
						displayProductReviews(productReviews, id);
					}
				}
				break reviewChoice;
			}
		}
	}
	
	private void displayUpdateReview(int reviewNo) {
		sc.nextLine();
		System.out.println("-----------------------------");
		System.out.println("✅ 변경할 내용을 입력하세요 ");
		System.out.println("-----------------------------");
		String newContent = sc.nextLine();
		int result = reviewController.updateMyReview(reviewNo, newContent);
		displayResult("[ 리뷰 내용변경 ]", result);
		
	}
	
	// DML 성공여부
	private void displayResult(String menu, int result) {
		if(result > 0) {
			System.out.println(menu + " 성공 🆗🆗");
		}
		else {
			System.out.println(menu + " 실패 🆖🆖");
		}
	}
}

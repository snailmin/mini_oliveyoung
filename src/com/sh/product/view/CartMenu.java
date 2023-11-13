package com.sh.product.view;

import java.util.List;
import java.util.Scanner;
import com.sh.product.controller.ProductController;
import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.user.controller.UserController;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;
import com.sh.user.view.MyPageMenu;

/**
 * <pre>
 * - 제품목록에서 제품 선택 할 때, 사용자가 할 수 있는 2가지 선택지를 고려했습니다.
 * 	 장바구니 담기와 바로 구매하기이며 각 항목에서는 다음 사항을 고려합니다. 
 * - 장바구니 담기 
 * 		1. 이미 장바구니에 같은 상품이 있을 때 update
 * 		2. 장바구니에 같은 상품이 없을 때 insert
 * - 제품 구매 
 * 		1. 제품 재고가 0 보다 큰지 확인 select
 * 			0이하일 경우 조기리턴
 * 		2. 해당 회원이 사용할 수 있는 적립금이 있는지 확인 select
 * 			적립금이 0이상일 때만 적립금 사용여부를 확인
 * - 구매 완료 
 * 		1. 적립금 사용액을 제외한 값에 대해 5%을 적립 update 
 * 		2. 구매된 수량만큼 제품 재고를 차감 insert에 대한 update trigger 실행
 */

public class CartMenu {
	private ProductController productController = new ProductController();
	private UserController userController = new UserController();
	private Scanner sc = new Scanner(System.in);

	/**
	 * 제품목록에서 제품 선택 후, 1. 장바구니 담기 2. 바로 구매하기를 할 수 있습니다.
	 */
	public void addCartOrPurchase(List<Product> products, String id) {
		int result = 0;

		// 제품 구매여 부가 Y일 때는 조기리턴
		System.out.print("✅ 제품 구매 여부를 입력하세요(Y/N) : ");
		if ("N".equals(sc.next().toUpperCase()))
			return;

		// 제품 구매 여부가 N이 아닐때만 반복적으로 구매 가능
		while (true) {
			System.out.println();
			System.out.print("[구매할 제품번호] : ");
			int choice = sc.nextInt();
			System.out.print("[제품수량] : ");
			int count = sc.nextInt();

			int index = choice - 1;

			// 만약 선택한 제품의 재고가 0이하라면 재고가 없다는 안내 메시지 출력 후, 조기리턴
			if (products.get(index).getStock() <= 0) {
				System.out.println("🔊 재고가 없습니다.😥😥");
				return;
			}

			// 선택한 제품의 재고가 있다면 장바구니 담기 또는 바로 구매하기
			System.out.print("\n(1)장바구니 담기\t(2)바로 구매하기\n[선택] : ");
			String purchaseOrCart = sc.next();

			// 1. 장바구니 담기를 선택한 경우, 해당 회원 장바구니에 그 제품이 있는지 확인
			if ("1".equals(purchaseOrCart)) {
				String productCode = products.get(index).getProductCode();
				Cart cart = productController.findByProductCode(productCode, id);

				// 제품이 없는 경우 장바구니(Cart) 테이블에 추가(insert)
				if (cart == null) {
					Cart insertCart = new Cart();
					insertCart.setId(id);
					insertCart.setProductCode(products.get(index).getProductCode());
					insertCart.setCount(count);

					result = productController.insertCart(insertCart);

					// 제품이 있는 경우 새로 선택한 수량만큼을 증가(update)
				} else {
					int totalCount = count + cart.getCount();
					result = productController.updateCart(id, productCode, totalCount);

				}
				displayResult("[장바구니 담기] ", result);

				// 2. 구매를 하는 경우
			} else if ("2".equals(purchaseOrCart)) {
				double totalPayPrice = 0;

				// 마일리지 사용여부를 확인
				double usingMileage = checkUsingMileage(id);
				double payPrice = (products.get(index).getPrice() * count);
				// 마일리지를 사용하는 경우, checkUsingMeileage는 사용할 마일리지 값을 반환
				totalPayPrice = payPrice - usingMileage;

				System.out.println();
				System.out.printf("🔊 총 결제금액은 %s원입니다.\n", totalPayPrice);

				Purchase purchase = new Purchase();
				purchase.setId(id);
				purchase.setProductCode(products.get(index).getProductCode());
				purchase.setCount(count);
				purchase.setPayPrice(payPrice - usingMileage);
				purchase.setMileage(usingMileage);

				// insertPurchase 메소드 실행(trigger 실행조건), 구매된 수량만큼 product 테이블에서 stock를 감소(trigger
				// 실행내용)
				// 장바구니 담기의 경우는 stock을 감소시키지 않음
				result = productController.insertPurchase(purchase);
				displayResult("[바로 구매하기] ", result);

				// 구매이후, 해당 구매건 최종결제 금액의 5% 적립 메소드 실행
				displaySaveMileageResult(totalPayPrice, id);
			} else {
				System.out.print("🚨 잘못 입력하셨습니다.");
			}

			System.out.println();
			System.out.print("✅ 추가 구매하시겠습니까? (Y/N) :");
			String yn = sc.next().toUpperCase();
			if ("N".equals(yn))
				break;
		}
	}

	/**
	 * 장바구니에 담은 제품을 구매할 때
	 */
	public void purchaseInCart(List<Cart> carts, String id) {
		while (true) {
			System.out.println();
			System.out.print("[구매할 장바구니번호] : ");
			int choice = sc.nextInt();
			int result = 0;
			double totalPayPrice = 0;
			int index = choice - 1;

			if (carts.get(index).getStock() <= 0) {
				System.out.println(carts.get(index).getStock());
				System.out.println("🔊 재고가 없습니다.😥😥");
				new MyPageMenu().displayMyPageMenu(id);
			} else {
				double usingMileage = checkUsingMileage(id);

				double payPrice = (carts.get(index).getPrice() * carts.get(index).getCount());
				totalPayPrice = payPrice - usingMileage;

				System.out.printf("🔊 총 결제금액은 %s원입니다.\n", totalPayPrice);

				Purchase purchase = new Purchase();
				purchase.setId(id);
				purchase.setProductCode(carts.get(index).getProductCode());
				purchase.setCount(carts.get(index).getCount());
				purchase.setPayPrice(payPrice - usingMileage);
				purchase.setMileage(usingMileage);

				result = productController.insertPurchase(purchase);
				System.out.println();
				displayResult("[바로 구매하기] ", result);

				displaySaveMileageResult(totalPayPrice, id);

				// 해당 제품 장바구니에서 지우기 no으로 찾아오기
				String cartNo = carts.get(index).getNo();
				productController.deleteCart(cartNo);
			}

			System.out.println();
			System.out.print("✅ 추가 구매하시겠습니까? (Y/N) :");
			String yn = sc.next().toUpperCase();
			if ("N".equals(yn))
				break;
		}
	}

	// 구매 성공 시 적립해주기
	public void displaySaveMileageResult(double totalPayPrice, String id) {
		User user = new User();
		user = userController.findById(id);

		int result = 0;
		double saveMileage = totalPayPrice * 0.05;

		// update user mileage
		result = userController.updateUserMileage(user, saveMileage);

		if (result > 0)
			System.out.printf("🎉%.2f원이 적립되었습니다!🎉\n", saveMileage);
	}

	// 적립액 확인하기
	public double checkUsingMileage(String id) {
		User user = new User();
		user = userController.findById(id);
		double usingMileage = 0.0;

		if (user.getMileage() == 0)
			return usingMileage;

		// 적립금이 있을 때만
		System.out.print("✅ 적립금을 사용하시겠습니까?(Y/N) : ");
		if ("Y".equals(sc.next().toUpperCase())) {
			System.out.printf("🔊 사용가능한 적립액 : %.2f\n", user.getMileage());
			System.out.print("✅ 사용할 적립액을 입력하세요 : ");
			usingMileage = sc.nextDouble();
		}
		return usingMileage;
	}

	// DML result 출력문
	private void displayResult(String menu, int result) {
		if (result > 0) {
			System.out.println(menu + " 성공 🆗🆗");
		} else {
			System.out.println(menu + " 실패 🆖🆖");
		}
	}
}
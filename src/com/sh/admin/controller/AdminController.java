package com.sh.admin.controller;

import java.util.List;

import com.sh.admin.model.entity.UserDel;
import com.sh.admin.model.service.AdminService;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;

public class AdminController {
	private AdminService adminService = new AdminService();
	
	public List<User> findUserAll() {
		List<User> users = null;
		try {
			users = adminService.findUserAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return users;
	}

	public List<UserDel> findUserDel() {
		List<UserDel> usersDel = null;
		try {
			usersDel = adminService.findUserDel();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return usersDel;
	}

	public List<Purchase> findPurchaseListAll() {
		List<Purchase> purchases = null;
		try {
			purchases = adminService.findPurchaseListAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return purchases;
	}

	public Product findByProductCode(String productCode) {
		Product product = null;
		try {
			product = adminService.findByProductCode(productCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return product;
	}
	
	public int addProduct(Product product) {
		int result = 0;
		try {
			result = adminService.addProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}

	public List<String> changeToIngredientCode(String ingredient) {
		List<String> ingredientsCode = null;
		try {
			ingredientsCode = adminService.changeToIngredientCode(ingredient);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return ingredientsCode;
	}
	
	public int addIngredient(String productCode, String ingredient) {
		int result = 0;
		try {
			result = adminService.addIngredient(productCode, ingredient);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다 : " + e.getMessage());
		}
		return result;
	}

	public int productInfoUpdate(Product product, String choice, Object newValue) {
		int result = 0;
			try {
				result = adminService.productInfoUpdate(product, choice, newValue);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
			}
		return result;
	}

	public int deleteProduct(Product product) {
		int result = 0;
		try {
			result = adminService.deleteProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return result;
	}

	public String findByIngredient(String ingredient) {
		String test = null;
		try {
			test = adminService.findByIngredient(ingredient);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 아차차 : " + e.getMessage());
		}
		return test;
	}
}

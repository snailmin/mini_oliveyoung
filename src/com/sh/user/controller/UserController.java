package com.sh.user.controller;

import java.util.HashMap;
import java.util.List;

import com.sh.product.model.entity.Cart;
import com.sh.product.model.vo.SumProductInCart;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;
import com.sh.user.model.service.UserService;

public class UserController {
	private UserService userService = new UserService();

	public User findByUser(String id, String password) {
		User user = null;
		try {
			user = userService.findByUser(id, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return user;
	}

	public User findById(String id) {
		User user = null;
		try {
			user = userService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return user;
	}

	public int updateUserInfo(User user, String choice, Object updateInfo) {
		int result = 0;
		
		try {
			result = userService.updateUserInfo(user, choice, updateInfo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return result;
	}

	public int deleteById(String id) {
		int result = 0;
		
		try {
			result = userService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return result;
	}

	public List<Purchase> findPurchaseById(String id) {
		List<Purchase> purchases = null;
		
		try {
			purchases = userService.findPurchaseById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return purchases;
	}
	
	public int insertUser(User user) {
		int result = 0;
		try {
			result = userService.insertUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return result;
	}

	public List<Cart> findCartById(String id) {
		
		List<Cart> carts = null;
		
		try {
			carts = userService.findCartById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return carts;
	}
	
	public List<SumProductInCart> findSumPriceCartById(String id) {
		
		List<SumProductInCart> carts = null;
		
		try {
			carts = userService.findSumPriceCartById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return carts;
	}

	public int updateUserMileage(User user, double saveMileage) {
		int result = 0;
		
		try {
			result = userService.updateUserMileage(user, saveMileage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return result;
	}
	
}

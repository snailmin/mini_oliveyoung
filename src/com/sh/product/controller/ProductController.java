package com.sh.product.controller;

import java.util.List;

import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.product.model.service.ProductService;
import com.sh.user.model.entity.Purchase;

public class ProductController {
	private ProductService productService = new ProductService(); 
	
	public List<Product> findProductAll() {
		List<Product> products = null;
		try {
			products = productService.findProductAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}
	
	public List<Product> findByName(String name) {
		List<Product> products = null;
		try {
			products = productService.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}

	public List<Product> findBySkinType(String skinType) {
		List<Product> products = null;
		try {
			products = productService.findBySkinType(skinType);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}

	public List<Product> findByCategoryCode(String categoryCode) {
		List<Product> products = null;
		try {
			products = productService.findByCategoryCode(categoryCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}
	
	// 11/07 재준씨
	public List<Product> findByIngredient(String ingredient) {
		List<Product> products = null;
		try {
			products = productService.findByIngredient(ingredient);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}

	public List<Product> findByBrand(String brand) {
		List<Product> products = null;
		try {
			products = productService.findByBrand(brand);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}


	public List<Product> findByPrice(int min, int max) {
		List<Product> products = null;
		try {
			products = productService.findByPrice(min, max);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return products;
	}
	
	

	// 231106 민정 추가
	public int insertCart(Cart cart) {
		int result = 0;
		
		try {
			result = productService.insertCart(cart);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	}

	public int insertPurchase(Purchase purchase) {
		int result = 0;
		
		try {
			result = productService.insertPurchase(purchase);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		return result;
	}

	public Cart findByProductCode(String productCode, String id) {
		Cart cart = new Cart();
		
		try {
			cart = productService.findByProductCode(productCode, id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		
		return cart;
	}

	public int updateCart(String id, String productCode, int totalCount) {
		int result = 0;
		
		try {
			result = productService.updateCart(id, productCode, totalCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		
		return result;
	}

	public int deleteCart(String cartNo) {
		int result = 0;
		
		try {
			result = productService.deleteCart(cartNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("> 불편을 드려 죄송합니다. : " + e.getMessage());
		}
		
		return result;
	}
}

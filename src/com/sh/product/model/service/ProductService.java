package com.sh.product.model.service;

import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.product.model.dao.ProductDao;
import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;

public class ProductService {
	private ProductDao productDao = new ProductDao();
	
	public List<Product> findProductAll() {
		Connection conn = getConnection();
		List<Product> products = productDao.findProductAll(conn);
		close(conn);
		return products;
	}
	
	public List<Product> findByName(String name) {
		Connection conn = getConnection();
		List<Product> products = productDao.findByName(conn, name);
		close(conn);
		return products;
	}

	public List<Product> findBySkinType(String skinType) {
		Connection conn = getConnection();
		List<Product> products = productDao.findBySkinType(conn, skinType);
		close(conn);
		return products;
	}

	public List<Product> findByCategoryCode(String categoryCode) {
		Connection conn = getConnection();
		List<Product> products = productDao.findByCategoryCode(conn, categoryCode);
		close(conn);
		return products;
	}

	// 231107 재준씨
	public List<Product> findByIngredient(String ingredient) {
		Connection conn = getConnection();
		List<Product> products = productDao.findByIngredient(conn, ingredient);
		close(conn);
		return products;
	}

	public List<Product> findByBrand(String brand) {
		Connection conn = getConnection();
		List<Product> products = productDao.findByBrand(conn, brand);
		close(conn);
		return products;
	}


	public List<Product> findByPrice(int min, int max) {
		Connection conn = getConnection();
		List<Product> products = productDao.findByPrice(conn, min, max);
		close(conn);
		return products;
	}
	
	
	
	
	
	
	
	

	
	// 231106 민정 추가
	public int insertCart(Cart cart) {
		int result = 0;
		
		Connection conn = getConnection();
		try {
			result = productDao.insertCart(conn, cart);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
				
		return result;
	}

	public int insertPurchase(Purchase purchase) {
		int result = 0;
		
		Connection conn = getConnection();
		try {
			result = productDao.insertPurchase(conn, purchase);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
				
		return result;
	}

	public Cart findByProductCode(String productCode, String id) {
		
		Connection conn = getConnection();
		Cart cart = productDao.findByProductCode(conn, productCode, id);
		
		return cart;
	}

	public int updateCart(String id, String productCode, int totalCount) {
		int result = 0;
		
		Connection conn = getConnection();
		
		try {
			result = productDao.updateCart(conn, id, productCode, totalCount);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public int deleteCart(String cartNo) {
		int result = 0;
		
		Connection conn = getConnection();
		
		try {
			result = productDao.deleteCart(conn, cartNo);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

}

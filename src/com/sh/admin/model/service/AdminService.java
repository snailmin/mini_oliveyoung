package com.sh.admin.model.service;

import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.admin.model.dao.AdminDao;
import com.sh.admin.model.entity.UserDel;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;

public class AdminService {
	private AdminDao adminDao = new AdminDao();
	
	public List<User> findUserAll() {
		Connection conn = getConnection();
		List<User> users = adminDao.findUserAll(conn);
		close(conn);
		return users;
	}

	public List<UserDel> findUserDel() {
		Connection conn = getConnection();
		List<UserDel> usersDel = adminDao.findUserDel(conn);
		close(conn);
		return usersDel;
	}

	public List<Purchase> findPurchaseListAll() {
		Connection conn = getConnection();
		List<Purchase> purchases = adminDao.findPurchaseListAll(conn);
		close(conn);
		return purchases;
	}

	public Product findByProductCode(String productCode) {
		Connection conn = getConnection();
		Product product = adminDao.findByProductCode(conn, productCode);
		close(conn);
		return product;
	}

	public int addProduct(Product product) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = adminDao.addProduct(conn, product);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}
	
	public List<String> changeToIngredientCode(String ingredient) {
		Connection conn = getConnection();
		List<String> ingredientsCode = adminDao.changeToIngredientCode(conn, ingredient);
		close(conn);
		return ingredientsCode;
	}

	public int addIngredient(String productCode, String ingredient) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = adminDao.addIngredient(conn, productCode, ingredient);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int productInfoUpdate(Product product, String choice, Object newValue) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = adminDao.productInfoUpdate(conn, product, choice, newValue);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteProduct(Product product) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = adminDao.deleteProduct(conn, product);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public String findByIngredient(String ingredient) {
		Connection conn = getConnection();
		String test = adminDao.findByIngredient(conn, ingredient);
		close(conn);
		return test;
	}
}

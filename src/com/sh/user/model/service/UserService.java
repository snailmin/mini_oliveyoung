package com.sh.user.model.service;

import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.product.model.entity.Cart;
import com.sh.product.model.vo.SumProductInCart;
import com.sh.user.model.dao.UserDao;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;

public class UserService {
	private UserDao userDao = new UserDao();

	public User findByUser(String id, String password) {
		User user = null;
		Connection conn = getConnection();
		user = userDao.findByUser(conn, id, password);
		close(conn);
		return user;
	}

	public User findById(String id) {
		Connection conn = getConnection();
		User user = userDao.findById(conn, id);
		close(conn);
		return user;
	}

	public int updateUserInfo(User user, String choice, Object updateInfo) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserInfo(conn, user, choice, updateInfo);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public int deleteById(String id) {
		int result = 0;
		
		Connection conn = getConnection();
		try {
			result = userDao.deleteById(conn, id);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		
		return result;
	}

	public List<Purchase> findPurchaseById(String id) {
		Connection conn = getConnection();
		List<Purchase> purchases = userDao.findPurchaseById(conn, id);
		close(conn);
		return purchases;
	}

	public int insertUser(User user) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.insertUser(conn, user);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public List<Cart> findCartById(String id) {
		Connection conn = getConnection();
		List<Cart> carts = userDao.findCartById(conn, id);
		close(conn);
		return carts;
	}
	
	public List<SumProductInCart> findSumPriceCartById(String id) {
		Connection conn = getConnection();
		List<SumProductInCart> carts = userDao.findSumPriceCartById(conn, id);
		close(conn);
		return carts;
	}

	public int updateUserMileage(User user, double saveMileage) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = userDao.updateUserMileage(conn, user, saveMileage);
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

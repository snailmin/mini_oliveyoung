package com.sh.user.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.product.model.dao.ProductDao;
import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.product.model.vo.SumProductInCart;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;
import com.sh.user.model.exception.UserException;

public class UserDao {
	private Properties prop = new Properties();
	private ProductDao productDao = new ProductDao();
	
	public UserDao() {
		try {
			prop.load(new FileReader("resources/user-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User findByUser(Connection conn, String id, String password) {
		User user = null;
		String sql = prop.getProperty("findByUser");
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			try(ResultSet rset = pstmt.executeQuery()){
				if(rset.next()) {
					user = handleUserResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new UserException("로그인 오류가 발생했습니다.", e);
		}
		
		return user;
	}

	public User findById(Connection conn, String id) {
		String sql = prop.getProperty("findById");
		User user = null;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					user = handleUserResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new UserException("로그인 오류가 발생했습니다.", e);
		}
		return user;
	}

	private User handleUserResultSet(ResultSet rset) throws SQLException {
			User user = new User();
			user.setId(rset.getString("user_id"));
			user.setName(rset.getString("user_name"));
			user.setPassword(rset.getString("password"));
			user.setBirthday(rset.getDate("birthday"));
			user.setSkinType(rset.getString("skin_type"));
			user.setMileage(rset.getDouble("mileage"));
			return user;
	}

	public int updateUserInfo(Connection conn, User user, String choice, Object updateInfo) {
		String sql = prop.getProperty("updateUserInfo");
		int result = 0;
		sql = sql.replace("#", choice);
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setObject(1, updateInfo);
			pstmt.setString(2, user.getId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("회원정보 수정 오류가 발생했습니다.", e);
		}
		return result;
	}

	public int deleteById(Connection conn, String id) {
		int result = 0;
		String sql = prop.getProperty("deleteById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("회원탈퇴 오류가 발생했습니다.", e);
		}
		
		return result;
	}

	public List<Purchase> findPurchaseById(Connection conn, String id) {
		List<Purchase> purchases = new ArrayList<>();
		String sql = prop.getProperty("findPurchaseById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					purchases.add(handleUserPurchaseResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new UserException("회원 구매내역 조회 오류가 발생했습니다.", e);
		}
		return purchases;
	}
	
	private Purchase handleUserPurchaseResultSet(ResultSet rset) throws SQLException {
		Purchase purchase = new Purchase(handleProductResultSet(rset));
		purchase.setNo(rset.getString("no"));
		purchase.setId(rset.getString("user_id"));
		purchase.setProductCode(rset.getString("product_code"));
		purchase.setCount(rset.getInt("count"));
		purchase.setPurchasedAt(rset.getTimestamp("purchased_at"));
		purchase.setPayPrice(rset.getDouble("pay_price"));
		purchase.setMileage(rset.getDouble("using_mileage"));
		
		return purchase;
}

	public int insertUser(Connection conn, User user) {
		int result = 0;
		String sql = prop.getProperty("insertUser");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			pstmt.setDate(4, user.getBirthday());
			pstmt.setString(5, user.getSkinType());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new UserException("회원가입 오류!", e);
		}
		return result;
	}

	public List<Cart> findCartById(Connection conn, String id) {
		List<Cart> carts = new ArrayList<>();
		String sql = prop.getProperty("findCartById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					carts.add(handleUserCartResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new UserException("회원 장바구니 오류가 발생했습니다.", e);
		}
		return carts;
	}
	
	public List<SumProductInCart> findSumPriceCartById(Connection conn, String id) {
		List<SumProductInCart> carts = new ArrayList<>();
		String sql = prop.getProperty("findSumPriceCartById");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			try(ResultSet rset = pstmt.executeQuery()){
				while(rset.next()) {
					carts.add(handleSumCartResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new UserException("회원 장바구니 오류가 발생했습니다.", e);
		}
		return carts;
	}

	private Cart handleUserCartResultSet(ResultSet rset) throws SQLException {
		Cart cart = new Cart(handleProductResultSet(rset));
		
		cart.setNo(rset.getString("no"));
		cart.setId(rset.getString("user_id"));
		cart.setProductCode(rset.getString("product_code"));
		cart.setCount(rset.getInt("count"));
		cart.setCartedAt(rset.getTimestamp("carted_at"));
		cart.setStock(rset.getInt("stock"));
		return cart;
	}
	
	
	private Product handleProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setProductName(rset.getString("product_name"));
		product.setProductCode(rset.getString("product_code"));
		product.setCategoryCode(rset.getString("category_code"));
		product.setBrandName(rset.getString("brand_name"));
		product.setPrice(rset.getInt("price"));
		product.setStock(rset.getInt("stock"));
		return product;
	}

	private SumProductInCart handleSumCartResultSet(ResultSet rset) throws SQLException {
//		p.product_name product_name,
//	    c.user_id user_id,
//	    p.brand_name brand_name,
//	    sum(count) over(partition by user_id, c.product_code) sumCount,
//	    sum(price) over(partition by user_id, c.product_code) sumPrice
		SumProductInCart cart = new SumProductInCart();
		cart.setProductName(rset.getString("product_name"));
		cart.setCart(null);
//		cart.getCart().setId(rset.getString("user_id"));
		cart.setBrandName(rset.getString("brand_name"));
		cart.setSumCount(rset.getInt("sumCount"));
		cart.setSumPrice(rset.getInt("sumPrice"));
		return cart;
	}

	public int updateUserMileage(Connection conn, User user, double saveMileage) {
		String sql = prop.getProperty("updateUserMileage");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setObject(1, saveMileage);
			pstmt.setString(2, user.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new UserException("적립금 업데이트 오류가 발생했습니다.", e);
		}
		return result;
	}
}

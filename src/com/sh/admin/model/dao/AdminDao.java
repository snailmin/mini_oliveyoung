package com.sh.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.admin.model.entity.UserDel;
import com.sh.admin.model.exception.AdminException;
import com.sh.product.model.entity.Product;
import com.sh.user.model.entity.Purchase;
import com.sh.user.model.entity.User;

public class AdminDao {
	private Properties prop = new Properties();
	
	public AdminDao() {
		try {
			prop.load(new FileReader("resources/admin-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<User> findUserAll(Connection conn) {
		List<User> users = new ArrayList<>();
		String sql = prop.getProperty("findUserAll");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					User user = new User();
					user.setId(rset.getString("user_id"));
					user.setName(rset.getString("user_name"));
					user.setPassword(rset.getString("password"));
					user.setBirthday(rset.getDate("birthday"));
					user.setSkinType(rset.getString("skin_type"));
					user.setCreatedAt(rset.getTimestamp("created_at"));
					user.setMileage(rset.getDouble("mileage"));
					users.add(user);
				}
			}
		} catch (SQLException e) {
			throw new AdminException("유저 전체 조회 오류!", e);
		}
		return users;
	}

	public List<UserDel> findUserDel(Connection conn) {
		List<UserDel> usersDel = new ArrayList<>();
		String sql = prop.getProperty("findUserDel");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					UserDel userDel = new UserDel();
					userDel.setNo(rset.getInt("no"));
					userDel.setUserId(rset.getString("user_id"));
					userDel.setUserName(rset.getString("user_name"));
					userDel.setCreatedAt(rset.getTimestamp("created_at"));
					userDel.setDelAt(rset.getTimestamp("del_at"));
					usersDel.add(userDel);
				}
			}
		} catch (SQLException e) {
			throw new AdminException("유저 전체 조회 오류!", e);
		}
		return usersDel;
	}

	public List<Purchase> findPurchaseListAll(Connection conn) {
		List<Purchase> purchases = new ArrayList<>();
		String sql = prop.getProperty("findPurchaseListAll");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					Purchase purchase = new Purchase(handleProductResultSet(rset));
					purchase.setNo(rset.getString("no"));
					purchase.setId(rset.getString("user_id"));
					purchase.setProductCode(rset.getString("product_code"));
					purchase.setCount(rset.getInt("count"));
					purchase.setPurchasedAt(rset.getTimestamp("purchased_at"));
					purchase.setPayPrice(rset.getInt("pay_price"));
					purchase.setMileage(rset.getDouble("using_mileage"));
					purchases.add(purchase);
				}
			}
		} catch (SQLException e) {
			throw new AdminException("유저 전체 조회 오류!", e);
		}
		return purchases;
	}
	
	public Product handleProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setProductName(rset.getString("product_name"));
		product.setProductCode(rset.getString("product_code"));
		product.setCategoryCode(rset.getString("category_code"));
		product.setBrandName(rset.getString("brand_name"));
		product.setPrice(rset.getInt("price"));
		product.setStock(rset.getInt("stock"));
		return product;
	}

	public Product findByProductCode(Connection conn, String productCode) {
		Product product = new Product();
		String sql = prop.getProperty("findByProductCode");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productCode);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					String productName = rset.getString("product_name");
					String _productCode = rset.getString("product_code");
					String categoryCode = rset.getString("category_code");
					String brandName = rset.getString("brand_name");
					int price = rset.getInt("price");
					int stock = rset.getInt("stock");
					product = new Product(productName, _productCode, categoryCode, brandName, price, stock);
				}
			}
		} catch (SQLException e) {
			throw new AdminException("제품 번호 조회 오류!", e);
		}
		return product;
	}

	public int addProduct(Connection conn, Product product) {
		int result = 0;
		String sql = prop.getProperty("addProduct");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getProductName());
			pstmt.setString(2, product.getProductCode());
			pstmt.setString(3, product.getCategoryCode());
			pstmt.setString(4, product.getBrandName());
			pstmt.setInt(5, product.getPrice());
			pstmt.setInt(6, product.getStock());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AdminException("제품 추가 오류!", e);
		}
		return result;
	}

	public List<String> changeToIngredientCode(Connection conn, String ingredient) {
		List<String> ingredientsCode = new ArrayList<>();
		String sql = prop.getProperty("changeToIngredientCode");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, ingredient);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					ingredientsCode.add(rset.getString("ingredient_code"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ingredientsCode;
	}
	
	public int addIngredient(Connection conn, String productCode, String ingredient) {
		int result = 0;
		String sql = prop.getProperty("addIngredient");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productCode);
			pstmt.setString(2, ingredient);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AdminException("제품 추가 오류!", e);
		}
		return result;
	}

	public int productInfoUpdate(Connection conn, Product product, String choice, Object newValue) {
		int result = 0;
		String sql = prop.getProperty("productInfoUpdate");
		sql = sql.replace("#", choice);
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setObject(1, newValue);
			pstmt.setString(2, product.getProductCode());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AdminException("제품 수정 오류!", e);
		}
		return result;
	}

	public int deleteProduct(Connection conn, Product product) {
		int result = 0;
		String sql = prop.getProperty("deleteProduct");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getProductCode());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new AdminException("제품 수정 오류!", e);
		}
		return result;
	}
	
	public String findByIngredient(Connection conn, String ingredient) {
		String test = null;
		String sql = prop.getProperty("findByIngredient");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, ingredient);
			try (ResultSet rset = pstmt.executeQuery()) {
				if (rset.next())
					test = rset.getString("ingredient_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return test;
	}
}

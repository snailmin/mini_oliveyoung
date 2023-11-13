package com.sh.product.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;
import com.sh.product.model.exception.ProductException;
import com.sh.user.model.entity.Purchase;

public class ProductDao {
	private Properties prop = new Properties();
	
	public ProductDao() {
		try {
			prop.load(new FileReader("resources/product-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> findProductAll(Connection conn) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findProductAll");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ProductException("제품명 검색 오류!", e);
		}
		return products;
	}
	
	public List<Product> findByName(Connection conn, String name) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findByName");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + name + "%");
			try(ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ProductException("제품명 검색 오류!", e);
		}
		return products;
	}

	public List<Product> findByCategoryCode(Connection conn, String categoryCode) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findByCategoryCode");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, categoryCode);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ProductException("제품명 검색 오류!", e);
		}
		return products;
	}
	
	public List<Product> findBySkinType(Connection conn, String skinType) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findBySkinType");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, skinType);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ProductException("제품명 검색 오류!", e);
		}
		return products;
	}

	public List<Product> findByIngredient(Connection conn, String ingredient) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findByIngredient");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, ingredient);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ProductException("제품명 검색 오류!", e);
		}
		return products;
	}

	public List<Product> findByBrand(Connection conn, String brand) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findByBrand");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, brand);
			try (ResultSet rset = pstmt.executeQuery()) {
				while(rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public List<Product> findByPrice(Connection conn, int min, int max) {
		List<Product> products = new ArrayList<>();
		String sql = prop.getProperty("findByPrice");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setLong(1, min);
			pstmt.setLong(2, max);
			try (ResultSet rset = pstmt.executeQuery()) {
				while (rset.next()) {
					products.add(handleProductResultSet(rset));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public Product handleProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setProductName(rset.getString("product_name"));
		product.setProductCode(rset.getString("product_code"));
		product.setCategoryCode(rset.getString("category_name"));
		product.setBrandName(rset.getString("brand_name"));
		product.setPrice(rset.getInt("price"));
		product.setStock(rset.getInt("stock"));
		return product;
	}
	
	public Product handleJoinProductResultSet(ResultSet rset) throws SQLException {
		Product product = new Product();
		product.setProductName(rset.getString("product_name"));
		product.setProductCode(rset.getString("product_code"));
		product.setCategoryCode(rset.getString("category_code"));
		product.setBrandName(rset.getString("brand_name"));
		product.setPrice(rset.getInt("price"));
		product.setStock(rset.getInt("stock"));
		return product;
	}
	
	
	//////////////////////////////////////////////////
	// 231106 민정 추가
	public int insertCart(Connection conn, Cart cart) {
		String sql = prop.getProperty("insertCart");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cart.getId());
			pstmt.setString(2, cart.getProductCode());
			pstmt.setInt(3, cart.getCount());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("장바구니 담기 오류!", e);
		}
		
		return result;
	}

	public int insertPurchase(Connection conn, Purchase purchase) {
		String sql = prop.getProperty("insertPurchase");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, purchase.getId());
			pstmt.setString(2, purchase.getProductCode());
			pstmt.setInt(3, purchase.getCount());
			pstmt.setDouble(4, purchase.getPayPrice());
			pstmt.setDouble(5, purchase.getMileage());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("구매하기 오류!", e);
		}
		
		return result;
	}

	public Cart findByProductCode(Connection conn, String productCode, String id) {
		String sql = prop.getProperty("findByProductCode");
		Cart cart = null;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productCode);
			pstmt.setString(2, id);
			
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					String no = rset.getString("no");
					Timestamp cartedAt = rset.getTimestamp("carted_at");
					int count = rset.getInt("count");
					cart = new Cart(no, id, productCode, count, cartedAt);
				}
			}
			
		} catch (SQLException e) {
			throw new ProductException("장바구니 조회 오류!", e);
		}
		return cart;
	}

	public int updateCart(Connection conn, String id, String productCode, int totalCount) {
		int result = 0;
		String sql = prop.getProperty("updateCart");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, totalCount);
			pstmt.setString(2, productCode);
			pstmt.setString(3, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("장바구니 담기 오류!", e);
		}
		return result;
	}

	public int deleteCart(Connection conn, String cartNo) {
		int result = 0;
		String sql = prop.getProperty("deleteCart");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cartNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ProductException("구매하기 오류!", e);
		}
		
		
		return result;
	}
	
}

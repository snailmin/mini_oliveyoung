package com.sh.review.model.dao;

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

import com.sh.product.model.dao.ProductDao;
import com.sh.product.model.entity.Product;
import com.sh.review.model.entity.Review;
import com.sh.review.model.exception.ReviewException;
import com.sh.review.model.vo.ProductReview;
import com.sh.review.model.vo.ProductReview;

public class ReviewDao {
	private Properties prop = new Properties();
	private ProductDao productDao = new ProductDao();
	
	public ReviewDao(){
		try {
			prop.load(new FileReader("resources/review-query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public List<Review> findAllReviews(Connection conn) {
		List<Review> reviews = new ArrayList<>();
		String sql = prop.getProperty("findAllReviews");

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rset = pstmt.executeQuery();) {

			while (rset.next()) {
				reviews.add(handleReviewResultSet(rset));
			}

		} catch (SQLException e) {
			throw new ReviewException("리뷰 전체보기 오류 발생", e);
		}

		return reviews;
	}
	
	
	public List<ProductReview> findAllProductReviews(Connection conn) {
		List<ProductReview> productReviews = new ArrayList<>();
		String sql = prop.getProperty("findAllProductReviews");
		
		try (
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
		) {
			while(rset.next()) {
				productReviews.add(handleProductReviewResultSet(rset));
			}
		} catch (SQLException e) {
			throw new ReviewException("리뷰 전체보기 오류 발생", e);
		}
		
		return productReviews;
	}
	
	public List<ProductReview> findUserReviews(Connection conn, String id) {
		List<ProductReview> productReviews = new ArrayList<>();
		String sql = prop.getProperty("findUserReviews");

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, id);
			
			try (ResultSet rset = pstmt.executeQuery();) {

				while (rset.next()) {
					productReviews.add(handleProductReviewResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ReviewException("내가 쓴 리뷰보기 오류 발생", e);
		}

		return productReviews;
	}
	
	

	public ProductReview handleProductReviewResultSet(ResultSet rset) throws SQLException{
		ProductReview productReviews = new ProductReview();
		
		productReviews.setReview(handleReviewResultSet(rset));
		productReviews.setProduct(productDao.handleJoinProductResultSet(rset));

		return productReviews;
	}
	
	
	
	
	public Review handleReviewResultSet(ResultSet rset) throws SQLException{
		int no = rset.getInt("no");
		int score = rset.getInt("score");
		String productCode = rset.getString("product_code");
		String id = rset.getString("user_id");
		String title = rset.getString("title");
		String contents = rset.getString("contents");
		Timestamp createdAt = rset.getTimestamp("created_at");
		String purchaseNo = rset.getString("purchase_no");
		
		return new Review(no, score, productCode, id, title, contents, createdAt, purchaseNo);
	}


	public int reviewInsert(Connection conn, Review review) {
		String sql = prop.getProperty("reviewInsert");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, review.getProductCode());
			pstmt.setInt(2, review.getScore());
			pstmt.setString(3, review.getId());
			pstmt.setString(4, review.getTitle());
			pstmt.setString(5, review.getContents());
			pstmt.setString(6, review.getPurchaseNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ReviewException("리뷰 작성 오류 발생", e);
		}
		
		
		return result;
	}


	public Review findReviewByProductCode(Connection conn, String productCode, String id) {
		Review review = null;
		String sql = prop.getProperty("findReviewByProductCode");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.setString(2, productCode);
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					review = handleReviewResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new ReviewException("리뷰 작성 오류 발생", e);
		}
		
		return review;
	}


	public List<ProductReview> reviewSearch(Connection conn, String column, String keyword) {
		List<ProductReview> productReviews = new ArrayList<>();
		String sql = prop.getProperty("findReviewByKeyword");
		sql = sql.replace("#", column);

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, keyword);
			
			try (ResultSet rset = pstmt.executeQuery();) {

				while (rset.next()) {
					productReviews.add(handleProductReviewResultSet(rset));
				}
			}
		} catch (SQLException e) {
			throw new ReviewException("리뷰 키워드 검색 오류 발생", e);
		}

		return productReviews;
	}


	public ProductReview findReviewByReviewNo(Connection conn, int reveiwNo) {
		ProductReview reviewDetail = null;
		String sql = prop.getProperty("findReviewByReviewNo");		

		try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, reveiwNo);
			try (ResultSet rset = pstmt.executeQuery();) {
				if (rset.next()) {
					reviewDetail = handleProductReviewResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new ReviewException("리뷰 자세히보기 오류 발생", e);
		}

		return reviewDetail;
	}


	public int updateMyReview(Connection conn, int reviewNo, String newContent) {
		String sql = prop.getProperty("updateMyReview");
		int result = 0;
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newContent);
			pstmt.setInt(2, reviewNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ReviewException("리뷰 작성 오류 발생", e);
		}
		
		
		return result;
	}


	public Review findReviewByPurchaseNo(Connection conn, String purchaseNo) {
		Review review = null;
		String sql = prop.getProperty("findReviewByPurchaseNo");
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, purchaseNo);
			try (ResultSet rset = pstmt.executeQuery()) {
				if(rset.next()) {
					review = handleReviewResultSet(rset);
				}
			}
		} catch (SQLException e) {
			throw new ReviewException("리뷰 작성 오류 발생", e);
		}
		
		return review;
	}
}

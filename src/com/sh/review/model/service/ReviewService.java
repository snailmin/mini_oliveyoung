package com.sh.review.model.service;

import static com.sh.common.JdbcTemplate.close;
import static com.sh.common.JdbcTemplate.commit;
import static com.sh.common.JdbcTemplate.getConnection;
import static com.sh.common.JdbcTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.sh.review.model.dao.ReviewDao;
import com.sh.review.model.entity.Review;
import com.sh.review.model.vo.ProductReview;
import com.sh.review.model.vo.ProductReview;

public class ReviewService {
	private ReviewDao reviewDao = new ReviewDao();

	public List<Review> findAllReviews() {
		List<Review> reviews = null;
		
		Connection conn = getConnection();
		reviews = reviewDao.findAllReviews(conn);
		
		return reviews;
	}

	public List<ProductReview> findAllProductReviews() {
		List<ProductReview> productReviews = null;
		
		Connection conn = getConnection();
		productReviews = reviewDao.findAllProductReviews(conn);
		
		return productReviews;
	}

	public List<ProductReview> findUserReviews(String id) {
		List<ProductReview> productReviews = null;
		
		Connection conn = getConnection();
		productReviews = reviewDao.findUserReviews(conn, id);
		
		return productReviews;
	}

	// 트랜젝션
	public int reviewInsert(Review review) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = reviewDao.reviewInsert(conn, review);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public Review findReviewByProductCode(String productCode, String id) {
		Review review = null;
		Connection conn = getConnection();
		
		review = reviewDao.findReviewByProductCode(conn, productCode, id);
		
		return review;
	}

	public List<ProductReview> reviewSearch(String column, String keyword) {
		List<ProductReview> productReviews = null;
		
		Connection conn = getConnection();
		productReviews = reviewDao.reviewSearch(conn, column, keyword);
		
		return productReviews;
	}

	public ProductReview findReviewByReviewNo(int reveiwNo) {
		ProductReview reviewDetail = null;
		
		Connection conn = getConnection();
		reviewDetail = reviewDao.findReviewByReviewNo(conn, reveiwNo);
		
		return reviewDetail;
	}

	public int updateMyReview(int reviewNo, String newContent) {
		int result = 0;
		Connection conn = getConnection();
		try {
			result = reviewDao.updateMyReview(conn, reviewNo, newContent);
			commit(conn);
		} catch (Exception e) {
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return result;
	}

	public Review findReviewByPurchaseNo(String purchaseNo) {
		Review review = null;
		Connection conn = getConnection();
		
		review = reviewDao.findReviewByPurchaseNo(conn, purchaseNo);
		
		return review;
	}
	
}

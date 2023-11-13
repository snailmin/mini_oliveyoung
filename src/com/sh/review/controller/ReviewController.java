package com.sh.review.controller;

import java.util.List;

import com.sh.review.model.entity.Review;
import com.sh.review.model.service.ReviewService;
import com.sh.review.model.vo.ProductReview;
import com.sh.review.model.vo.ProductReview;

public class ReviewController {
	private ReviewService reviewService = new ReviewService();

	public List<Review> findAllReviews() {
		List<Review> reviews = null;
		
		try {
			reviews = reviewService.findAllReviews();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return reviews;
	}

	public List<ProductReview> findAllProductReviews() {
		List<ProductReview> productReviews = null;
		
		try {
			productReviews = reviewService.findAllProductReviews();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return productReviews;
	}

	public List<ProductReview> findUserReviews(String id) {
		List<ProductReview> productReviews = null;
		
		try {
			productReviews = reviewService.findUserReviews(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return productReviews;
	}

	public int reviewInsert(Review review) {
		int result = 0;
		
		try {
			result = reviewService.reviewInsert(review);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return result;
	}

	public Review findReviewByProductCode(String productCode, String id) {
		Review review = null;
		
		try {
			review = reviewService.findReviewByProductCode(productCode, id);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return review;
	}

	public List<ProductReview> reviewSearch(String column, String keyword) {
		List<ProductReview> productReviews = null;
		
		try {
			productReviews = reviewService.reviewSearch(column, keyword);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		return productReviews;
	}

	public ProductReview findReviewByReviewNo(int reveiwNo) {
		ProductReview reviewDetail = null;
		
		try {
			reviewDetail = reviewService.findReviewByReviewNo(reveiwNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return reviewDetail;
	}

	public int updateMyReview(int reviewNo, String newContent) {
		int result = 0;
		
		try {
			result = reviewService.updateMyReview(reviewNo, newContent);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return result;
	}

	public Review findReviewByPurchaseNo(String purchaseNo) {
		Review review = null;
		
		try {
			review = reviewService.findReviewByPurchaseNo(purchaseNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("> 불편을 드려 죄송합니다." + e.getMessage());
		}
		
		return review;
	}
	
}

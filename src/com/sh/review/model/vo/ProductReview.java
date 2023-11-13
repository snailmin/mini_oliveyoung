package com.sh.review.model.vo;

import com.sh.product.model.entity.Product;
import com.sh.review.model.entity.Review;

public class ProductReview {
	private Product product;
	private Review review;
	public ProductReview() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductReview(Product product, Review review) {
		super();
		this.product = product;
		this.review = review;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	@Override
	public String toString() {
		return "ProductReview [product=" + product + ", review=" + review + "]";
	}
}

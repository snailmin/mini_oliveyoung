package com.sh.review.model.entity;

import java.sql.Timestamp;
import java.time.LocalTime;

public class Review {
	private int no;
	private int score;
	private String productCode;
	private String id;
	private String title;
	private String contents;
	private Timestamp createdAt;
	private String purchaseNo;
	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Review(int no, int score, String productCode, String id, String title, String contents, Timestamp createdAt,
			String purchaseNo) {
		super();
		this.no = no;
		this.score = score;
		this.productCode = productCode;
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.createdAt = createdAt;
		this.purchaseNo = purchaseNo;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	@Override
	public String toString() {
		return "Review [no=" + no + ", score=" + score + ", productCode=" + productCode + ", id=" + id + ", title="
				+ title + ", contents=" + contents + ", createdAt=" + createdAt + ", purchase_no=" + purchaseNo + "]";
	}
	
	
	
	
}
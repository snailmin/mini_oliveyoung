package com.sh.user.model.entity;

import java.sql.Timestamp;

import com.sh.product.model.entity.Product;

public class Purchase extends Product {
	private String no;
	private String id;
	private String productCode;
	private int count;
	private Timestamp purchasedAt;
	private double payPrice;
	private double mileage;
	
	public Purchase(Product product) {
		super.setProductName(product.getProductName());
		super.setProductCode(product.getProductCode());
		super.setCategoryCode(product.getCategoryCode());
		super.setBrandName(product.getBrandName());
		super.setPrice(product.getPrice());
	}

	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Purchase(String productName, String productCode, String categoryCode, String brandName, int price,
			int stock) {
		super(productName, productCode, categoryCode, brandName, price, stock);
		// TODO Auto-generated constructor stub
	}

	public Purchase(String no, String id, String productCode, int count, Timestamp purchasedAt, double payPrice,
			double mileage) {
		super();
		this.no = no;
		this.id = id;
		this.productCode = productCode;
		this.count = count;
		this.purchasedAt = purchasedAt;
		this.payPrice = payPrice;
		this.mileage = mileage;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Timestamp getPurchasedAt() {
		return purchasedAt;
	}

	public void setPurchasedAt(Timestamp purchasedAt) {
		this.purchasedAt = purchasedAt;
	}

	public double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	@Override
	public String toString() {
		return "Purchase [no=" + no + ", id=" + id + ", productCode=" + productCode + ", count=" + count
				+ ", purchasedAt=" + purchasedAt + ", payPrice=" + payPrice + ", mileage=" + mileage + "]";
	}

	
	
}

package com.sh.product.model.entity;

import java.sql.Timestamp;

public class Cart extends Product{
	private String no;
	private String id;
	private String productCode;
	private int count;
	private Timestamp CartedAt;
	
	public Cart(Product product) {
		super.setProductName(product.getProductName());
		super.setProductCode(product.getProductCode());
		super.setProductCode(product.getProductCode());
		super.setCategoryCode(product.getCategoryCode());
		super.setBrandName(product.getBrandName());
		super.setPrice(product.getPrice());
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(String productName, String productCode, String categoryCode, String brandName, int price, int stock) {
		super(productName, productCode, categoryCode, brandName, price, stock);
		// TODO Auto-generated constructor stub
	}

	public Cart(String no, String id, String productCode, int count, Timestamp cartedAt) {
		super();
		this.no = no;
		this.id = id;
		this.productCode = productCode;
		this.count = count;
		CartedAt = cartedAt;
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

	public Timestamp getCartedAt() {
		return CartedAt;
	}

	public void setCartedAt(Timestamp cartedAt) {
		CartedAt = cartedAt;
	}

	@Override
	public String toString() {
		return "Cart [no=" + no + ", id=" + id + ", productCode=" + productCode + ", count=" + count + ", CartedAt="
				+ CartedAt + "]";
	}
	
	
	
	
	
}

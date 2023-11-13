package com.sh.product.model.entity;

public class Product {
	private String productName;
	private String productCode;
	private String categoryCode;
	private String brandName;
	private int price;
	private int stock;
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String productName, String productCode, String categoryCode, String brandName, int price,
			int stock) {
		super();
		this.productName = productName;
		this.productCode = productCode;
		this.categoryCode = categoryCode;
		this.brandName = brandName;
		this.price = price;
		this.stock = stock;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Product [productName=" + productName + ", productCode=" + productCode + ", categoryCode=" + categoryCode
				+ ", brandName=" + brandName + ", price=" + price + ", stock=" + stock + "]";
	}
	
}

package com.sh.product.model.entity;

public class Category {
	private String categoryName;
	private String categoryCode;
	
	public Category() {}
	
	public Category(String categoryName, String categoryCode) {
		this.categoryName = categoryName;
		this.categoryCode = categoryCode;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", categoryCode=" + categoryCode + "]";
	}
}

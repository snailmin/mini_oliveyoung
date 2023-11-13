package com.sh.product.model.vo;

import com.sh.product.model.entity.Cart;
import com.sh.product.model.entity.Product;

public class SumProductInCart extends Product{
	private Cart cart;
	private int sumCount;
	private int sumPrice;
	public SumProductInCart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SumProductInCart(Cart cart) {
		this.cart.setId(cart.getId());
	}
	
	public SumProductInCart(String productName, String productCode, String categoryCode, String brandName, int price,
			int stock) {
		super(productName, productCode, categoryCode, brandName, price, stock);
		// TODO Auto-generated constructor stub
	}
	public SumProductInCart(Cart cart, int sumCount, int sumPrice) {
		super();
		this.cart = cart;
		this.sumCount = sumCount;
		this.sumPrice = sumPrice;
	}
	
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public int getSumCount() {
		return sumCount;
	}
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}
	public int getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}
	@Override
	public String toString() {
		return "SumProductInCart [cart=" + cart + ", sumCount=" + sumCount + ", sumPrice=" + sumPrice + "]";
	}
	
	
	
	
	
}

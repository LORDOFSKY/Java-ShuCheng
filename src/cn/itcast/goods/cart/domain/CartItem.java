package cn.itcast.goods.cart.domain;

import java.math.BigDecimal;

import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.user.domain.User;

public class CartItem {
	private String cartItemId;//主键
	private int quantity;//数量
	private Book book;//条目对应的图书
	private User user;//所属用户
	
	
	//添加小计方法
	public Double getSubtotal() {
		//return book.getCurrPrice() * quantity; 有误差
		/*
		 * 使用BigDecimal来计算小数不会有误差
		 * 注意：BigDecimal要求使用String类型构造器
		 */
		BigDecimal b1 = new BigDecimal(book.getCurrPrice()+"");
		BigDecimal b2 = new BigDecimal(quantity+"");
		BigDecimal b3 = b1.multiply(b2);//计算
		return b3.doubleValue();//将字符串转为double
	}
	
	
	
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

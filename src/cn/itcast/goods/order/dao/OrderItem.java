package cn.itcast.goods.order.dao;

import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.order.domain.Order;

public class OrderItem {
	private String orderItemId;//主键
	private int quantity;//数量
	private double subtotal;//小计
	private Book book;//所关联的Book
	private Order order;//所属的订单
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}

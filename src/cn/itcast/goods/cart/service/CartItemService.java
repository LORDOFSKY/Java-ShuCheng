package cn.itcast.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.cart.dao.CartItemDao;
import cn.itcast.goods.cart.domain.CartItem;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid){
		try {
			return cartItemDao.findByUid(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void add(CartItem cartItem) {
		try {
			/*
			 * 1.使用uid和bid去数据库中查询这个条目是否存在
			 */
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(),cartItem.getBook().getBid()); 
			if(_cartItem == null) {//如果数据库没有这个条目，则添加条目
				cartItem.setCartItemId(CommonUtils.uuid());
				cartItemDao.addCartItem(cartItem);
			}else {//如果数据库中有这个条目，则修改数量
				//使用原有数量和新条目之和来作为新的数量
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				//修改老条目的数量
				_cartItem.setQuantity(quantity);
				cartItemDao.updateQuantity(_cartItem.getCartItemId(),quantity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 批量删除条目
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 修改购物车条目数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId , int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId,quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			 return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}

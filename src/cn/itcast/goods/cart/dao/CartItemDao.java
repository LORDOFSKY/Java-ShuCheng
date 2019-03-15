package cn.itcast.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.cart.domain.CartItem;
import cn.itcast.goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 通过用户查询购物车条目
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public List<CartItem> findByUid(String uid) throws SQLException {
		String sql = "select * from t_cartitem c, t_book b where c.bid=b.bid and uid=? order by c.orderBy";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(mapList);
	}

	/*
	 * 把一个Map映射成一个CartItem
	 */
	private CartItem toCartItem(Map<String, Object> map) {
		if (map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}

	/*
	 * 把多个Map（即List<Map<String,Object>>）映射成多个CartItem（即List<CartItem>）
	 */
	private List<CartItem> toCartItemList(List<Map<String, Object>> mapList) {
		List<CartItem> cartItemList = new ArrayList<CartItem>();// 新建集合来放cartItem
		for (Map<String, Object> map : mapList) {
			CartItem cartItem = toCartItem(map);// 把一个Map映射成一个CartItem
			cartItemList.add(cartItem);// 将cartItem依次添加进集合
		}
		return cartItemList;
	}
	
	
	/*
	 * 查询某个用户某本图书的购物车条目是否存在
	 */
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException {
		String sql = "select * from t_cartitem where uid=? and bid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), uid, bid);
		CartItem cartItem =toCartItem(map);
		return cartItem;
	}
	
	
	/**
	 * 添加条目
	 * @param cartItem
	 * @throws SQLException
	 */
	public void addCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem(cartItemId,quantity,uid,bid) values(?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(), cartItem.getQuantity(), cartItem.getUser().getUid(), cartItem.getBook().getBid()};
		qr.update(sql,params);
	}
	
	
	/**
	 * 修改指定条目的数量
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException
	 */
	public void updateQuantity(String cartItemId, int quantity) throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql,quantity,cartItemId);
	}
	
	
	
	
	/**
	 * 批量删除条目
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String cartItemIds) throws SQLException {
		/*
		 * 需要先把cartItems转换成数组
		 * 1.把cartItems转换成一个where语句
		 * 2.与delete from连接在一起，再执行
		 */
		Object[] cartItemIdArray = cartItemIds.split(",");//将cartItemIds根据“，”切割成数组
		String whereSql = toWhereSql(cartItemIdArray.length);//调用toWhereSql方法生成where语句
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql, cartItemIdArray);
	}
	
	/*
	 * 用来生成where语句，根据所给参数生成“cartItemId in（？，？，？，...）”
	 */
	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i = 0;i<len;i++) {
			sb.append("?");
			if(i<len-1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * 按Id查询
	 * @param cartItemId
	 * @return
	 * @throws SQLException
	 */
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartitem c , t_book b where c.bid = b.bid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),cartItemId);
		return toCartItem(map);
	}
	
	/**
	 * 加载多个CartItem
	 * @param cartItemIds
	 * @return
	 * @throws SQLException
	 */
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException {
		//把cartItemIds转化为数组
		Object[] cartItemIdArray = cartItemIds.split(",");
		//生成where子句
		String whereSql = toWhereSql(cartItemIdArray.length);
		//生成sql语句
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and " + whereSql;
		//执行sql，返回List<CartItem>
		return toCartItemList(qr.query(sql, new MapListHandler(),cartItemIdArray));
	}
	
	
	
	
}

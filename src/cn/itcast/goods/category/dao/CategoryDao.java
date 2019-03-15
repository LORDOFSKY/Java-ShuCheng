package cn.itcast.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

/**
 * 
 * 分类持久层
 * @author MyPC
 *
 */
public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 返回所有分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findAll() throws SQLException{
		/*
		 * 1.查询所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		List<Category> parents = toCategoryList(mapList);
		
		/*
		 * 2.循环遍历所有一级分类，为每个一级分类加载它的二级分类
		 */
		for(Category parent :parents) {
			//查询当前所有父分类的所有子分类
			List<Category> children = findByParent(parent.getCid());
			//设置给父分类
			parent.setChildren(children);
		}
		
		return parents;
	}
	
	/**
	 * 通过父分类查询子分类
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findByParent(String pid) throws SQLException{
		String sql = "select * from t_category where pid=? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),pid);
		return toCategoryList(mapList);
	}

	
	
	
	/*
	 * 把一个Map中的数据映射到Category中
	 */
	public Category toCategory(Map<String,Object> map) {
		/*
		 * map:{cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx,cname:xx, parent:(cid=pid), desc:xx}
		 * 
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String)map.get("pid");// 如果是一级分类，那么pid是null
		if(pid!=null) {//如果父分类ID不为空
			/*
			 * 使用一个父分类装载pid；
			 * 再将父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid("pid");
			category.setParent(parent);
		}
		return category;
	}
	
	
	
	/*
	 * 把多个Map（即List<Map>）映射成多个Category (即List<Categopy>)
	 */
	public List<Category> toCategoryList(List<Map<String,Object>> mapList){
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String,Object> map:mapList) {
			Category c = toCategory(map);
			categoryList.add(c);
		}
		return categoryList;
		
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";//desc是关键字，之前有用到，在这里做下处理 加``号
		/*
		 * 因为一级分类，没有parent，而二级分类有！
		 * 这个方法，要兼容两次分类，所以需要判断
		 */	
		String pid = null;//一级分类
		if(category.getParent() != null) {//二级分类
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCid(),category.getCname(),pid,category.getDesc()};
		qr.update(sql,params);
	}
	
	

	/**
	 * 获取所有父分类，但不带子分类
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents() throws SQLException{
		/*
		 * 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		
		return toCategoryList(mapList);
	}	
	
	
	/**
	 * 加载分类  （可加载一级分类、二级分类）
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(),cid));
	}
	
	/**
	 * 修改分类（可修改一级分类，二级分类）
	 * @param category
	 * @throws SQLException
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?,pid=?,`desc`=? where cid=?";
		String pid = null;//一级分类为空
		if(category.getParent() != null) {//二级分类不为空
			pid = category.getParent().getCid();
		}
		Object[] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		qr.update(sql,params);
	}
	
	
	/*
	 * 查询指定父分类下的分类的个数
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql = "select count(*) from t_category where pid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(),pid);
		return cnt == null ? 0 :cnt.intValue();
	}
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql,cid);
	}
	
}

package cn.itcast.goods.category.domain;

import java.util.List;

/**
 * 分类模块实体类
 * @author MyPC
 *
 */
public class Category {
	private String cid;//主键
	private String cname;//主键名称
//	private String pid;//父分类的ID
//  pid是外键 要找到外键对应的表（此处为原表） 改为父分类的对象
	private Category parent;//父分类
	private String desc;//分类描述
	private List<Category> children;//子分类
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<Category> getChildren() {
		return children;
	}
	public void setChildren(List<Category> children) {
		this.children = children;
	}
}
 
package cn.itcast.goods.pager;

/**
 * 条件结构 name operator value 例如：bid = 1
 * @author MyPC
 *
 */
public class Expression {
	private String name;
	private String operator;
	private String value; 
	
	@Override
	public String toString() {
		return "Expression [name=" + name + ", operator=" + operator + ", value=" + value + "]";
	}
	public Expression(String name, String operator, String value) {
		super();
		this.name = name;
		this.operator = operator;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

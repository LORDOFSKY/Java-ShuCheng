package cn.itcast.goods.user.domain;
/**
 * 用户实体类
 * @author MyPC
 *
 */
/**
 * 属性来源：t_user表：将表中查询出的数据封装到对象中
 *
 */
public class User {
		//对应数据库表
		private String uid;	//主键
		private String loginname;	//登录名
		private String loginpass;	//登录密码
		private String email;		//邮箱
		private boolean status;			//状态，1表示已激活；0表示未激活
		private String activationCode;	//激活码，唯一值；用小工具生成的uuid
		
		//注册表单
		private String reloginpass;//确认密码
		private String verifyCode;//验证码
		
		//修改密码表单
		private String newpass;
		
		public String getNewpass() {
			return newpass;
		}
		public void setNewpass(String newpass) {
			this.newpass = newpass;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getLoginname() {
			return loginname;
		}
		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}

	public String getLoginpass() {
			return loginpass;
		}
		
		public String getReloginpass() {
			return reloginpass;
		}
		public void setReloginpass(String reloginpass) {
			this.reloginpass = reloginpass;
		}
		public String getVerifyCode() {
			return verifyCode;
		}
		public void setVerifyCode(String verifyCode) {
			this.verifyCode = verifyCode;
		}
		public void setLoginpass(String loginpass) {
			this.loginpass = loginpass;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public String getActivationCode() {
			return activationCode;
		}
		public void setActivationCode(String activationCode) {
			this.activationCode = activationCode;
		}
		@Override
		public String toString() {
			return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", email=" + email
					+ ", status=" + status + ", activationCode=" + activationCode + ", reloginpass=" + reloginpass
					+ ", verifyCode=" + verifyCode + ", newpass=" + newpass + "]";
		}
		
		
}

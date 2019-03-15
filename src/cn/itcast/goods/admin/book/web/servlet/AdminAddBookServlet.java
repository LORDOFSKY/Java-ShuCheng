package cn.itcast.goods.admin.book.web.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.book.service.BookService;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.category.service.CategoryService;


public class AdminAddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 1.conmons-fileupload的上传三步
		 */
		//创建工具
		FileItemFactory factory = new DiskFileItemFactory();//DiskFileItemFactory()构造函数可加参数，分别为缓存大小和缓存路径   默认缓存大小为10kb
		
		/*
		 * 2.创建解析器对象
		 */
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80 * 1024);//设置上传的文件上限为80kb
		
		/*
		 * 3.解析request得到List<FileItem>
		 */
		List<FileItem> fileItemList = null;
		try {
			fileItemList = sfu.parseRequest(request);//解析
		} catch (FileUploadException e) {
			// 如果出现这个异常，说明单个上传文件超出了80kb
			error("上传的文件超出了80Kb",request,response);
			return;
		}
		
		/*
		 * 4.把List<FileItem>封装到Book对象中
		 * 4.1先把 普通表单字段（type为非file） 放到一个Map中，再把Map转换成Book对象和Category对象，在建立两者的关系
		 */
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem : fileItemList) {
			if(fileItem.isFormField()) {//判断是否为普通表单字段
				map.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));//从fileItem中获取name和对应的值添加进map中
			}
		}
		Book book = CommonUtils.toBean(map, Book.class);//把Map中大部分数据封装到Book对象中
		Category category = CommonUtils.toBean(map, Category.class);//把Map中cid封装到Category对象中
		book.setCategory(category);
		/*
		 * 4.2把上传的图片保存起来
		 * 	>获取文件名，截取之
		 * 	>给文件添加前缀：使用uuid前缀，避免文件名出现同名现象
		 * 	>校验文件格式
		 *	 >校验图片尺寸
		 *	>指定图片的保存路径，需使用ServletContext#getRealPath（）
		 *	>保存之
		 *	>把图片路径设置给Book对象
		 */
//大图		//获取文件名
		FileItem fileItem = fileItemList.get(1);//获取大图
		String filename = fileItem.getName();
		//截取文件名，因为部分浏览器上传的是绝对路径
		int index = filename.lastIndexOf("\\");
		if(index != -1) {//   !=-1 是有“\\”
			filename = filename.substring(index + 1);//\\后一位开始截取
		}
		//给文件名添加uuid前缀
		filename = CommonUtils.uuid() + "_" + filename;
		//校验文件名的格式（扩展名）
		if(!filename.toLowerCase().endsWith(".jpg")) {//转为小写来对比是否为“.jpg”
			error("上传图片的格式必须为JPG",request,response);
			return;
		}
		//校验图片尺寸
		//先保存上传的图片，再把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO  其中Image为抽象类，所以用ImageIcon类来实现
		/*
		 * 保存图片
		 * 1.获取真实路径
		 */
		String savepath = this.getServletContext().getRealPath("/book_img");
		/*
		 * 2.创建目标文件
		 */
		File destFile = new File(savepath,filename);
		/*
		 * 3.保存文件
		 */
		try {
			fileItem.write(destFile);//write方法会把临时文件重定向到指定路径，再删除临时文件   若二次调用将找不到文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		/*
		 * 校验尺寸
		 */
		//1.使用文件路径来创建ImageIcon  （）
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
		//2.通过ImageIcon得到Image对象
		Image image = icon.getImage();
		//3.获取宽高来进行校验
		if(image.getHeight(null)>350 || image.getHeight(null)>350) {
			error("上传的图片尺寸超出了350*350！",request,response);
			destFile.delete();//删除图片
			return;
		}
		
		//把图片路径设置给Book对象
		book.setImage_w("book_img/" + filename);
		
		
		
		
		
		
		
		
//小图		//获取文件名
				fileItem = fileItemList.get(2);//获取小图
				filename = fileItem.getName();
				//截取文件名，因为部分浏览器上传的是绝对路径
				index = filename.lastIndexOf("\\");
				if(index != -1) {//   !=-1 是有“\\”
					filename = filename.substring(index + 1);//\\后一位开始截取
				}
				//给文件名添加uuid前缀
				filename = CommonUtils.uuid() + "_" + filename;
				//校验文件名的格式（扩展名）
				if(!filename.toLowerCase().endsWith(".jpg")) {//转为小写来对比是否为“.jpg”
					error("上传图片的格式必须为JPG",request,response);
					return;
				}
				//校验图片尺寸
				//先保存上传的图片，再把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO  其中Image为抽象类，所以用ImageIcon类来实现
				/*
				 * 保存图片
				 * 1.获取真实路径
				 */
				savepath = this.getServletContext().getRealPath("/book_img");
				/*
				 * 2.创建目标文件
				 */
				destFile = new File(savepath,filename);
				/*
				 * 3.保存文件
				 */
				try {
					fileItem.write(destFile);//write方法会把临时文件重定向到指定路径，再删除临时文件   若二次调用将找不到文件
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				/*
				 * 校验尺寸
				 */
				//1.使用文件路径来创建ImageIcon  （）
				icon = new ImageIcon(destFile.getAbsolutePath());
				//2.通过ImageIcon得到Image对象
				image = icon.getImage();
				//3.获取宽高来进行校验
				if(image.getHeight(null)>350 || image.getHeight(null)>350) {
					error("上传的图片尺寸超出了350*350！",request,response);
					destFile.delete();//删除图片
					return;
				}
				
				//把图片路径设置给Book对象
				book.setImage_b("book_img/" + filename);
				
				
	//调用service完成保存（到数据库中）
		book.setBid(CommonUtils.uuid());
		BookService bookService = new BookService();
		bookService.add(book);
	
	//保存成功信息转发到msg.jsp
		request.setAttribute("msg", "添加图书成功!");
		request.setAttribute("parents", new CategoryService().findParents());//回显时要返回所有一级分类
		request.getRequestDispatcher("/adminjsps/msg.jsp").forward(request, response);
		
	}
	
	/*
	 * 保存错误信息，转发到add.jsp
	 */
	private void error(String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
	}


}

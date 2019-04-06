package web.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import domain.Category;
import domain.Product;
import service.ProductService;
import utils.CommonUtils;

public class AdminAddProduct extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Product product = new Product();
		//封装map是后期好删除
		Map<String,Object> map = new HashMap<String,Object>();
		
		try {
			//接收多功能表单提交的数据
			//1.创建磁盘文件项工厂
			DiskFileItemFactory factory  = new DiskFileItemFactory();
			//注意这里面有两个方法
//			factory.setSizeThreshold(1024*100);100kb以上的话使用缓存技术
//			factory.setRepository(repository);  缓存的文件夹
			
			//2.创建文件上传核心对象
			ServletFileUpload upload  = new ServletFileUpload(factory);
			
			//3.解析request
			List<FileItem> parseRequest = upload.parseRequest(request);
			
			//4.for循环判断文件项中是普通表单还是文件项
			for (FileItem fileItem : parseRequest) {
				//判断
				boolean formField = fileItem.isFormField();
				if(formField) {
					//普通表单项	封装到product实体中
					String fieldName = fileItem.getFieldName();
					String fieldValue = fileItem.getString("UTF-8");//设置编码
					
					//封装
					map.put(fieldName, fieldValue); 
				}else {
					//文件表单项	文件上传	获取文件名称和文件的内容
					String filename = fileItem.getName();
					String path = this.getServletContext().getRealPath("upload");//把下载文件放到upload下
					InputStream in = fileItem.getInputStream();
					
					OutputStream out = new FileOutputStream(path+"/"+filename);
					//简便代码
					IOUtils.copy(in, out);
					
					in.close();
					out.close();
//					fileItem.delete();
					/*	int len = 0;
					byte[] buffer = new byte[1024];
					while((len = in.read(buffer))>0) {
						out.write(buffer, 0, len);
						
					}*/
					
					map.put("pimage", "upload/"+filename);
				}
			}
			//封装
			BeanUtils.populate(product, map);
			
			//将少的字段进行封装
			//1.pid
			product.setPid(CommonUtils.getUUID());
			//2.pimage(在上传文件时封装)
			//3.pdate
			product.setPdate(new Date());
			//4.pflag   0上架
			product.setPflag(0);
			
			//5.category
			Category category = new Category();
			category.setCid( map.get("cid").toString());
			
			product.setCategory(category);
			
			
			//product封装完毕，传递给service，插入product
			ProductService service = new ProductService();
			service.addProduct(product);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		//返回
		response.sendRedirect(request.getContextPath()+"/adminGetAllProduct");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
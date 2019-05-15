package com.zxgs.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static  String uploadPath = "C:/upload/"; // 上传文件的目录
	private String tempPath = "C:/uploadtmp/"; // 临时文件目录
	//private String serverPath = null;

	@SuppressWarnings("unused")
	private int sizeMax = -1;// 文件最大上限
	@SuppressWarnings("unused")
	private String[] fileType = new String[] { ".jpg", ".gif", ".bmp", ".png",
			".jpeg", ".ico" };

	/**
	 * 处理用户上传请求
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = null;
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8"); 
		//serverPath = getServletContext().getRealPath("/").replace("\\", "/");
		// Servlet初始化时执行,如果上传文件目录不存在则自动创建
		if (!new File(uploadPath).isDirectory()) {
			new File(uploadPath).mkdirs();
		}
		if (!new File(tempPath).isDirectory()) {
			new File(tempPath).mkdirs();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(5 * 1024); // 最大缓存
		factory.setRepository(new File(tempPath));// 临时文件目录

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(-1);// 设置上传文件限制大小,-1无上限
		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {// 判断是否是文件流
				} else {
					String value = item.getName();// 会将完整路径名传过来
					int start = value.lastIndexOf("\\");
					String fileName = value.substring(start + 1);
					int index = fileName.lastIndexOf(".");
					//String realFileName = fileName.substring(0, index);
					UUID uuid = UUID.randomUUID();
					String type = fileName.substring(index + 1);
					String realFileName = uuid.toString()+ "." + type;
					String filePath = uploadPath + realFileName;
					File saveFile = new File(filePath);
					item.write(saveFile);
					pw = response.getWriter();
					/*pw.write("<script>alert('上传成功');window.returnValue='"
							+ uploadPath + realFileName
							+ "';</script>");*/
					pw.write(realFileName);
					pw.flush();
					pw.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			pw = response.getWriter();
			//pw.write("<script>alert('上传失败');</script>");
			pw.write("-1");
			pw.flush();
			pw.close();
		}
	}

}
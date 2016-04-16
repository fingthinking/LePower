package com.lesport.appcontroller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.mail.handlers.multipart_mixed;

/*
 * 用来测试图片，先放这里
 * 
 * @author:刘衍庆
 * 
 * 
 */


@Controller
public class TestUploadImg {

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImag(HttpServletRequest request2,
			HttpServletResponse response) {	
		
		System.out.println("jinru");
		
		MultipartHttpServletRequest request = (MultipartHttpServletRequest) request2;
		
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		
		
		System.out.println("name: "+request.getParameter("name"));		
		
		Map< String,MultipartFile>  files = request.getFileMap();
		
		for (Map.Entry<String, MultipartFile>  file: files.entrySet()) {
			
			String originalName = file.getValue().getOriginalFilename();
			
			System.out.println("originalName: "+originalName);
			
			String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
			
			String newName=System.currentTimeMillis()+"."+suffix;
			
			File newFile=new File(path+"/"+newName);			
			
			try {
				FileUtils.copyInputStreamToFile(file.getValue().getInputStream(),newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		return "fileItems=";
	}
	
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	@RequestMapping(value = "/uploadImg", method = RequestMethod.GET)
	public String uploadImag11(HttpServletRequest request, MultipartHttpServletRequest request2,
			HttpServletResponse response) {	
		
		System.out.println("get已发");
		
		String temp = request.getSession().getServletContext().getRealPath("/") + "temp"; // 临时目录

		System.out.println("temp=" + temp);

		String loadpath = request.getSession().getServletContext().getRealPath("/") + "Image"; // 上传文件存放目录

		System.out.println("loadpath=" + loadpath);

		DiskFileUpload fu = new DiskFileUpload();

		fu.setSizeMax(1 * 1024 * 1024); // 设置允许用户上传文件大小,单位:字节
		fu.setSizeThreshold(4096); // 设置最多只允许在内存中存储的数据,单位:字节
		fu.setRepositoryPath(temp); // 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录

		// 开始读取上传信息
		int index = 0;
		List fileItems = null;

		try {
			fileItems = fu.parseRequest(request2);
			System.out.println("fileItems=" + fileItems);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator iter = fileItems.iterator(); // 依次处理每个上传的文件
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();// 忽略其他不是文件域的所有表单信息
			if (!item.isFormField()) {
				String name = item.getName();// 获取上传文件名,包括路径
				name = name.substring(name.lastIndexOf("\\") + 1);// 从全路径中提取文件名
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				int point = name.indexOf(".");
				name = (new Date()).getTime() + name.substring(point, name.length()) + index;
				index++;
				File fNew = new File(loadpath, name);
				try {
					item.write(fNew);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else // 取出不是文件域的所有表单信息
			{
				String fieldvalue = item.getString();
				// 如果包含中文应写为：(转为UTF-8编码)
				// String fieldvalue = new
				// String(item.getString().getBytes(),"UTF-8");
			}
		}
		String text1 = "11";

		// response.sendRedirect("result.jsp?text1=" + text1);
		return "";
	}
	
	
	
	@RequestMapping(value = "/uploadImg2", method = RequestMethod.POST)
	public String uploadImag2(HttpServletRequest request, MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response) throws IOException {
		
		System.out.println("已进入2");
		
		//设置文件上传路径
		String UploadFilePath = "d:/upload22";
		//限制文件大小
		//通过 request.getHeader("Content-Length") 获取request 请求内容长度来限制
		if(ServletFileUpload.isMultipartContent(request)) {
		 
		    //创建ServletFileUpload实例
		    ServletFileUpload upload = new ServletFileUpload();
		    try {
		        //解析request 请求 并返回FileItemStream 的iterator 实例
		        FileItemIterator iter = upload.getItemIterator(request);
		        
		        System.out.println();
		        
		        while (iter.hasNext()) {
		            FileItemStream item = iter.next();
		            String name = item.getFieldName();
		            InputStream stream = item.openStream();
		            if (item.isFormField()) {
		                System.out.println("Form field " + name + " with value "
		                        + Streams.asString(stream) + " detected.");
		            } else {
		                System.out.println("File field " + name + " with file name "
		                        + item.getName() + " detected.");
		                // Process the input stream
		                //System.out.println(Streams.asString(stream));
		                System.out.println("shuru:");
		                String filename = new Scanner(System.in).next();
		                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(new File("e:/upload"),"ez.zip")));
		                BufferedInputStream bis = new BufferedInputStream(stream);
		                byte[] buffer = new byte[1024];
		                int len = -1;
		                while (-1 != (len = bis.read(buffer))){
		                    bos.write(buffer,0,len);
		                }
		                bis.close();
		                bos.flush();
		                bos.close();
		 
		                PrintWriter out = response.getWriter();
		                out.write("完成");
		                System.out.println("wanchengle ");
		            }
		        }
		    } catch (FileUploadException e) {
		        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		    }
		} else {
		    throw new RuntimeException("请设置form表单的enctype属性");
		}
		return UploadFilePath;
	}

}

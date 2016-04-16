package com.lesport.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.lang.jstl.test.PageContextImpl;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

public class UploadImage {

	//进行上传图片的处理，对原图进行压缩，将原图和缩略图保存到服务器指定文件夹，并生成保存路径存入数据库
		static public String uploadPicture(HttpServletRequest request,MultipartFile pirUrl) 
		{
			    String basePath = request.getSession().getServletContext().getRealPath("");
			//获取服务器实际路径
			    
				String filename=pirUrl.getOriginalFilename();
				String wholePath="";
				String prePath = "pages/img";
				//生成图片的相对路径
				int index=filename.lastIndexOf(".");
				String suffix=filename.substring(index+1);//获得文件后缀名
				String uploadFileName=System.currentTimeMillis()+String.valueOf(0); //生成随机文件名，规则：系统当前时间+图片编号
				//新的图片名称
				String newFileName=uploadFileName+"."+suffix;				
				//上传文件的相对路径
				String srcImagePath="/"+prePath+"/"+uploadFileName+"."+suffix;
				//文件上传
				String srcImgRealpath=basePath+srcImagePath;//原图的绝对路径
				
				System.out.println("++++++++++："+srcImgRealpath);
				try{
					pirUrl.transferTo(new File(srcImgRealpath));
					//UploadProcess.uploadSrcImage(pirUrl,srcImgRealpath); //上传原图
					
				}catch(IOException e)
				{
					e.printStackTrace();
					System.out.println("文件上传失败："+e.getMessage());
				}
				
				//生成数据库要保存的图片地址
				//将相对地址中的"\\"替换成"/"，以符合在网页上显示的路径
			//	srcImagePath=srcImagePath.replace("\\","/");
				
				//打印上传结果信息
				System.out.println(filename+"上传成功！");
				System.out.println("原图保存为："+srcImagePath);
				if(srcImagePath!="")
				{
					srcImagePath=srcImagePath.substring(1);
				}
				return srcImagePath;
			
		}
}

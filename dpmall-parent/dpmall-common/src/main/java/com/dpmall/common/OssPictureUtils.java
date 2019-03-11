package com.dpmall.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 测试oss 用主账号链接
 * @author Administrator
 *
 */
public class OssPictureUtils  {
	private static final Logger LOG = LoggerFactory.getLogger(OssPictureUtils.class);
	
	public static OssInfoUtils ossInfoUtils = new OssInfoUtils();// 获取对应信息
	private static String endpoint =ossInfoUtils.getEndpoint();
	//空间
	private static String bucketName =OssInfoUtils.getBucketName();
    //oss文件存储目录
//    private String filedir = OssInfoUtils.getFiledirName("dpmallFiledir");//助销宝

	 /**
	   * 上传到OSS服务器  如果同名文件会覆盖服务器上的（通过流上传）
	   *
	   * @param instream 文件流
	   * @param fileName 文件名称 包括后缀名
	   * @return 出错返回"" ,唯一MD5数字签名
	   */
	  public int uploadFile2OSS(OSSClient ossClient, InputStream instream, String fileName) throws Exception{
	    String ret = ""; 
	    int i = 0;
	    try {
	      //创建上传Object的Metadata 
	      ObjectMetadata objectMetadata = new ObjectMetadata();
	      objectMetadata.setContentLength(instream.available());
	      objectMetadata.setCacheControl("no-cache");
	      objectMetadata.setHeader("Pragma", "no-cache");
	      objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
	      objectMetadata.setContentDisposition("inline;filename=" + fileName);
	      objectMetadata.setContentEncoding("utf-8");  //指定该Object被下载时的内容编码格式   
	      //上传文件
	      PutObjectResult putResult = ossClient.putObject(bucketName, fileName, instream, objectMetadata);
	      ret = putResult.getETag();
	      i = 1;//代表成功
	    } catch (Exception e) {
	      LOG.error(e.getMessage(), e);
	    } 
	    finally {
	      try {
	        if (instream != null) {
	          instream.close();
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	    return i;
	  }
	 
	 /**
	  * 删除文件
	  */
	  public int deleteObject2Oss(OSSClient ossClient,String fileName) {
		  int i = 0;
		  try {
			  ossClient.deleteObject(bucketName, fileName);
			  i = 1;//成功删除
		} catch (Exception e) {
			i=0;
			LOG.error(e.getMessage(), e);
		}
		  
		  return i;
	  }
	
	
	/**
	 * 上传图片至Oss服务器(通过文件上传)
	 * @return
	 */
	public String uploadPictureByFile(OSSClient ossClient, File file,String folder)throws Exception  {
		 String resultStr = null;  
	        try {  
	            //以输入流的形式上传文件  
	            InputStream is = new FileInputStream(file);  
	            //文件名  
	            String fileName = file.getName(); 
	            //文件大小  
	            Long fileSize = file.length();   
	            //创建上传Object的Metadata    
	            ObjectMetadata metadata = new ObjectMetadata();  
	            //上传的文件的长度  
	            metadata.setContentLength(is.available());    
	            //指定该Object被下载时的网页的缓存行为  
	            metadata.setCacheControl("no-cache");   
	            //指定该Object下设置Header  
	            metadata.setHeader("Pragma", "no-cache");    
	            //指定该Object被下载时的内容编码格式  
	            metadata.setContentEncoding("utf-8");    
	            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，  
	            //如果没有扩展名则填默认值application/octet-stream  
	            metadata.setContentType(getContentType(fileName));    
	            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）  
	            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");    
	            //上传文件   (上传文件流的形式)  
	            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);    
	            //解析结果  
	            resultStr = putResult.getETag();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);    
	        }  
	        return resultStr; 
		
	}
	
	/** 
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType 
     * @param fileName 文件名 
     * @return 文件的contentType 
     */  
    public static  String getContentType(String fileName){  
        //文件的后缀名  
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));  
        if(".bmp".equalsIgnoreCase(fileExtension)) {  
            return "image/bmp";  
        }  
        if(".gif".equalsIgnoreCase(fileExtension)) {  
            return "image/gif";  
        }  
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {  
            return "image/jpeg";  
        }  
        if(".html".equalsIgnoreCase(fileExtension)) {  
            return "text/html";  
        }  
        if(".txt".equalsIgnoreCase(fileExtension)) {  
            return "text/plain";  
        }  
        if(".vsd".equalsIgnoreCase(fileExtension)) {  
            return "application/vnd.visio";  
        }  
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {  
            return "application/vnd.ms-powerpoint";  
        }  
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {  
            return "application/msword";  
        }  
        if(".xml".equalsIgnoreCase(fileExtension)) {  
            return "text/xml";  
        }  
        //默认返回类型  
        return "image/jpeg";  
    }  
	
   /**
    * 获取oss对象
    * @param readOrWrite :read：上传图片、write：下载图片
    * @return
    */
    public static  OSSClient  getOssClient(String readOrWrite) {
    	//通过sts临时授权访问oss
		OssStsUtils OssStsUtil = new OssStsUtils();
		Map<String, String> staMap = OssStsUtil.getOssTemporary(readOrWrite);
		OSSClient client = new OSSClient(endpoint, staMap.get("STSAccessKeyId"), staMap.get("STSAccessKeySecret"), staMap.get("STSSecurityToken"));
		return client;
    } 
    
//    /** 
//     * 创建模拟文件夹 
//     * @param ossClient oss连接 
//     * @param bucketName 存储空间 
//     * @param folder   模拟文件夹名如"qj_nanjing/" 
//     * @return  文件夹名 
//     */  
//    private   String createFolder(OSSClient ossClient,String bucketName,String folder){  
//        //文件夹名字
//    	String folderResult = folder;
//    	//判断文件夹是否存在，不存在则创建  
//        if(!ossClient.doesObjectExist(bucketName, folder)){  
//            //创建文件夹  
//            ossClient.putObject(bucketName, folder, new ByteArrayInputStream(new byte[0]));  
//            LOG.info("创建文件夹成功");  
//            //得到文件夹名  
//            OSSObject object = ossClient.getObject(bucketName, folder);  
//            String fileDir=object.getKey();  
//            return fileDir;  
//        }  
//        return folderResult;  
//    }    

    /**
     * 测试
     */
	public static void main(String[] args) throws Exception {
		// 创建OSSClient实例
//		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		 OSSClient ossClient=OssPictureUtils.getOssClient("write"); 
		 OssPictureUtils ossUtils = new OssPictureUtils();
		
		try {
			// 使用访问OSS
			//创建bucket （oss中的存储空间）
//			client.createBucket(bucketName);
				
//			//创建文件夹
//			String folderName = ossUtils.createFolder(ossClient, bucketName, "testFolder");
//			System.out.println("\nfolderName:"+folderName);
			
//			//上传图片
//			String fileStr= "C:\\Users\\Administrator\\Desktop\\杂\\图片2.png";
//			File file = new File(fileStr);

////			String key = "助销宝\\";//key ，即文件名
//			String resultStr = ossUtils.uploadPictureByFile(ossClient, file,  "助销宝Test/");
//			System.out.println("\n"+resultStr+"\n");
			
//			//获取图片路径
//			//有效时间
//			Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 );  
//			URL url = ossClient.generatePresignedUrl(bucketName, "助销宝/ttttt1.png", expiration);
//			if (url !=null) {
//				System.out.println("\nurl:\n"+url.toString());
//			}
			int i = ossUtils.deleteObject2Oss(ossClient, "dpmall/big1.png");
			System.out.println(i);
			
			
			
		} catch (OSSException  oe) {
			 System.out.println("Caught an OSSException, which means your request made it to OSS, "
	                    + "but was rejected with an error response for some reason.");
	            System.out.println("Error Message: " + oe.getErrorCode());
	            System.out.println("Error Code:       " + oe.getErrorCode());
	            System.out.println("Request ID:      " + oe.getRequestId());
	            System.out.println("Host ID:           " + oe.getHostId());
		}finally {
			// 关闭client
			ossClient.shutdown();
		}
	}
	
    
	
}

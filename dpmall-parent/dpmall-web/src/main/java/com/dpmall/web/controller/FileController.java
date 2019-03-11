package com.dpmall.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.WebUtils;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/file")
public class FileController {
	
	private final com.alibaba.dubbo.common.logger.Logger LOG = LoggerFactory.getLogger(FileController.class);
	
	private final static String inagePath=WebUtils.getWebImagesUrl();
	
	
	/**
	 * 凭证上传
	 * @param consignmentId 订单ID
	 * @param files 文件
	 * @return 文件相对路径
	 */
	@RequestMapping("/imageUpload")
	@ResponseBody
	public Response imageUpload(@RequestParam String consignmentId,@RequestParam(required=false) CommonsMultipartFile[] files) {
		LOG.info("begin to upload images consignmentCode:"+consignmentId);
		String path =inagePath+consignmentId+"/";
		StringBuffer resultPath=new StringBuffer();
		Response res=new Response();
		if (files==null||files.length<1) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="没有上传文件";
		}
		else {
			Random random=new Random();
			for(CommonsMultipartFile file:files) {
				String name = file.getOriginalFilename();
				String type = name.substring(name.lastIndexOf("."),name.length());
				String fileName=System.currentTimeMillis()+random.nextInt(10000)+type;
				String filePath=(path+fileName).replace(",", "");
				File localFile = new File(filePath);
				if (!localFile.exists()) {
					localFile.mkdirs();
				}
				try {
					file.transferTo(localFile);
				} catch (IllegalStateException e) {
					res.resultCode=ErrorCode.INTERNAL_ERR;
					res.message="上传文件失败";					
					LOG.error(e.getMessage());
					return res;
				} catch (IOException e) {
					res.resultCode=ErrorCode.INTERNAL_ERR;
					res.message="上传文件失败";
					LOG.error(e.getMessage());
					return res;
				}
				resultPath.append(consignmentId+"/"+fileName+",");
			}
			res.resultCode=ErrorCode.SUCCESS;
			res.data=resultPath;
		}
		LOG.info("end upload images response"+JSON.toJSONString(res));
		return res;
		
	}
}

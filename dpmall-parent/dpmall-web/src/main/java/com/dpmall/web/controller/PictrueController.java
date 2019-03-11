package com.dpmall.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.common.OssPictureUtils;
import com.dpmall.datasvr.api.IPictureService;
import com.dpmall.enums.EPictureType;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.PictureForm;
import com.dpmall.web.controller.form.Response;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/picture")
public class PictrueController {

	private final Logger LOG = LoggerFactory.getLogger(PictrueController.class);

	// private final static String inagePath=WebUtils.getWebImagesUrl();
	private static final String bucketName = OssInfoUtils.getBucketName();

	@Autowired
	private IPictureService pictureService;

	/**
	 * 图片上传
	 * 
	 * @param files
	 *            文件
	 */
	@RequestMapping("/ossUpload")
	@ResponseBody
	public Response ossUpload( PictureForm form , @RequestParam(required = false) MultipartFile[] files) throws IOException {
		LOG.info("开始上传图片");
//		StringBuffer resultPath = new StringBuffer();
		List<String> resultPath = new ArrayList<>();
		Response res = new Response();
		if (files == null || files.length < 1) {
			res.resultCode = ErrorCode.INVALID_PARAM;
			res.message = "没有上传文件";
		} else {
			if (StringUtils.isEmpty(form.getFiledirName()) || StringUtils.isEmpty(form.getPictureType()))  {
				res.resultCode = ErrorCode.INVALID_PARAM;
				res.message = "参数错误";
				return res;
			}
			OSSClient ossClient = OssPictureUtils.getOssClient("write");// 创建ossClient用于上传文件
			try {
				for (MultipartFile file : files) {
					// 重命名图片名称
					Random random = new Random();
					String name = file.getOriginalFilename();
					String type = name.substring(name.lastIndexOf("."), name.length());
					String fileName = System.currentTimeMillis() + random.nextInt(10000) + type;
					fileName = form.getFiledirName()+((fileName).replace(",", ""));
					OssPictureUtils ossUtils = new OssPictureUtils();
					try {
						int ossResult = ossUtils.uploadFile2OSS(ossClient, file.getInputStream(), fileName);// 上传文件
						int insertResult = 0;
						if (ossResult == 1) {//图片成功上传oss后，将图片名字存到数据库
							 insertResult = pictureService.uploadPicture(fileName, this.selectPictureTitle(form.getPictureType()), form.getContent(), form.getPictureType());
						}
						if (insertResult !=0) {//两者成功才返回成功信息
							resultPath.add(fileName);
						}
						
					} catch (Exception e) {
						res.resultCode = ErrorCode.INTERNAL_ERR;
						res.message = "上传文件失败";
						ossUtils.deleteObject2Oss(ossClient, fileName);//出错时，删除刚刚上传的oss文件
						LOG.error(e.getMessage(), e);
						return res;
					}
				}
				res.resultCode = ErrorCode.SUCCESS;
				res.data = resultPath;
			} catch (Exception e) {
				res.resultCode = ErrorCode.INTERNAL_ERR;
				res.message = "上传文件失败";
				LOG.error(e.getMessage(), e);
				return res;
			}finally {
				if (ossClient != null) {//关闭oss
					ossClient.shutdown();
				}
			}
		}
		LOG.info("上传结束" + JSON.toJSONString(res));
		return res;

	}
	
	
	
	

	/**
	 * 获取祝福卡的列表图片
	 */
	@RequestMapping(value = "/getPictures", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getPictures(@RequestBody PictureForm form) {
		LOG.info("{method:'PictrueController::getPictures',in:" + JSON.toJSONString(form) + "}");
		Response response = new Response();
		if (form.getPageSize() == null || form.getStartNum() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = pictureService.getPictures(form.getStartNum(), form.getPageSize(),EPictureType.CARDS_CONTENT.getType());
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
			}
		}
		LOG.info("{method:'PictrueController::getPictures',out:{res:'" + JSON.toJSONString(response) + "'}}");
		return response;
	}
	

	/**
	 * 查询祝福卡的导航栏图片
	 */
	@RequestMapping(value = "/getHomePictures", method = { RequestMethod.GET,RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getHomePictures(@RequestBody PictureForm form) {
		LOG.info("{method:'PictrueController::getPictures',in:" + JSON.toJSONString(form) + "}");
		Response response = new Response();
		if (form.getPageSize() == null || form.getStartNum() == null) {
			response.resultCode = ErrorCode.INVALID_PARAM;
			response.message = "参数错误";
		} else {
			try {
				response.data = pictureService.getPictures(form.getStartNum(), form.getPageSize(),EPictureType.CARDS_GUIDE.getType());
				response.resultCode = ErrorCode.SUCCESS;
			} catch (Exception e) {
				response.resultCode = ErrorCode.INTERNAL_ERR;
				response.message = "未知错误";
			}
		}
		LOG.info("{method:'PictrueController::getPictures',out:{res:'" + JSON.toJSONString(response) + "'}}");
		return response;

	}

	/**
	 * 跳转图片管理页面
	 * @return
	 */
	@RequestMapping(value = "/toPictureAdmin",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	public ModelAndView toPictureUtils(HttpServletRequest request){
		return new ModelAndView("picture/hello");
	}
	/**
	 * 根据图片类型选择图片标题
	 */
	private String selectPictureTitle(String pictureType) {
		String pictureTitle = "";
		if (EPictureType.HOME_PAGE.getType().equals(pictureType)) {
			pictureTitle = EPictureType.HOME_PAGE.getTitle();
		}
		else if (EPictureType.HOME_PAGE_CARDS_GUIDE.getType().equals(pictureType)) {
			pictureTitle = EPictureType.HOME_PAGE_CARDS_GUIDE.getTitle();
		}
		else if (EPictureType.HOME_PAGE_VIDEO.getType().equals(pictureType)) {
			pictureTitle = EPictureType.HOME_PAGE_VIDEO.getTitle();
		}
		else if (EPictureType.HOME_CARDS_GUIDE.getType().equals(pictureType)) {
			pictureTitle = EPictureType.HOME_CARDS_GUIDE.getTitle();
		}
		else if (EPictureType.CARDS_GUIDE.getType().equals(pictureType)) {
			pictureTitle = EPictureType.CARDS_GUIDE.getTitle();
		}
		else if (EPictureType.CARDS_CONTENT.getType().equals(pictureType)) {
			pictureTitle = EPictureType.CARDS_CONTENT.getTitle();
		}
		return pictureTitle;
	}
}

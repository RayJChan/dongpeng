package com.dpmall.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.PictureForm;
import com.dpmall.web.controller.form.Response;

@Controller
@RequestMapping("/video")
public class VideoController {

	private final Logger LOG = LoggerFactory.getLogger(VideoController.class);

	// oss路径
	private static final String OSSURL = OssInfoUtils.getOssUrl() + "video/";
	private static final String IOS_VIDEO = "test_Video.mp4";
	private static final String ANDROID_VIDEO = "test_Video1.mp4";
	
	
	/**
	 * 获取首页的视频
	 */
	@RequestMapping(value = "/getHomePageVideo", method = { RequestMethod.GET,RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public Response getHomePageVideo(@RequestBody PictureForm form) {
		LOG.info("{method:'VideoController::getHomePageVideo',in:" + JSON.toJSONString(form) + "}");
		Response response = new Response();
		Map<String, String> videos = new HashMap<>();
		try {
			videos.put("iosVideo", OSSURL + IOS_VIDEO);
			videos.put("androidVideo", OSSURL + ANDROID_VIDEO);
			response.data = videos;
			response.resultCode = ErrorCode.SUCCESS;
		} catch (Exception e) {
			response.resultCode = ErrorCode.INTERNAL_ERR;
			response.message = "未知错误";
		}

		LOG.info("{method:'VideoController::getHomePageVideo',out:{res:'" + JSON.toJSONString(response) + "'}}");
		return response;
	}

}

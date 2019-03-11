package com.dpmall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取图片
 * @author cwj
 * @since 2017-12-26
 */
public class PictureModel implements Serializable {

	private static final long serialVersionUID = 6985378477329940114L;

	/**图片id**/
	private Long id ;
	
	/**图片路径**/
	private String imgUrl;
	
	/**图片标题**/
	private String title;
	
	/**图片内容**/
	private String content;

	
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}

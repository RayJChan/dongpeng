package com.dpmall.web.controller.form;


public class PictureForm extends RequestForm {
	
	/**开始页码**/
	private Integer startNum;
	
	private Integer pageSize;
	
	/**图片类型**/
	private String pictureType;
	
	/**图片内容**/
	private String content;
	
	/**图片标题**/
	private String title;
	
	/**文件夹名字**/
	private String filedirName;
	

	
	
	public String getFiledirName() {
		return filedirName;
	}

	public void setFiledirName(String filedirName) {
		this.filedirName = filedirName;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getPictureType() {
		return pictureType;
	}

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	
}

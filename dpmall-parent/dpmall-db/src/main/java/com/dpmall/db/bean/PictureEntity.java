package com.dpmall.db.bean;


/**
 * 登陆者权限信息
 */
public class PictureEntity  {
	

	/**图片id**/
	private Long id ;
	
	/**图片标题**/
	private String title;
	
	/**图片内容**/
	private String content;
	
	/**图片名称**/
	private String name;
	
	/**图片类型**/
	private String pictureType;
	
	

	
	

	public String getPictureType() {
		return pictureType;
	}

	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
    
    
}

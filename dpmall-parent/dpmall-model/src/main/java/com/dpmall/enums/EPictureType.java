package com.dpmall.enums;

/**
 * 图片类型
 * @author cwj
 * @date 2018-1-17
 */
public enum EPictureType {
	/**首页导航图片*/
	HOME_PAGE("A-1","首页横幅"),
	
	/**首页中的祝福卡*/
	HOME_PAGE_CARDS_GUIDE("A-2","首页祝福卡横幅"),
	
	/**首页中的祝福卡*/
	HOME_PAGE_VIDEO("A-3","视频"),
	
	/**首页问候卡入口*/
	HOME_CARDS_GUIDE("B","首页问候卡入口"),
	
	/**祝福卡模块导航卡*/
	CARDS_GUIDE("C","祝福卡横幅"),
	
	/**祝福卡列表*/
	CARDS_CONTENT("D","祝福卡内容");
	
    private String type;
	
	private String title;
	
	private EPictureType(String type, String title) {
		this.type = type;
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
	
}



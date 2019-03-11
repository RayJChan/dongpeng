package com.dpmall.enums;

/**
 * <p>
 * 装修风格
 * @author river
 * @date 2017-07-20
 */
public enum EDecStyle {
	RETRO_CH("retro","复古中式"),
	FRENCH_STYLE("french","法式风格"),
	BAROCO_STYLE("baroco","巴洛克"),
	RUSTIC_STYLE("rustic","乡村风格"),
	EUROPEAN_STYLE("european","欧式风格"),
	MIXMATCH_STYLE("mixmatch","时尚混搭"),
	CLASSIC_STYLE("classic","古典风格"),
    SIMPLE_STYLE("simple","简约风格"),
    ELEGANT_STYLE("elegant","精致风格"),
    NATURE_STYLE("nature","自然风格"),
    BRISK_STYLE("brisk","轻快风格"),
    SOFT_STYLE("soft","柔和风格"),
	URBAN_STYLE("urban","都市风格"),
	FRESH_STYLE("fresh","清新风格"),
	MODERN_STYLE("modern","现代风格"),
	VILLAGE_STYLE("village","田园风格"), 
	POSTMODERN_STYLE("postmodern","后现代风格"),
	CHINESE_STYLE("chinese","中式风格"),
	EUR_CLASSIC_STYLE("eur_classic","欧式古典"),
	MEDITERRANEAN("mediterranean","地中海风格"),
	SOUTH_EAST_ASIAN("south_east_asian","东南亚风格"),
	JAPANESE_STYLE("japanese_style","日式风格");
	
    private String code;
	
	private String name;

	private EDecStyle(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	
}

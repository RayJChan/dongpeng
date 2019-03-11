package com.dpmall.enums;

public enum Category {
		/**瓷砖**/
		TILET(1,"瓷砖"),
	
		/**卫浴洁具**/
		UTENSILS(2,"卫浴洁具"),
		
		/**厨卫五金**/
		HARDWARE(3,"厨卫五金"),
		
		/**木地板**/
		WOOD_FLOOR(4,"木地板"),
		
		/**背景墙**/
		BACKGROUND_WALL(5,"背景墙"),
		
		/**基础建材**/
		BASIC_MATERIALS(6,"基础建材"),
		
		/**家居饰品**/
		HOME_ACCESSORIES(7,"家居饰品"),
		
		/**设计**/
		DESIGN(8,"设计"),
		
		/**装修**/
		RENOVATION(9,"装修"),
		
		/**监理**/
		SUPERVISORS(10,"监理"),
		
		/**安装**/
		INSTALL(11,"安装"),
		
		/**家居套餐**/
		HOME_SUI(12,"家居套装"),
		
		/**增值服务**/
		VALUE_ADDED_SERVICES(13,"增值服务");
		private Integer value;
		
		private String code;

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		private Category(Integer value, String code) {
			this.value = value;
			this.code = code;
		}
}

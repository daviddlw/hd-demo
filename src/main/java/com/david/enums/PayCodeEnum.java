package com.david.enums;

/**
 * 宝付银行编码
 * 
 * @author dailiwei
 *
 */
public enum PayCodeEnum {

	ICBC("ICBC", "上海工商银行"),

	ABC("ICBC", "中国农业银行"),

	CCB("CCB", "中国建设银行"),

	BOC("BOC", "中国银行"),

	BOCOM("BOCOM", "中国交通银行"),

	CIB("CIB", "兴业银行"),

	CITIC("CITIC", "中信银行"),

	CEB("CEB", "中国光大银行"),

	PAB("PAB", "平安银行"),

	SHB("SHB", "上海银行"),

	SPDB("SPDB", "浦东发展银行"),

	CMBC("CMBC", "中国民生银行"),

	CMB("CMB", "招商银行");

	private String code;

	private String desc;

	private PayCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static PayCodeEnum getPayCodeEnum(String code) {
		PayCodeEnum result = null;
		PayCodeEnum[] payCodeEnums = PayCodeEnum.values();
		for (PayCodeEnum payCodeEnum : payCodeEnums) {
			if(payCodeEnum.getCode().equalsIgnoreCase(code)){
				result = payCodeEnum;
				break;
			}
		}
		
		return result;
	}

}

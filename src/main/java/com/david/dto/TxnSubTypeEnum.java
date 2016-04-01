package com.david.dto;

/**
 * 交易子类型
 * 
 * @author dailiwei
 *
 */
public enum TxnSubTypeEnum {

	BindCardTrade("01", "实名建立绑定关系类交易"),

	UnBindCardTrade("02", "解除绑定关系类交易"),
	
	QueryBindCard("03", "查询绑定关系类交易"),
	
	PaymentTrade("04", "支付类交易"),
	
	SmsTrade("05", "发送短信类交易"),
	
	QueryPaymentStateTrade("06", "交易状态查询类交易");

	private String code;
	private String desc;

	private TxnSubTypeEnum(String code, String desc) {
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

}

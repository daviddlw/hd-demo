package com.david.dto;

/**
 * 宝付公共请求报文
 * 
 * @author dailiwei
 *
 */
public class BaoFuAuthRequest {
	private String version;
	private String terminal_id;
	private String txn_type;
	private String txn_sub_type;
	private String member_id;
	private String data_type;
	private String data_content;

	public BaoFuAuthRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaoFuAuthRequest(String version, String terminal_id, String txn_type, String txn_sub_type, String member_id,
			String data_type, String data_content) {
		super();
		this.version = version;
		this.terminal_id = terminal_id;
		this.txn_type = txn_type;
		this.txn_sub_type = txn_sub_type;
		this.member_id = member_id;
		this.data_type = data_type;
		this.data_content = data_content;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getTxn_type() {
		return txn_type;
	}

	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}

	public String getTxn_sub_type() {
		return txn_sub_type;
	}

	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getData_content() {
		return data_content;
	}

	public void setData_content(String data_content) {
		this.data_content = data_content;
	}

	@Override
	public String toString() {
		return "BaoFuAuthRequest [version=" + version + ", terminal_id=" + terminal_id + ", txn_type=" + txn_type
				+ ", txn_sub_type=" + txn_sub_type + ", member_id=" + member_id + ", data_type=" + data_type
				+ ", data_content=" + data_content + "]";
	}

}

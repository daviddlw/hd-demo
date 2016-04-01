package com.david.dto;

/**
 * 
 * @author dailiwei
 *
 */
public class BaoFuAuthPayDataContent {
	private String txn_sub_type;
	private String next_txn_sub_type;
	private String biz_type;
	private String terminal_id;
	private String member_id;
	private String trans_serial_no;
	private String trans_id;
	private String acc_no;
	private String id_card_type;
	private String id_card;
	private String id_holder;
	private String mobile;
	private String acc_pwd;
	private String txn_amt;
	private String bind_id;
	private String valid_date;
	private String valid_no;
	private String pay_code;
	private String sms_code;
	private String trade_date;
	private String additional_info;
	private String req_reserved;
	private String orig_trans_id;

	public BaoFuAuthPayDataContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaoFuAuthPayDataContent(String txn_sub_type, String next_txn_sub_type, String biz_type, String terminal_id,
			String member_id, String trans_serial_no, String trans_id, String acc_no, String id_card_type,
			String id_card, String id_holder, String mobile, String acc_pwd, String txn_amt, String bind_id,
			String valid_date, String valid_no, String pay_code, String sms_code, String trade_date,
			String additional_info, String req_reserved, String orig_trans_id) {
		super();
		this.txn_sub_type = txn_sub_type;
		this.next_txn_sub_type = next_txn_sub_type;
		this.biz_type = biz_type;
		this.terminal_id = terminal_id;
		this.member_id = member_id;
		this.trans_serial_no = trans_serial_no;
		this.trans_id = trans_id;
		this.acc_no = acc_no;
		this.id_card_type = id_card_type;
		this.id_card = id_card;
		this.id_holder = id_holder;
		this.mobile = mobile;
		this.acc_pwd = acc_pwd;
		this.txn_amt = txn_amt;
		this.bind_id = bind_id;
		this.valid_date = valid_date;
		this.valid_no = valid_no;
		this.pay_code = pay_code;
		this.sms_code = sms_code;
		this.trade_date = trade_date;
		this.additional_info = additional_info;
		this.req_reserved = req_reserved;
		this.orig_trans_id = orig_trans_id;
	}

	public String getTxn_sub_type() {
		return txn_sub_type;
	}

	public void setTxn_sub_type(String txn_sub_type) {
		this.txn_sub_type = txn_sub_type;
	}

	public String getNext_txn_sub_type() {
		return next_txn_sub_type;
	}

	public void setNext_txn_sub_type(String next_txn_sub_type) {
		this.next_txn_sub_type = next_txn_sub_type;
	}

	public String getBiz_type() {
		return biz_type;
	}

	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}

	public String getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTrans_serial_no() {
		return trans_serial_no;
	}

	public void setTrans_serial_no(String trans_serial_no) {
		this.trans_serial_no = trans_serial_no;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getId_card_type() {
		return id_card_type;
	}

	public void setId_card_type(String id_card_type) {
		this.id_card_type = id_card_type;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getId_holder() {
		return id_holder;
	}

	public void setId_holder(String id_holder) {
		this.id_holder = id_holder;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAcc_pwd() {
		return acc_pwd;
	}

	public void setAcc_pwd(String acc_pwd) {
		this.acc_pwd = acc_pwd;
	}

	public String getTxn_amt() {
		return txn_amt;
	}

	public void setTxn_amt(String txn_amt) {
		this.txn_amt = txn_amt;
	}

	public String getBind_id() {
		return bind_id;
	}

	public void setBind_id(String bind_id) {
		this.bind_id = bind_id;
	}

	public String getValid_date() {
		return valid_date;
	}

	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}

	public String getValid_no() {
		return valid_no;
	}

	public void setValid_no(String valid_no) {
		this.valid_no = valid_no;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public String getSms_code() {
		return sms_code;
	}

	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}

	public String getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}

	public String getReq_reserved() {
		return req_reserved;
	}

	public void setReq_reserved(String req_reserved) {
		this.req_reserved = req_reserved;
	}

	public String getOrig_trans_id() {
		return orig_trans_id;
	}

	public void setOrig_trans_id(String orig_trans_id) {
		this.orig_trans_id = orig_trans_id;
	}

	@Override
	public String toString() {
		return "BaoFuAuthPayDataContent [txn_sub_type=" + txn_sub_type + ", next_txn_sub_type=" + next_txn_sub_type
				+ ", biz_type=" + biz_type + ", terminal_id=" + terminal_id + ", member_id=" + member_id
				+ ", trans_seral_no=" + trans_serial_no + ", trans_id=" + trans_id + ", acc_no=" + acc_no
				+ ", id_card_type=" + id_card_type + ", id_card=" + id_card + ", id_holder=" + id_holder + ", mobile="
				+ mobile + ", acc_pwd=" + acc_pwd + ", txn_amt=" + txn_amt + ", bind_id=" + bind_id + ", valid_date="
				+ valid_date + ", valid_no=" + valid_no + ", pay_code=" + pay_code + ", sms_code=" + sms_code
				+ ", trade_date=" + trade_date + ", additional_info=" + additional_info + ", req_reserved="
				+ req_reserved + ", orig_trans_id=" + orig_trans_id + "]";
	}

}

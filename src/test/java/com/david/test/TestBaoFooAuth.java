package com.david.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.david.demo.HttpUtils;
import com.david.dto.BaoFuAuthPayDataContent;
import com.david.dto.BaoFuAuthRequest;
import com.david.dto.TxnSubTypeEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 测试宝付认证
 * 
 * @author dailiwei
 *
 */
public class TestBaoFooAuth {

	private static final String BFKEY_100000749_100000933_CER = "bfkey_100000749@@100000933.cer";
	private static final String BFKEY_100000749_100000933_PFX = "bfkey_100000749@@100000933.pfx";
	private static final String UTF_8 = "UTF-8";
	private static final String REQUEST_URL = "https://tgw.baofoo.com/cutpayment/api/backTransRequest";
	private static final String TXN_TYPE = "0431";
	private static final String VERSION = "4.0.0.0";
	private static final String TEST_MEMBER_ID = "100000749";
	private static final String TEST_TERMINAL_ID = "100000933";
	private static final String TEST_BIZ_TYPE = "0000";
	private static final String DATA_TYPE = "json";
	private static final String MOBILE = "13661896734";
	private static final Gson gson = new Gson();

	/**
	 * 测试绑卡
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testBindCard() throws UnsupportedEncodingException {
		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.BindCardTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.BindCardTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david绑卡交易通知");
		dataContent.setReq_reserved("david绑卡交易通知");

		String acc_no = "6222020111122220000";
		System.out.println("acc_no: " + acc_no);
		String transId = TestHelper.getNewUUID();
		System.out.println("transId: " + transId);

		dataContent.setAcc_no(acc_no);
		dataContent.setTrans_id(transId);
		dataContent.setId_card_type("01");
		dataContent.setId_card("310112198801128181");
		dataContent.setId_holder("戴维测试");
		dataContent.setMobile(MOBILE);
		dataContent.setAcc_pwd("123456");
		dataContent.setValid_date("03/20");
		dataContent.setValid_no("888");
		dataContent.setPay_code("ICBC");
		dataContent.setSms_code("123456");

		doWork(dataContent, request);

	}

	@Test
	public void testUnBindCard() throws UnsupportedEncodingException {
		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.UnBindCardTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.UnBindCardTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david解除绑卡交易通知");
		dataContent.setReq_reserved("david解除绑卡交易通知");
		dataContent.setBind_id("201603261412121000009649074");

		doWork(dataContent, request);
	}

	@Test
	public void testQueryBindCard() throws UnsupportedEncodingException {
		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.QueryBindCard.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david查询绑卡信息交易通知");
		dataContent.setReq_reserved("david查询绑卡信息交易通知");
		dataContent.setAcc_no("6222020111122220000");

		doWork(dataContent, request);
	}

	@Test
	public void testPayment() throws UnsupportedEncodingException {
		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.PaymentTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.PaymentTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david支付类交易通知");
		dataContent.setReq_reserved("david支付类交易通知");
		dataContent.setTrans_id(TestHelper.getNewUUID());
		dataContent.setBind_id("201603261412121000009649074");
		dataContent.setTxn_amt("1");
		dataContent.setSms_code("123456");

		doWork(dataContent, request);
	}

	private BaoFuAuthRequest getAuthRequest(TxnSubTypeEnum type) {
		BaoFuAuthRequest request = new BaoFuAuthRequest();
		request.setVersion(VERSION);
		request.setMember_id(TEST_MEMBER_ID);
		request.setTerminal_id(TEST_TERMINAL_ID);
		request.setTxn_type(TXN_TYPE);
		request.setTxn_sub_type(type.getCode());
		request.setData_type(DATA_TYPE);

		return request;
	}

	/**
	 * 测试发送短信交易
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testSendSms() throws UnsupportedEncodingException {

		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.SmsTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.SmsTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david短信交易通知");
		dataContent.setReq_reserved("david保留短信交易通知");

		String transId = TestHelper.getNewUUID();
		System.out.println("transId: " + transId);
		dataContent.setTrans_id(transId);
		dataContent.setMobile(MOBILE);
		String accNo = TestHelper.getICBCDebitCardNo();
		System.out.println(accNo);
		dataContent.setAcc_no(accNo);
		dataContent.setNext_txn_sub_type(TxnSubTypeEnum.BindCardTrade.getCode());

		doWork(dataContent, request);

	}

	private void doWork(BaoFuAuthPayDataContent dataContent, BaoFuAuthRequest request)
			throws UnsupportedEncodingException {
		String keyStorePath = "Q:" + File.separator + "baofu_cert_auth" + File.separator
				+ BFKEY_100000749_100000933_PFX;
		String keyStorePassword = "100000749_272769";
		String pub_key = "Q:" + File.separator + "baofu_cert_auth" + File.separator + BFKEY_100000749_100000933_CER;

		String tempReqdata = gson.toJson(dataContent);
		String origData = new String(Base64.encodeBase64(tempReqdata.getBytes(Charset.forName(UTF_8))));
		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, keyStorePath, keyStorePassword);

		System.out.println("----------->私钥加密结果：" + encryptData);

		request.setData_content(encryptData);
		String requestBody = gson.toJson(request);
		Map<String, String> requestParams = stringToMap(requestBody);

		String encryptPostResult = HttpUtils.httpPostNoBody(REQUEST_URL, requestParams);

		System.out.println(encryptPostResult);
		String postResult = RsaCodingUtil.decryptByPubCerFile(encryptPostResult, pub_key);

		String unBase64Data = new String(Base64.decodeBase64(postResult.getBytes(UTF_8)));
		System.out.println(unBase64Data);

		Map<String, String> resultMap = stringToMap(unBase64Data);

		System.out.println("======================================");
		System.out.println(resultMap);
	}

	@Test
	public void testQueryPaymentState() throws UnsupportedEncodingException {
		BaoFuAuthRequest request = getAuthRequest(TxnSubTypeEnum.PaymentTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFuAuthPayDataContent dataContent = new BaoFuAuthPayDataContent();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.PaymentTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david支付类交易通知");
		dataContent.setReq_reserved("david支付类交易通知");
		dataContent.setBind_id("201603261412121000009649074");
		dataContent.setTrans_id(TestHelper.getNewUUID());
		dataContent.setOrig_trans_id(TestHelper.getNewUUID());
		dataContent.setSms_code("123456");

		doWork(dataContent, request);
	}

	/**
	 * jsonstring transform to map
	 * 
	 * @param jsonStr
	 *            jsonstr
	 * @return 返回指定map
	 */
	private Map<String, String> stringToMap(String jsonStr) {
		Map<String, String> resultMap = gson.fromJson(jsonStr, new TypeToken<Map<String, String>>() {
		}.getType());
		return resultMap;
	}

}

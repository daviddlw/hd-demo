package com.david.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.david.demo.HttpUtils;
import com.david.dto.BaoFooCommonReqDTO;
import com.david.dto.BaoFooPaymentDTO;
import com.david.dto.FastUser;
import com.david.dto.User;
import com.david.enums.PayCodeEnum;
import com.david.enums.TxnSubTypeEnum;
import com.david.utils.RsaCodingUtil;
import com.david.utils.RsaReadUtil;

/**
 * 测试宝付认证（认证绑卡+支付）
 * 
 * @author dailiwei
 *
 */
public class TestBaoFooPayment {

	private static final String BFKEY_100000276_100000994_CER = "bfkey_100000276@@100000994.cer";
	private static final String BFKEY_100000276_100000994_PFX = "bfkey_100000276@@100000994.pfx";
	private static final String BFKEY_100000749_100000933_CER = "bfkey_100000749@@100000933.cer";
	private static final String BFKEY_100000749_100000933_PFX = "bfkey_100000749@@100000933.pfx";
	private static final String BFKEY_REAL_PFX = "baofoo_pri.pfx";
	private static final String BFKEY_REAL_CER = "bfkey_795052@@28821.cer";
	private static final String UTF_8 = "UTF-8";
	private static final String REQUEST_URL = "https://tgw.baofoo.com/cutpayment/api/backTransRequest";
	private static final String REQUEST_URL_REAL= "https://public.baofoo.com/cutpayment/api/backTransRequest";
	private static final String TXN_TYPE = "0431";
	private static final String VERSION = "4.0.0.0";
	private static final String TEST_MEMBER_ID = "795052"; // 商户号 100000749-100000276
	private static final String TEST_TERMINAL_ID = "28821"; // 终端号 100000933-100000994
	private static final String TEST_BIZ_TYPE = "0000";
	private static final String DATA_TYPE = "json";
	private static final String MOBILE = "13661896734";
	private static final String KEY_STORE_PASSWORD = "Hengda888";

	/**
	 * 测试绑卡交易
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testBindCard() throws UnsupportedEncodingException {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.BindCardTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.BindCardTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);

		String transNo = "TISN" + System.currentTimeMillis();
		dataContent.setTrans_serial_no(transNo);
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
		dataContent.setId_card("320301198502169000");
		dataContent.setId_holder("张宝");
		dataContent.setMobile(MOBILE);
		dataContent.setAcc_pwd("123456");
		dataContent.setValid_date("03/20");
		dataContent.setValid_no("888");
		dataContent.setPay_code(PayCodeEnum.ICBC.getCode());
//		dataContent.setSms_code("123456");

		doWork(dataContent, request);

	}

	/**
	 * 测试解绑卡交易
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testUnBindCard() throws UnsupportedEncodingException {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.UnBindCardTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.UnBindCardTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		String tranSerialNo = "TISN" + System.currentTimeMillis();
		dataContent.setTrans_serial_no(tranSerialNo);
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david解除绑卡交易通知");
		dataContent.setReq_reserved("david解除绑卡交易通知");
		dataContent.setBind_id("201603261412121000009649074");

		doWork(dataContent, request);
	}

	/**
	 * 测试查询绑卡关系交易
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryBindCard() throws UnsupportedEncodingException {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.QueryBindCard.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david查询绑卡信息交易通知");
		dataContent.setReq_reserved("david查询绑卡信息交易通知");
		dataContent.setAcc_no("6230580000077369564");

		doWork(dataContent, request);
	}

	/**
	 * 测试支付类交易接口
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testPayment() throws UnsupportedEncodingException {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.PaymentTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.PaymentTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david支付类交易通知");
		dataContent.setReq_reserved("david支付类交易通知");
		String transId = TestHelper.getNewUUID();
		System.out.println("transId: " + transId);
		dataContent.setTrans_id(transId);
		dataContent.setBind_id("201603261412121000009649074");
		dataContent.setTxn_amt("1");
		dataContent.setSms_code("123456");

		doWork(dataContent, request);
	}

	private BaoFooCommonReqDTO getAuthRequest(TxnSubTypeEnum type) {
		BaoFooCommonReqDTO request = new BaoFooCommonReqDTO();
		request.setVersion(VERSION);
		request.setMember_id(TEST_MEMBER_ID);
		request.setTerminal_id(TEST_TERMINAL_ID);
		request.setTxn_type(TXN_TYPE);
		request.setTxn_sub_type(type.getCode());
		request.setData_type(DATA_TYPE);

		return request;
	}

	/**
	 * 测试发送短信类交易
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testSendSms() throws UnsupportedEncodingException {

		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.SmsTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
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
	
	/**
	 * 查询支付交易状态
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testQueryPaymentState() throws UnsupportedEncodingException {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryPaymentStateTrade);

		String tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		BaoFooPaymentDTO dataContent = new BaoFooPaymentDTO();
		dataContent.setTxn_sub_type(TxnSubTypeEnum.QueryPaymentStateTrade.getCode());
		dataContent.setBiz_type(TEST_BIZ_TYPE);
		dataContent.setTerminal_id(TEST_TERMINAL_ID);
		dataContent.setMember_id(TEST_MEMBER_ID);
		dataContent.setTrans_serial_no("TISN" + System.currentTimeMillis());
		dataContent.setTrade_date(tradeDate);
		dataContent.setAdditional_info("david支付类交易通知");
		dataContent.setReq_reserved("david支付类交易通知");
		dataContent.setBind_id("20160415163218288218861");
		dataContent.setOrig_trans_id("QAP00000000000000124");

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
		Map<String, String> resultMap = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {
		});

		return resultMap;
	}

	@Test
	public void testPubAndPrivateKey() {
//		System.out.println(getPrivateKey());
//		System.out.println(getPublicKey());
		System.out.println(getMerchantInfo(new HashMap<String, String>()));
	}

	/**
	 * 获取参数化map
	 * 
	 * @param paramMap
	 * @return
	 */
	private String getMerchantInfo(Map<String, String> paramMap) {
		paramMap.put("baofooPrivateKey", getPrivateKey());
		paramMap.put("baofooPublicKey", getPublicKey());
		paramMap.put("baofooTerminalKey", "28821");
		return JSON.toJSONString(paramMap, false);
	}

	/**
	 * 获取私钥方法
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getPrivateKey() {
		String keyStorePath = "Q:" + File.separator + "baofu_cert_auth" + File.separator + BFKEY_REAL_PFX;
		String keyStorePassword = "Hengda888";

		PrivateKey privateKey = RsaReadUtil.getPrivateKeyFromFile(keyStorePath, keyStorePassword);
		return Base64.encodeBase64String(privateKey.getEncoded());
	}

	private String getPublicKey() {
		String pubPath = "Q:" + File.separator + "baofu_cert_auth" + File.separator + BFKEY_REAL_CER;
		PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(pubPath);
		return Base64.encodeBase64String(publicKey.getEncoded());
	}

	private String getPrivateKeyNew() {
		String keyStorePath = "Q:" + File.separator + "baofu_cert_new" + File.separator + BFKEY_100000276_100000994_PFX;
		String keyStorePassword = "123456";

		PrivateKey privateKey = RsaReadUtil.getPrivateKeyFromFile(keyStorePath, keyStorePassword);
		return Base64.encodeBase64String(privateKey.getEncoded());
	}

	private String getPublicKeyNew() {
		String pubPath = "Q:" + File.separator + "baofu_cert_new" + File.separator + BFKEY_100000276_100000994_CER;
		PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(pubPath);
		return Base64.encodeBase64String(publicKey.getEncoded());
	}

	/**
	 * 公钥加解密
	 * 
	 * @param dataContent
	 * @param request
	 * @throws Exception
	 */
	private void doWork(BaoFooPaymentDTO dataContent, BaoFooCommonReqDTO request) {
		try {

			String tempReqdata = JSON.toJSONString(dataContent);
			String origData = new String(Base64.encodeBase64(tempReqdata.getBytes(Charset.forName(UTF_8))));
			// String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData,
			// keyStorePath, keyStorePassword);
			// PrivateKey privateKey =
			// RsaReadUtil.getPrivateKey(getPrivateKey());
			String encryptData = RsaCodingUtil.encryptByPrivateKey(origData, getPrivateKey());

			System.out.println("----------->私钥加密结果：" + encryptData);

			request.setData_content(encryptData);
			String requestBody = JSON.toJSONString(request);
			Map<String, String> requestParams = stringToMap(requestBody);

			String encryptPostResult = HttpUtils.httpPostNoBody(REQUEST_URL_REAL, requestParams);

			System.out.println(encryptPostResult);
			// PublicKey publicKey = RsaReadUtil.getPublicKey(getPublicKey());
			String postResult = RsaCodingUtil.decryptByPublicKey(encryptPostResult, getPublicKey());
			// String postResult =
			// RsaCodingUtil.decryptByPubCerFile(encryptPostResult, pub_key);

			String unBase64Data = new String(Base64.decodeBase64(postResult.getBytes(UTF_8)));
			System.out.println(unBase64Data);

			Map<String, String> resultMap = stringToMap(unBase64Data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	@Test
	public void TestFastJson(){
		FastUser fastUser = new FastUser();
		fastUser.setUserId(888);
		fastUser.setUserName("戴维测试");
		String result = JSON.toJSONString(fastUser);
		System.out.println(result);
		
		FastUser newUser = JSON.parseObject(result, FastUser.class);
		System.out.println(newUser);
	}

}

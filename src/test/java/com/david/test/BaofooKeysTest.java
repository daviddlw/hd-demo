package com.david.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.david.demo.HttpUtils;
import com.david.dto.BaoFooCommonReqDTO;
import com.david.dto.BaoFooPaymentDTO;
import com.david.dto.ResponseDTO;
import com.david.enums.TxnSubTypeEnum;
import com.david.utils.RsaCodingUtil;

public class BaofooKeysTest {

	private static final String BFKEY_REAL_PFX = "baofoo_pri.pfx";
	private static final String BFKEY_REAL_CER = "bfkey_795052@@28821.cer";
	private static final String UTF_8 = "UTF-8";
	private static final String REQUEST_URL_REAL = "https://public.baofoo.com/cutpayment/api/backTransRequest";
	private static final String TXN_TYPE = "0431";
	private static final String VERSION = "4.0.0.0";
	private static final String TEST_MEMBER_ID = "795052"; // 商户号
	private static final String TEST_TERMINAL_ID = "28821"; // 终端号
	private static final String ERROR_TERMINAL_ID = "28822"; // 终端号
	private static final String DATA_TYPE = "json";
	private static final String TEST_BIZ_TYPE = "0000";

	private static final String baofooErrorFormatPrivateKey = "12345678";
	private static final String baofooErrorContentPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANHA6dosVBBy09Rq8BG6i61pU4WtQtKfRebQQaXJ6cMDcMx7+cFBVi+gSy4/5H/MsUJeXuiPL4otTEUWaconIcC7qN2Q8ummjZNiWDBtn7RSnUZlN825sbu9H2HuEwsCcIqd99JDGktHFRP3KSjjhGo5lQjAl82YCTXCDyP7BubxAgMBAAECgYEAtOAISK0lKLH8XRaCQqd8v2MuNP39u6bBHpLDqXV+cT5MTOkzrCTtb1BoxE8wecxHpxW9/glLU3pQZNs5CqvH+9z68a9NMNWcWVYQHuiNr2LOOsdrNG0c8AOOx2Cx66x1rcZo7P6y1Q2UpK5ii3Fuw1QEOUctZIcXPVMp5Mh/m+ECQQD19kOBL0o8g1EPjf5QW5LYywS0XMlvFx8CKBwwQVpsKv6zRcKNRVRnNpJDa0zdpbCZl+yfcDphrcw9diLM7w/9AkEA2lBa+k6LJzE2JTHe3ufQC4e+EGPh4+pE4p3bpUwz1Us8J55ug0CakociOe/80bQM6pRK1cG32PrTJvpu0ZwjBQJAKTNhR2iGhPBySaq9aPSN+qa3WQcZ7vPui8vwPN0VqnzhQWJ0Hnn4k3l+srBspcw1cuUSC/serHISflElrzJ0eQJAepAsU2RvGQS2yjDyV6A7G44MtXTt55+pJrTLEET0wKVpUzDOCF/np6W4IGDMRgrYSYYRcWo9RdKjYrmsJ3+J5QJAW+r+FnafFaFnuo4uJwbBZzW6bSVyXIzXW9izEJh+WmxdnTVlHL953yqB5zdv0QIncRjQqnBjrOTb62psqM1g/Q==";
	private static final String baofooErrorFormatPublicKey = "12345678";
	private static final String baofooErrorContentPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDlJZl8EVP9Y6KBgSRHCvfPHDCRgVTboCwwwlLoeYg/I1w4S7+FJ8ToRqOevcVjRBNOAvS5diEFamHU+5NnAwm2TA4BBbKNxpOj03NtWV5ryULrFDKW+yGSLGhtCkLAoW8P0GhJ38YSbQtOo0/U1lXA+nozB+mIGYqpFIgqLjmbBQIDAQAB";
	private static final String baofooPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMrH/C/W9hpD1nifR6xvj9p/7Ydxq1Zr5B8MD+nWOHPXFRKvENyBTafFN5sxBJacWQfpmYPvRvMHPWeiQygxZSXIapxHaTZUR73q7D+S+y9VZqaGp31T/eYPByvW52tAoiuBJgVber4Aq/hC4C+sN0s3pLkF2teQbTGKf1dum/WrAgMBAAECgYEAoKt6RxGEp+TuT16s9JTkn1TTiIjpIC2Wb38oMLg6vJZDrS0nKAAP3oml+IILR6aT6HAP4fi595/Z6H4pxfP1VmRs/tF2npd51SMp/omzOCeUo93gKpgPeOUqf5pknQhFmIHa6OFaAZkMAIJvpU07rIRTJ4ZY3ICgybSgE3Z5qNkCQQDnuLSJeqGtXKcgEYV3/zBxmKUaXIjixx5LEXzDrNavybbuzrBcIhvPStxfmrUMIGERatw4MklR//Y2CJy6mAb9AkEA4AcJBabCm+cRbHRDbBG5DrOeNY4LfSYw7JIymmI3JdjAk0BKXzw45I6hOi57Wwe8ha6o6ua5QXPINmMO5RzTxwJAWWBW0kgooDOCis33Swd06fvUgsx6R+5Bcvb1xoud6wPEuWujfoXxcA0R3xZPCrRafFtZQZuzyBsQHltICHinCQJATVYgZzMKO4epHCeGnuf/WnPnSdWfYbStbv2JOoJtR0NsN2kaQ4YmncAfKFajfulMy1lgNrcvfMjbdOr9WVp6QQJBALRJ8AY93sR6T2y3SXseYykFxhGEspkk7pHCcW96kI+YSuaOGB5UgmtKrgoOetmDcaY91brxmadVZ7Ts3OJix6k=";
	private static final String baofooPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXDZnx40vP6+779Y4Z0Rz7/DhL2it5zkDXDmjdTBD/EsLt2Eh9j07nPZeID2izt6KprfbQ6sBXK3rKhQlurqRj2z9i3iH/LnL6hBT5q3kQ39j5pFIjN47hnzgKWbNUZc4aG2VkE73gV3u12AJI6he4yzKjvx4tPqFINp+rNQUdkQIDAQAB";

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

	private BaoFooCommonReqDTO getErrorAuthRequest(TxnSubTypeEnum type) {
		BaoFooCommonReqDTO request = new BaoFooCommonReqDTO();
		request.setVersion(VERSION);
		request.setMember_id(TEST_MEMBER_ID);
		request.setTerminal_id(ERROR_TERMINAL_ID);
		request.setTxn_type(TXN_TYPE);
		request.setTxn_sub_type(type.getCode());
		request.setData_type(DATA_TYPE);
		return request;
	}

	private BaoFooPaymentDTO getDataContent() {
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

		return dataContent;
	}

	private void doWork(BaoFooPaymentDTO dataContent, BaoFooCommonReqDTO request, String privateKey, String publicKey) {
		try {

			String tempReqdata = JSON.toJSONString(dataContent);
			String origData = new String(Base64.encodeBase64(tempReqdata.getBytes(Charset.forName(UTF_8))));
			ResponseDTO encryptResponseDTO = RsaCodingUtil.encryptAndCheckPrivateKey(origData, privateKey);
			String encryptData = encryptResponseDTO.getValue();
			if (encryptData != null && encryptResponseDTO.getException() != null) {
				System.err.println(encryptResponseDTO.getException().getClass());
			}

			System.out.println("----------->私钥加密结果：" + encryptData);

			request.setData_content(encryptData);
			String requestBody = JSON.toJSONString(request);
			Map<String, String> requestParams = stringToMap(requestBody);

			String encryptPostResult = HttpUtils.httpPostNoBody(REQUEST_URL_REAL, requestParams);

			System.out.println(encryptPostResult);
			ResponseDTO responseDTO = RsaCodingUtil.decryptAndCheckPublicKey(encryptPostResult, publicKey);
			String postResult = responseDTO.getValue();
			if (responseDTO != null && responseDTO.getException() != null) {
				System.err.println(responseDTO.getException().getClass());
			}

			System.out.println("----------->公钥解密结果: " + postResult);
			String unBase64Data = new String(Base64.decodeBase64(postResult.getBytes(UTF_8)));
			System.out.println(unBase64Data);

			Map<String, String> resultMap = stringToMap(unBase64Data);
			System.out.println(resultMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Map<String, String> stringToMap(String jsonStr) {
		Map<String, String> resultMap = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {
		});

		return resultMap;
	}

	/**
	 * 加载错误格式的私钥(私钥加密数据为java.security.InvalidKeyException)
	 */
	@Test
	public void testErrorPrivateKeyFormat() {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);
		doWork(getDataContent(), request, baofooErrorFormatPrivateKey, baofooPublicKey);
	}

	/**
	 * 加载错误内容的私钥 {data_type=json, txn_type=0431, version=4.0.0.0,
	 * txn_sub_type=03} 返回的报文格式中不包含dataContent
	 */
	@Test
	public void testErrorPrivateKeyContent() {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);
		doWork(getDataContent(), request, baofooErrorContentPrivateKey, baofooPublicKey);
	}

	/**
	 * 加载错误格式的公钥(私钥加密数据为java.security.InvalidKeyException)
	 */
	@Test
	public void testErrorPublicKeyFormat() {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);
		doWork(getDataContent(), request, baofooPrivateKey, baofooErrorFormatPublicKey);
	}

	/**
	 * 加载错误内容的公钥(返回报文维null)
	 */
	@Test
	public void testErrorPublicKeyContent() {
		BaoFooCommonReqDTO request = getAuthRequest(TxnSubTypeEnum.QueryBindCard);
		doWork(getDataContent(), request, baofooPrivateKey, baofooErrorContentPublicKey);
	}

	/**
	 * 加载错误的终端号(BF00124-商户与终端号不匹配(member_id=795052,terminal_id=28822))
	 */
	@Test
	public void testErrorTerminalKey() {
		BaoFooCommonReqDTO request = getErrorAuthRequest(TxnSubTypeEnum.QueryBindCard);
		doWork(getDataContent(), request, baofooPrivateKey, baofooPublicKey);
	}
}

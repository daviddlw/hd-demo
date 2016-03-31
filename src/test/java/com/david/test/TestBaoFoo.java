package com.david.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.baofoo.sdk.demo.base.TransContent;
import com.baofoo.sdk.demo.base.TransHead;
import com.baofoo.sdk.demo.base.request.TransReqBF0040001;
import com.baofoo.sdk.demo.base.request.TransReqBF0040002;
import com.baofoo.sdk.demo.base.request.TransReqBF0040004;
import com.baofoo.sdk.domain.RequestParams;
import com.baofoo.sdk.http.SimpleHttpResponse;
import com.baofoo.sdk.rsa.RsaCodingUtil;
import com.baofoo.sdk.util.BaofooClient;

public class TestBaoFoo {

	private static final String DATE_TYPE = "json";

	private void doWork(String beanToJsonStr, String requestUrl) throws Exception {
		String keyStorePath = "Q:" + File.separator + "baofu_cert" + File.separator + "m_pri.pfx";
		String keyStorePassword = "123456";
		String pub_key = "Q:" + File.separator + "baofu_cert" + File.separator + "baofoo_pub.cer";
		String origData = new String(Base64.encodeBase64(beanToJsonStr.getBytes()));

		String encryptData = RsaCodingUtil.encryptByPriPfxFile(origData, keyStorePath, keyStorePassword);
		System.out.println("----------->私钥加密结果：" + encryptData);

		String memberId = "100000178";
		String terminalId = "100000859";

		RequestParams params = new RequestParams();
		params.setMemberId(Integer.parseInt(memberId));
		params.setTerminalId(Integer.parseInt(terminalId));
		params.setDataType(DATE_TYPE);
		params.setDataContent(encryptData);
		params.setVersion("4.0.0");
		params.setRequestUrl(requestUrl);
		SimpleHttpResponse response = BaofooClient.doRequest(params);

		System.out.println("宝付支付结果：" + response.getEntityString());

		String result2 = response.getEntityString();
		String decryptData = RsaCodingUtil.decryptByPubCerFile(result2, pub_key);

		result2 = new String(Base64.decodeBase64(decryptData.getBytes()));
		System.out.println(result2);
	}

	/**
	 * 宝付代付（BF0040001）
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBF0040001Demo() throws Exception {
		TransContent<TransReqBF0040001> transContent = new TransContent<>(DATE_TYPE);
		List<TransReqBF0040001> transReqDatas = new ArrayList<>();
		TransReqBF0040001 transReqData = new TransReqBF0040001();
		transReqData.setTrans_no("ABC00000000000000003");
		transReqData.setTrans_money("0.01");
		transReqData.setTo_acc_name("david.dai戴维测试");
		transReqData.setTo_acc_no("6230580000077369565"); // 6230580000077369564
		transReqData.setTo_bank_name("平安银行");
		transReqData.setTo_pro_name("上海市");
		transReqData.setTo_city_name("上海市");
		transReqData.setTo_acc_dept("陆家浜路支行");
		transReqData.setTrans_summary("交易备注信息256个字符");

		transReqDatas.add(transReqData);
		transContent.setTrans_reqDatas(transReqDatas);

		String beanToJsonStr = transContent.obj2Str(transContent);
		System.out.println(beanToJsonStr);

		String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040001.do";

		doWork(beanToJsonStr, requestUrl);
	}

	@Test
	public void testBF0040002Demo() throws Exception {

		TransContent<TransReqBF0040002> transContent = new TransContent<>(DATE_TYPE);
		List<TransReqBF0040002> transReqDatas = new ArrayList<>();
		TransReqBF0040002 transReqData = new TransReqBF0040002();
		transReqData.setTrans_no("ABC00000000000000005");
		transReqData.setTrans_batchid("20273964");

		transReqDatas.add(transReqData);
		transContent.setTrans_reqDatas(transReqDatas);

		String beanToJsonStr = transContent.obj2Str(transContent);
		System.out.println(beanToJsonStr);

		String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040002.do";

		doWork(beanToJsonStr, requestUrl);
	}
	
	@Test
	public void testBF0040004() throws Exception{
		TransContent<TransReqBF0040004> transContent = new TransContent<>(DATE_TYPE);
		
		List<TransReqBF0040004> transReqDatas = new ArrayList<>();
		TransReqBF0040004 transReqData = new TransReqBF0040004();
		transReqData.setTrans_no("ABC00000000000000004");
		transReqData.setTrans_money("0.01");
		transReqData.setTo_acc_name("david.dai戴维测试");
		transReqData.setTo_acc_no("6230580000077369565"); // 6230580000077369564
		transReqData.setTo_bank_name("平安银行");
		transReqData.setTo_pro_name("上海市");
		transReqData.setTo_city_name("上海市");
		transReqData.setTo_acc_dept("陆家浜路支行");
		transReqData.setTrans_summary("交易备注信息256个字符");

		transReqDatas.add(transReqData);
		
		TransReqBF0040004 transReqData2 = new TransReqBF0040004();
		transReqData2.setTrans_no("ABC00000000000000005");
		transReqData2.setTrans_money("0.01");
		transReqData2.setTo_acc_name("david.dai戴维测试2");
		transReqData2.setTo_acc_no("6230580000077369566"); // 6230580000077369564
		transReqData2.setTo_bank_name("平安银行1");
		transReqData2.setTo_pro_name("上海市1");
		transReqData2.setTo_city_name("上海市1");
		transReqData2.setTo_acc_dept("陆家浜路支行1");
		transReqData2.setTrans_summary("交易备注信息256个字符1");

		transReqDatas.add(transReqData2);
		transContent.setTrans_reqDatas(transReqDatas);
		TransHead transHead = new TransHead();
		transHead.setTrans_count(String.valueOf(transReqDatas.size()));
		transHead.setTrans_totalMoney("0.02");
		transContent.setTrans_head(transHead);
		
		String beanToJsonStr = transContent.obj2Str(transContent);
		System.out.println(beanToJsonStr);

		String requestUrl = "http://paytest.baofoo.com/baofoo-fopay/pay/BF0040004.do";

		doWork(beanToJsonStr, requestUrl);
	}
}

package com.david.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Currency;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestDemo {

	private static final String SIMPLE_CHECK_URL = "http://service.sfxxrz.com/simpleCheck.ashx";
	private static final String QUERY_SIMEPLE_CITIZEN_DATA = "http://service.sfxxrz.com/querySimpleCitizenData.ashx";

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testHexToString() {
		String s = Integer.toHexString(240);
		System.out.println(s);
		
		double money = 100.88;
		for (Currency currency : Currency.getAvailableCurrencies()) {
			System.out.println(Double.toString(currency.getNumericCode()));
		}
	}

	@Test
	public void testCurrency() {
//		System.out.println(Currency.getAvailableCurrencies().size());
		System.out.println(UUID.randomUUID().toString());
	}

	@Test
	public void testIdIdentification() {
		// System.out.println("测试id card");
		// querySimpleCitizenData();
		simpleCheck();
	}

	public void simpleCheck() {
		String account = "xxx";
		String key = "xxx";
		String idNumber = "310112198801105612";
		String name = "戴励维";
		String sign = DigestUtils.md5Hex(DigestUtils.md5Hex(idNumber + account).toUpperCase() + key).toUpperCase();
		try {
			String url = SIMPLE_CHECK_URL + "?idNumber=" + idNumber + "&name=" + URLEncoder.encode(name, "UTF-8")
					+ "&account=" + account + "&pwd=" + key + "&sign=" + sign;

			String json = getHtml(url);
			System.out.println(json);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void querySimpleCitizenData() {
		String account = "xxx";
		String key = "xxx";
		String idNumber = "110101198010103365";
		String name = "测试";
		String sign = md5(md5(idNumber + account) + key);
		String url;
		try {
			url = "http://service.sfxxrz.com/querySimpleCitizenData.ashx?idNumber=" + idNumber + "&name="
					+ URLEncoder.encode(name, "UTF-8") + "&account=" + account + "&pwd=" + key + "&sign=" + sign;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		String json = getHtml(url);
		System.out.println(json);
	}

	public String md5(String text) {
		byte[] bts;
		try {
			bts = text.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bts_hash = md.digest(bts);
			StringBuffer buf = new StringBuffer();
			for (byte b : bts_hash) {
				buf.append(String.format("%02X", b & 0xff));
			}
			return buf.toString();
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getHtml(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}

package com.david.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.david.utils.RsaReadUtil;

public class TestGenerateKeys {

	private static final String GYL_PFX = "000191400205422.pfx";

	private static final String GYL_CER = "gnete_pds.cer";

	private static final String GYL_PFX_PASSWORD = "123456";

	private static final String YEEBAO_PFX = "hengda_yeepay_prod_3.pfx";

	private static final String YEEBAO_PFX_PASSWORD = "123456";

	@Test
	public void testGenerateKeys() {
		String gylPrivateFile = getGYLPrivateKey();
		String gylPublicFile = getGYLPublicKey();
		String yeepayFile = getYeePayPrivateKey();

		System.out.println(gylPrivateFile);
		System.out.println(gylPublicFile);
		System.out.println(yeepayFile);
	}
	
	/**
	 * 获取私钥方法
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getYeePayPrivateKey() {
		String result = "";
		try {
			String keyStorePath = "Q:" + File.separator + "generatekeys" + File.separator + YEEBAO_PFX;

			try (FileInputStream fis = new FileInputStream(new File(keyStorePath))) {
				byte[] binaryByts = new byte[fis.available()];
				fis.read(binaryByts);
				result = Base64.encodeBase64String(binaryByts);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 获取私钥方法
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getGYLPrivateKey() {
		String result = "";
		try {
			String keyStorePath = "Q:" + File.separator + "generatekeys" + File.separator + GYL_PFX;

			try (FileInputStream fis = new FileInputStream(new File(keyStorePath))) {
				byte[] binaryByts = new byte[fis.available()];
				fis.read(binaryByts);
				result = Base64.encodeBase64String(binaryByts);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private String getGYLPublicKey() {
		String result = "";
		try {
			String keyStorePath = "Q:" + File.separator + "generatekeys" + File.separator + GYL_CER;

			try (FileInputStream fis = new FileInputStream(new File(keyStorePath))) {
				byte[] binaryByts = new byte[fis.available()];
				fis.read(binaryByts);
				result = Base64.encodeBase64String(binaryByts);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}

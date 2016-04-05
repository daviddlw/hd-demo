package com.david.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class RsaReadUtil {
	static final Log log = LogFactory.getLog(RsaReadUtil.class);

	public static PublicKey getPublicKeyFromFile(String pubCerPath) {
		FileInputStream pubKeyStream = null;
		try {
			pubKeyStream = new FileInputStream(pubCerPath);
			byte[] reads = new byte[pubKeyStream.available()];
			pubKeyStream.read(reads);
			return getPublicKeyByText(new String(reads));
		} catch (FileNotFoundException e) {
			log.error("publicKey does not exist:", e);
		} catch (IOException e) {
			log.error("read privateKey file failed:", e);
		} finally {
			if (pubKeyStream != null) {
				try {
					pubKeyStream.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static PublicKey getPublicKeyByText(String pubKeyText) {
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");

			BufferedReader br = new BufferedReader(new StringReader(pubKeyText));
			String line = null;
			StringBuilder keyBuffer = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("-")) {
					keyBuffer.append(line);
				}
			}
			Base64 base64 = new Base64();
			Certificate certificate = certificateFactory
					.generateCertificate(new ByteArrayInputStream(base64.decode(keyBuffer.toString())));

			return certificate.getPublicKey();
		} catch (Exception e) {
			log.error("analysis publicKey content failed:", e);
		}
		return null;
	}

	public static PrivateKey getPrivateKeyFromFile(String pfxPath, String priKeyPass) {
		InputStream priKeyStream = null;
		try {
			priKeyStream = new FileInputStream(pfxPath);
			byte[] reads = new byte[priKeyStream.available()];
			priKeyStream.read(reads);
			return getPrivateKeyByStream(reads, priKeyPass);
		} catch (Exception e) {
			log.error("analysis privateKey file failed:", e);
		} finally {
			if (priKeyStream != null) {
				try {
					priKeyStream.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static PrivateKey getPrivateKeyByStream(byte[] pfxBytes, String priKeyPass) {
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			char[] charPriKeyPass = priKeyPass.toCharArray();
			ks.load(new ByteArrayInputStream(pfxBytes), charPriKeyPass);
			Enumeration<String> aliasEnum = ks.aliases();
			String keyAlias = null;
			if (aliasEnum.hasMoreElements()) {
				keyAlias = (String) aliasEnum.nextElement();
			}
			return (PrivateKey) ks.getKey(keyAlias, charPriKeyPass);
		} catch (IOException e) {
			log.error("analysis privateKey failed:", e);
		} catch (KeyStoreException e) {
			log.error("an exception occur when the privateKey is stored:", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("the decrypt algorithm does not exist:", e);
		} catch (CertificateException e) {
			log.error("certification exception:", e);
		} catch (UnrecoverableKeyException e) {
			log.error("unrecoverable key exception", e);
		}
		return null;
	}
	
	/**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.decodeBase64(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}

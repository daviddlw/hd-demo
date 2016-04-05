package com.david.utils;

import com.baofoo.sdk.lang.string.StringUtil;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.GET;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class RsaCodingUtil {
	static final Log log = LogFactory.getLog(RsaCodingUtil.class);

	public static String encryptByPubCerFile(String src, String pubCerPath) {
		PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(pubCerPath);
		if (publicKey == null) {
			return null;
		}
		return encryptByPublicKey(src, publicKey);
	}

	public static String encryptByPubCerText(String src, String pubKeyText) {
		PublicKey publicKey = RsaReadUtil.getPublicKeyByText(pubKeyText);
		if (publicKey == null) {
			return null;
		}
		return encryptByPublicKey(src, publicKey);
	}

	public static String encryptByPublicKey(String src, PublicKey publicKey) {
		byte[] destBytes = rsaByPublicKey(src.getBytes(), publicKey, 1);
		if (destBytes == null) {
			return null;
		}
		return StringUtil.byte2Hex(destBytes);
	}

	public static String decryptByPriPfxFile(String src, String pfxPath, String priKeyPass) {
		if ((StringUtils.isEmpty(src)) || (StringUtils.isEmpty(pfxPath))) {
			return null;
		}
		PrivateKey privateKey = RsaReadUtil.getPrivateKeyFromFile(pfxPath, priKeyPass);
		if (privateKey == null) {
			return null;
		}
		return decryptByPrivateKey(src, privateKey);
	}

	public static String decryptByPriPfxStream(String src, byte[] pfxBytes, String priKeyPass) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		PrivateKey privateKey = RsaReadUtil.getPrivateKeyByStream(pfxBytes, priKeyPass);
		if (privateKey == null) {
			return null;
		}
		return decryptByPrivateKey(src, privateKey);
	}

	public static String decryptByPrivateKey(String src, PrivateKey privateKey) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		try {
			byte[] destBytes = rsaByPrivateKey(StringUtil.hex2Bytes(src), privateKey, 2);
			if (destBytes == null) {
				return null;
			}
			return new String(destBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("decrypt content is not formated as utf-8:", e);
		} catch (Exception e) {
			log.error("decrypt action failed with exception:", e);
		}
		return null;
	}

	public static String encryptByPriPfxFile(String src, String pfxPath, String priKeyPass) {
		PrivateKey privateKey = RsaReadUtil.getPrivateKeyFromFile(pfxPath, priKeyPass);
		if (privateKey == null) {
			return null;
		}
		return encryptByPrivateKey(src, privateKey);
	}

	public static String encryptByPriPfxStream(String src, byte[] pfxBytes, String priKeyPass) {
		PrivateKey privateKey = RsaReadUtil.getPrivateKeyByStream(pfxBytes, priKeyPass);
		if (privateKey == null) {
			return null;
		}
		return encryptByPrivateKey(src, privateKey);
	}

	public static String encryptByPrivateKey(String src, PrivateKey privateKey) {
		byte[] destBytes = rsaByPrivateKey(src.getBytes(), privateKey, 1);
		if (destBytes == null) {
			return null;
		}
		return StringUtil.byte2Hex(destBytes);
	}

	public static String encryptByPrivateKey(String src, String privateKey) {
		PrivateKey piKey;
		try {
			piKey = RsaReadUtil.getPrivateKey(privateKey);
			return encryptByPrivateKey(src, piKey);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}

	}

	public static String decryptByPubCerFile(String src, String pubCerPath) {
		PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(pubCerPath);
		if (publicKey == null) {
			return null;
		}
		return decryptByPublicKey(src, publicKey);
	}

	public static String decryptByPubCerText(String src, String pubKeyText) {
		PublicKey publicKey = RsaReadUtil.getPublicKeyByText(pubKeyText);
		if (publicKey == null) {
			return null;
		}
		return decryptByPublicKey(src, publicKey);
	}

	public static String decryptByPublicKey(String src, PublicKey publicKey) {
		try {
			byte[] destBytes = rsaByPublicKey(StringUtil.hex2Bytes(src), publicKey, 2);
			if (destBytes == null) {
				return null;
			}
			return new String(destBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("decrypt content is not formated as utf-8:", e);
		}
		return null;
	}

	public static String decryptByPublicKey(String src, String publicKey) {
		try {
			PublicKey pubKey = RsaReadUtil.getPublicKey(publicKey);
			return decryptByPublicKey(src, pubKey);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}

	}

	public static byte[] rsaByPublicKey(byte[] srcData, PublicKey publicKey, int mode) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(mode, publicKey);

			int blockSize = mode == 1 ? 117 : 128;
			byte[] encryptedData = null;
			for (int i = 0; i < srcData.length; i += blockSize) {
				byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(srcData, i, i + blockSize));
				encryptedData = ArrayUtils.addAll(encryptedData, doFinal);
			}
			return encryptedData;
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException:", e);
		} catch (NoSuchPaddingException e) {
			log.error("NoSuchPaddingException:", e);
		} catch (IllegalBlockSizeException e) {
			log.error("IllegalBlockSizeException:", e);
		} catch (BadPaddingException e) {
			log.error("BadPaddingException:", e);
		} catch (InvalidKeyException e) {
			log.error("InvalidKeyException:", e);
		}
		return null;
	}

	public static byte[] rsaByPrivateKey(byte[] srcData, PrivateKey privateKey, int mode) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(mode, privateKey);

			int blockSize = mode == 1 ? 117 : 128;
			byte[] decryptData = null;
			for (int i = 0; i < srcData.length; i += blockSize) {
				byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(srcData, i, i + blockSize));
				decryptData = ArrayUtils.addAll(decryptData, doFinal);
			}
			return decryptData;
		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException:", e);
		} catch (NoSuchPaddingException e) {
			log.error("NoSuchPaddingException:", e);
		} catch (IllegalBlockSizeException e) {
			log.error("IllegalBlockSizeException:", e);
		} catch (BadPaddingException e) {
			log.error("BadPaddingException:", e);
		} catch (InvalidKeyException e) {
			log.error("InvalidKeyException:", e);
		}
		return null;
	}
}

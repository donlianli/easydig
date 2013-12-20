package com.donlian.util;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.sun.crypto.provider.SunJCE;

public class JeeheDES {
	private static final String strDefaultKey = "L6Xe8dXVGISZ17LJy7GzZaeYGpeGfe";
	private Cipher encryptCipher;
	private Cipher decryptCipher;

	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;

		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; ++i) {
			int intTmp = arrB[i];

			while (intTmp < 0) {
				intTmp += 256;
			}

			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i += 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[(i / 2)] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	public JeeheDES() throws Exception {
		this("L6Xe8dXVGISZ17LJy7GzZaeYGpeGfe");
	}

	public JeeheDES(String strKey) throws Exception {
		this.encryptCipher = null;

		this.decryptCipher = null;

		Security.addProvider(new SunJCE());
		Key key = getKey(strKey.getBytes());

		this.encryptCipher = Cipher.getInstance("DES");
		this.encryptCipher.init(1, key);

		this.decryptCipher = Cipher.getInstance("DES");
		this.decryptCipher.init(2, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception {
		return this.encryptCipher.doFinal(arrB);
	}

	public String encrypt(String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception {
		return this.decryptCipher.doFinal(arrB);
	}

	public String decrypt(String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];

		for (int i = 0; (i < arrBTmp.length) && (i < arrB.length); ++i) {
			arrB[i] = arrBTmp[i];
		}

		Key key = new SecretKeySpec(arrB, "DES");

		return key;
	}

	public static void main(String[] args) {
		try {
			String test = "50";

			JeeheDES des = new JeeheDES();
			System.out.println("加密前的字符：" + test);
			System.out.println("加密后的字符：" + des.encrypt("117310"));
			System.out.println("解密后的字符：" + des.decrypt("5bfc5feb84e7bc22"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

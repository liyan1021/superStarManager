package com.liyan.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class LicenseUtil {
    // --------------------------------------------------------------------------------------------
    // 获得密钥
    public static SecretKey getKey(String s) throws Exception {
        char[] ss = s.toCharArray();
        String sss = "";
        for (int i = 0; i < ss.length; i = i + 2) {
            sss = sss + ss[i];
        }
        SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
        DESKeySpec ks = new DESKeySpec(sss.substring(0, 8).getBytes());
        SecretKey kd = kf.generateSecret(ks);
        return kd;
    }

    // --------------------------------------------------------------------------------------------------
    // 返回加密后的字符串
    // key是用于生成密钥的字符串，input是要加密的字符串
    public static String getEncryptedString(String key, String input) {
        String base64 = "";
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));

            byte[] inputBytes = input.getBytes("UTF8");
            byte[] outputBytes = cipher.doFinal(inputBytes);
            BASE64Encoder encoder = new BASE64Encoder();
            base64 = encoder.encode(outputBytes);
        } catch (Exception e) {
            base64 = e.getMessage();
        }
        return base64;
    }

    // --------------------------------------------------------------------------------------------------
    // 返回解密后的字符串
    // key是用于生成密钥的字符串，input是要解密的字符串
    public static  String getDecryptedString(String key, String input) {
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, getKey(key));
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] raw = decoder.decodeBuffer(input);
            byte[] stringBytes = cipher.doFinal(raw);
            result = new String(stringBytes, "UTF8");
        } catch (Exception e) {
            result = e.getMessage();
        }
        return result;
    }

    public static boolean validateLicence(String license) {
    	
    	if (license == null)
    		return false;
    	
    	String trueLicense = getDecryptedString("Winner6678912345678906", getDecryptedString("Winner6678912345678906", license));
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date licenseDate = null;
    	try {
    		licenseDate = format.parse(trueLicense);
		} catch (ParseException e) {
			return false;
		}
    	
    	if (new Date().after(licenseDate))
    		return false;
    	
    	return true;
    	
    }
    
    public static void main(String[] args) {
        try {
            // SecretKey skey = mycrypt.getKey("g8TlgLEc6oqZxdwGe6pDiKB8Y");
            String ss = getEncryptedString("Winner6678912345678906", getEncryptedString("Winner6678912345678906", "2012-02-02"));
            System.out.println("ss==" + ss);
            String ss2 = getDecryptedString("Winner6678912345678906", getDecryptedString("Winner6678912345678906", ss));
            System.out.println("ss2==" + ss2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

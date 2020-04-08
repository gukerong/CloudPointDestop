package com.szxmrt.android.app.cloudpointdestop.net.entity;


import android.util.Log;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt {

	private static String TAG = Crypt.class.getSimpleName();

	// 加密模式
	public static class Encmode {
		static public final String AES = "aes";
	}

	private static class Base64 {
		private static String encode(byte[] buf) {
			try {
				//return java.util.Base64.getEncoder().encodeToString(buf);
				return android.util.Base64.encodeToString(buf, android.util.Base64.NO_WRAP);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		private static byte[] decode(String str) {
			try {
				//return java.util.Base64.getDecoder().decode(str);
				return android.util.Base64.decode(str, android.util.Base64.NO_WRAP);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String Md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			//convert the byte to hex format method 1
			StringBuilder sb = new StringBuilder();
			for (byte aByteData : byteData) {
				sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			Log.e(TAG, "MD5 error", e);
			return null;
		}
	}

	static public class AES {
		private static final String AESTYPE = "AES/CBC/NoPadding";//"AES/ECB/PKCS5Padding";

		public static String Encrypt(String password, String str) {
			try {
				if (null == str) {
					return null;
				}
				if (null == password || password.equals("")) {
					return str;
				}
				byte[] key = Md5(password).substring(0, 16).getBytes("UTF-8"); // 定长为16字符
				Cipher cipher = Cipher.getInstance(AESTYPE);
				int blockSize = cipher.getBlockSize();
				byte[] dataBytes = str.getBytes();
				int plaintextLength = dataBytes.length;
				if (plaintextLength % blockSize != 0) {
					plaintextLength = plaintextLength + (blockSize - (plaintextLength %
							blockSize));
				}
				byte[] plaintext = new byte[plaintextLength];
				System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
				SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
				IvParameterSpec ivspec = new IvParameterSpec(key);
				cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
				byte[] encrypted = cipher.doFinal(plaintext);
				return Base64.encode(encrypted);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public static String Decrypt(String password, String enc) {
			try {
				if (null == enc) {
					return null;
				}
				if (null == password || password.equals("")) {
					return enc;
				}
				byte[] key = Md5(password).substring(0, 16).getBytes("UTF-8"); // 定长为16字符
				byte[] iv = key;
				byte[] enc_buf = Base64.decode(enc);
				if (null == enc_buf) {
					Log.e(TAG, "Base64.decode err");
					return null;
				}
				Cipher cipher = Cipher.getInstance(AESTYPE);
				SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
				IvParameterSpec ivspec = new IvParameterSpec(iv);
				cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
				byte[] original = cipher.doFinal(enc_buf);
				String originalString = new String(original);
				return originalString.replaceAll("\0+$", ""); // 去掉末尾填充的\0字符（注：因目前处理的都是文本字符串 [XXX]）
			} catch (Exception e) {
				Log.e("crypt", e.toString());
			}
			return null;
		}
	}

	public static class RSA {
		private static String CurPrefix(String pubKey) {
			String[] lines = pubKey.split("\n");
			StringBuilder key = new StringBuilder();
			for (String s : lines) {
				if (s.startsWith("-----")) {
					continue;
				}
				key.append(s);
			}
			return key.toString();
		}

		public static String Encrypt(String pubKey, String str) {
			try {
				if (null == pubKey || pubKey.equals("")) {
					Log.e(TAG, "pubKey null");
					return str;
				}
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				//// 成生的rsa_public_key.pem文件内容中，去掉首尾“-----***-----”，连成一行
				//String pubKey = ""
				//        //+ "-----BEGIN PUBLIC KEY-----" +
				//        + "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClnAfSpNh3EMKoMGN10MWlCmV+"
				//        + "8lcYU92GnvgHVlFn9rS9aZEig9Dy+9Wos13Zfszp3qfPo7NlnXP59CUKlC07zw/Z"
				//        + "8VPJsHQrsah2HX6nQXKlgyFcqB6q6GoRI4Vp36Vdu8XoNSiWsz7KpBY7MHgMy4uA"
				//        + "xsH7vYPq9U30Q0sBlwIDAQAB"
				//        //+ "-----END PUBLIC KEY-----" +
				//        + "";
				pubKey = CurPrefix(pubKey);
				byte[] keyBytes = Base64.decode(pubKey);
				if (null == keyBytes) {
					Log.e(TAG, "pubKey Base64.decode err");
					return null;
				}
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PublicKey publicKey = keyFactory.generatePublic(keySpec);
				cipher.init(Cipher.ENCRYPT_MODE, publicKey);
				return Base64.encode(cipher.doFinal(str.getBytes()));
			} catch (Exception e) {
				Log.e(TAG, "Encrypt error", e);
			}
			return null;
		}
/*
        public static String Decrypt(String priKey, String enc) {
            try {
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                //// 成生的rsa_private_key.pem文件内容中，去掉首尾“-----***-----”，连成一行
                //String priKey = ""
                //        //+ "-----BEGIN RSA PRIVATE KEY-----" + "\n"
                //        + "MIICXQIBAAKBgQClnAfSpNh3EMKoMGN10MWlCmV+8lcYU92GnvgHVlFn9rS9aZEi" + ""
                //        + "g9Dy+9Wos13Zfszp3qfPo7NlnXP59CUKlC07zw/Z8VPJsHQrsah2HX6nQXKlgyFc" + ""
                //        + "qB6q6GoRI4Vp36Vdu8XoNSiWsz7KpBY7MHgMy4uAxsH7vYPq9U30Q0sBlwIDAQAB" + ""
                //        + "AoGABNjCKdoFM8bby4xO/N21SxU4zzRMdDvQGeaBH8XY8A/6TijOxJHTBal1IVDO" + ""
                //        + "iFT7rkSD6MHDuE+ZW1FX3C1l/XQOdBKzUMJhwJwgjBW9IpHNzUlN7kSObF3eYtae" + ""
                //        + "PjCQivAFMOUUSemsSiLfo80IzCIvpV0WkoNxZavUx0fq/dECQQDPq7V/ux2rIG8x" + ""
                //        + "48ru3kGafqYQF0g2GHS4kGAY/D/79ZIZS9DiMxgmxxP03vFOtNfEO0oD3udT1mUg" + ""
                //        + "qFkh9ULTAkEAzCZ1+Y2ISmXBlk3Zl6PTghCurzTZHmDZhKNoTFOqt0avh8Ppo/PC" + ""
                //        + "n+12Mgx8x0FQ7S6jk6V3+yy06nnyD64jrQJBALirMpzBIeLY1siAjibX0XK3CKjq" + ""
                //        + "azZfjPvKtwnA1o0RlLeV6cwcL2/cO+zWi7K3sd838dt7Ti4JSqg9y/Ucii0CQGDb" + ""
                //        + "5qFuSzmxKbYcXZ6ateFB9P9fvZuyK8HIndWI5LhsKx/pDdMh9jdWvPtl/VW0YacG" + ""
                //        + "t8l3eoOLZJLTJMvXvUkCQQCjx4yA/60HI78YIMbA3d1esuJ7nzhBv8MHXcUuvBb/" + ""
                //        + "zBu0YwR8A+Zl/zTy1CZBiBAwXU2+JXyY85TuDSzALrio" + ""
                //        //+ "-----END RSA PRIVATE KEY-----" + "\n"
                //        + "";
                priKey = CurPrefix(priKey);
                byte[] priKeyData = Base64.decode(priKey);
                if (null == priKeyData) {
                    Logger.e("priKeyData Base64.decode err");
                    return null;
                }
                RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence)
                        ASN1Sequence.fromByteArray(priKeyData));
                RSAPrivateKeySpec rsaPrivKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(),
                        asn1PrivKey.getPrivateExponent());
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivKeySpec);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return new String(cipher.doFinal(Base64.decode(enc)));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return null;
        }*/
	}
}

package cc.nanjo.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class Rsa2Utils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String RSA_ALGORITHM_SIGN = "SHA256WithRSA";

    private static RSAPublicKey rsaPublicKey(String publicKey) {
        // 通过PKCS#8编码的Key指令获得私钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            // 通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static RSAPrivateKey rsaPrivateKey(String privateKey) {
        // 通过PKCS#8编码的Key指令获得私钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 为RSA算法创建一个KeyPairGenerator对象
     */
    public static Map<String, String> createKeys(int keySize) {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->【" + RSA_ALGORITHM + "】");
        }

        // 初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 公钥加密
     */
    public static String publicEncrypt(String publicKey, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

            RSAPublicKey rsaPublicKey = rsaPublicKey(publicKey);

            cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
            return Base64.encodeBase64URLSafeString(
                    rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPublicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串【" + data + "】时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     */
    public static String privateDecrypt(String privateLey, String publicKey, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

            RSAPrivateKey rsaPrivateKey = rsaPrivateKey(privateLey);
            RSAPublicKey rsaPublicKey = rsaPublicKey(publicKey);

            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaPublicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串【" + data + "】时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     */
    public static String privateEncrypt(String privateLey, String publicKey, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

            RSAPrivateKey rsaPrivateKey = rsaPrivateKey(privateLey);
            RSAPublicKey rsaPublicKey = rsaPublicKey(publicKey);

            cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
            return Base64.encodeBase64URLSafeString(
                    rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), rsaPublicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串【" + data + "】时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     */
    public static String publicDecrypt(String publicKey, String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);

            RSAPublicKey rsaPublicKey = rsaPublicKey(publicKey);

            cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), rsaPublicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串【" + data + "】时遇到异常", e);
        }
    }

    /**
     * 签名
     */
    public static String sign(String privateLey, String data) {
        try {
            // sign
            Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);

            RSAPrivateKey rsaPrivateKey = rsaPrivateKey(privateLey);

            signature.initSign(rsaPrivateKey);
            signature.update(data.getBytes(CHARSET));
            return Base64.encodeBase64URLSafeString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名字符串【" + data + "】时遇到异常", e);
        }
    }

    /**
     * 验证签名
     */
    public static boolean verify(String publicKey, String data, String sign) throws Exception {
        Signature signature;
        signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
        RSAPublicKey rsaPublicKey = rsaPublicKey(publicKey);
        signature.initVerify(rsaPublicKey);
        signature.update(data.getBytes(CHARSET));
        return signature.verify(Base64.decodeBase64(sign));
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为【" + maxBlock + "】的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

}

package com.myproject.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author lc_xin.
 * @date 2017-10-10
 * 基于BC的RSA数字签名算法
 */
public class RSACoderUtil {
    private static final String ENCODING = "UTF-8";
    private static final String KEY_ALGORITHM = "RSA";//非对称加密密钥算法
//    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";//指定数字签名算法（可以换成SHA1withRSA或SHA256withRSA）
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";//指定数字签名算法（可以换成SHA1withRSA或SHA256withRSA）
    private static final int KEY_SIZE = 1024;//非对称密钥长度（512~1024之间的64的整数倍）
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 生成发送方密钥对
     */
    public static KeyPair initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);//密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);//指定密钥长度
        KeyPair keyPair = keyPairGenerator.generateKeyPair();//生成密钥对
        return keyPair;
    }

    /**
     * 还原公钥
     * @param pubKey 二进制公钥
     */
    public static PublicKey toPublicKey(byte[] pubKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//密钥工厂
        return keyFactory.generatePublic(new X509EncodedKeySpec(pubKey));//还原公钥
    }

    /**
     * 还原私钥
     * @param priKey 二进制私钥
     */
    public static PrivateKey toPrivateKey(byte[] priKey) throws NoSuchAlgorithmException,
            InvalidKeySpecException{
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//密钥工厂
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priKey));//还原私钥
    }

    /**
     * 私钥验签(签名)
     * @param data     待加密数据
     * @param keyByte  私钥
     */
    public static byte[] signPriKey(String data, byte[] keyByte) throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            InvalidKeyException,
            SignatureException,
            UnsupportedEncodingException {
        PrivateKey priKey = toPrivateKey(keyByte);//还原私钥

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
//        signature.update(data.getBytes(ENCODING));
        signature.update(ConvertsUtil.stringToBytes(data));

        return signature.sign();
    }

    /**
     * 公钥验签(验证)
     * @param data        原文（待加密数据，也成为“待校验数据”）
     * @param keyByte    公钥
     * @param sign        密文（也称作“签名”）
     */
    public static boolean signPubKey(String data, byte[] keyByte, byte[] sign) throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            InvalidKeyException,
            SignatureException,
            UnsupportedEncodingException {
        PublicKey pubKey = toPublicKey(keyByte);//还原公钥

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
//        signature.update(data.getBytes(ENCODING));
        signature.update(ConvertsUtil.stringToBytes(data));

        return signature.verify(sign);
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 获取公钥
     */
    public static byte[] getPublicKey(KeyPair keyPair){
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 获取私钥
     */
    public static byte[] getPrivateKey(KeyPair keyPair){
        return keyPair.getPrivate().getEncoded();
    }

    /**
     * 从keyStore中获取公钥私钥
     * @param keystore 密钥库
     * @param alias 别名
     * @param password 密码
     * @return
     */
    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            Key key=keystore.getKey(alias,password);
            if(key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey=cert.getPublicKey();
                return new KeyPair(publicKey,(PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    /**
     * 生成证书
     * @param filePath 生成文件的地址
     * @throws Exception
     */
    public static void writerFileKeyPair(String filePath)throws Exception {
        byte[] pubKey1;//甲方公钥
        byte[] priKey1;//甲方私钥
        KeyPair keyPair1 = RSACoderUtil.initKey();//生成甲方密钥对
        pubKey1 = RSACoderUtil.getPublicKey(keyPair1);
        priKey1 = RSACoderUtil.getPrivateKey(keyPair1);
        FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
        FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
        BufferedWriter pubbw = new BufferedWriter(pubfw);
        BufferedWriter pribw = new BufferedWriter(prifw);
        pubbw.write(Base64.encodeBase64String(pubKey1));
        pribw.write(Base64.encodeBase64String(priKey1));
        pubbw.flush();
        pubbw.close();
        pubfw.close();
        pribw.flush();
        pribw.close();
        prifw.close();
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param filePath
     *            公钥文件
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static String loadPublicKeyByFile(String filePath) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param filePath
     *            私钥文件
     * @return 是否成功
     * @throws Exception
     */
    public static String loadPrivateKeyByFile(String filePath) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 从KeyStore获取公钥私钥
     * @param keystoreFile keyStore密钥库地址
     * @param keyStorePassWord keyStore密钥库密码
     * @param exportedFile 输出的公钥私钥地址
     * @param alias 别名
     * @param password 别名密码
     * @throws Exception
     */
    public static void exportKeyPair(String keystoreFile,String keyStorePassWord,String exportedFile,String alias,String password) throws Exception{
        KeyStore keystore=KeyStore.getInstance("JKS");
        BASE64Encoder encoder=new BASE64Encoder();
        keystore.load(new FileInputStream(keystoreFile),keyStorePassWord.toCharArray());
        KeyPair keyPair=getPrivateKey(keystore,alias,password.toCharArray());
        PrivateKey priKey=keyPair.getPrivate();
        PublicKey pubKey=keyPair.getPublic();
        FileWriter pubfw = new FileWriter(exportedFile + "/publicKey.keystore");
        FileWriter prifw = new FileWriter(exportedFile + "/privateKey.keystore");
        BufferedWriter pubbw = new BufferedWriter(pubfw);
        BufferedWriter pribw = new BufferedWriter(prifw);
        System.out.println("提取的公钥为___:"+Base64.encodeBase64String(pubKey.getEncoded()));
        System.out.println("提取的私钥为___:"+Base64.encodeBase64String(priKey.getEncoded()));
        pubbw.write(encoder.encode(pubKey.getEncoded()));
        pribw.write(encoder.encode(priKey.getEncoded()));
        pubbw.flush();
        pubbw.close();
        pubfw.close();
        pribw.flush();
        pribw.close();
        prifw.close();
    }

    /**
     * 从KeyStore加载公钥
     * @param keystoreFile keyStore密钥库地址
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static byte[] loadPublicKeystoreFile(String keystoreFile,String keyStorePassWord,String alias,String password) throws Exception {
        try {
            KeyStore keystore=KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystoreFile),keyStorePassWord.toCharArray());
            KeyPair keyPair=getPrivateKey(keystore,alias,password.toCharArray());
            PublicKey pubKey=keyPair.getPublic();
            return pubKey.getEncoded();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        }
    }

    /**
     * 从KeyStore加载私钥
     * @param keystoreFile keyStore密钥库地址
     * @return 是否成功
     * @throws Exception
     */
    public static byte[] loadPrivateKeyByKeystoreFile(String keystoreFile,String keyStorePassWord,String alias,String password) throws Exception {
        try {
            KeyStore keystore=KeyStore.getInstance("JKS");
            keystore.load(new FileInputStream(keystoreFile),keyStorePassWord.toCharArray());
            KeyPair keyPair=getPrivateKey(keystore,alias,password.toCharArray());
            PrivateKey priKey=keyPair.getPrivate();
            return priKey.getEncoded();
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        }
    }

    /**
         * 测试
         */
    public static void main(String[] args) throws Exception {
        byte[] pubKey1;//公钥
        byte[] priKey1;//私钥

        /*********************测试是否可以正确生成以上2个key*********************/
        /*KeyPair keyPair1 = RSACoderUtil.initKey();//生成密钥对
        pubKey1 = RSACoderUtil.getPublicKey(keyPair1);
        priKey1 = RSACoderUtil.getPrivateKey(keyPair1);

        System.out.println("公钥pubKey1-->"+Base64.encodeBase64String(pubKey1)+"@@pubKey1.length-->"+pubKey1.length);
        System.out.println("私钥priKey1-->"+Base64.encodeBase64String(priKey1)+"@@priKey1.length-->"+priKey1.length);*/

//        writerFileKeyPair("C:\\Users\\Administrator\\Desktop\\key");//生成秘钥对文件
        testSignToken();
//        test();
//        testSign();
//        testHttpSign();
//        testKeyStore();
    }

    static void testSignToken()throws Exception{
        String keystoreFile="C:\\Users\\Administrator\\Desktop\\keyStore\\weconex_server.keystore";
        String privateKey =Base64.encodeBase64String(loadPrivateKeyByKeystoreFile(keystoreFile,"888999","weconex","888999"));
        String publicKey =Base64.encodeBase64String(loadPublicKeystoreFile(keystoreFile,"888999","weconex","888999"));
        /*StringBuffer code = new StringBuffer();
//        code.append(fixNumberAddPre0(4,Integer.toHexString(1))).append("00");
        code.append(fixNumberAddPre0(4,Integer.toHexString(22))).append("E22B4DE391BFFE4628E20F39326F982D020368937542");
//        code.append(fixNumberAddPre0(4,Integer.toHexString(1))).append("01");
        code.append(fixNumberAddPre0(4, Integer.toHexString(4))).append(getHexAmount(4,"1000"));
        code.append(fixNumberAddPre0(4,Integer.toHexString(16))).append(getOrderId());
        code.append(fixNumberAddPre0(4,Integer.toHexString(4))).append(Integer.toHexString(Integer.parseInt(String.valueOf(System.currentTimeMillis()/1000L))).toUpperCase());
        System.out.println("authenticationCode,length:"+code.toString()+"==============>>"+code.length());
        byte[] sign = RSACoderUtil.signPriKey(code.toString(), Base64.decodeBase64(privateKey));
        System.err.println("签名：" + Base64.encodeBase64String(sign));
        System.out.println("token,length:"+ConvertsUtil.bytesToHex(sign)+"==============>>"+ConvertsUtil.bytesToHex(sign).length());
        boolean status = RSACoderUtil.signPubKey(code.toString(),Base64.decodeBase64(publicKey), sign);
        System.err.println("签名验证结果：" + status);
        code.append(fixNumberAddPre0(4,Integer.toHexString(ConvertsUtil.bytesToHex(sign).length()/2))).append(ConvertsUtil.bytesToHex(sign));
        System.out.println("authenticationCode,length:"+code.toString()+"==============>>"+code.length());*/

        String s = "0016E22B4DE391BFFE4628E20F39326F982D020368937542000400000064001020171012022202699131507789322699000459E83BAE";
        byte[] sign = RSACoderUtil.signPriKey(s, Base64.decodeBase64(privateKey));
        System.out.println("签名：" + ConvertsUtil.bytesToString(sign));
        boolean flag = RSACoderUtil.signPubKey(s,Base64.decodeBase64(publicKey), sign);
        System.err.println("签名验证结果：" + flag);
    }

    static void testKeyStore()throws Exception{
        String keystoreFile="C:\\Users\\Administrator\\Desktop\\keyStore\\ddmmz_server.keystore";
        String exportedFile="C:\\Users\\Administrator\\Desktop\\";
        exportKeyPair(keystoreFile,"888999",exportedFile,"weconex","888999");
    }

    static void test() throws Exception {
        String privateKey =loadPrivateKeyByFile("C:\\Users\\Administrator\\Desktop\\key\\privateKey.keystore");
        String publicKey =loadPrivateKeyByFile("C:\\Users\\Administrator\\Desktop\\key\\publicKey.keystore");
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("加密前文字：" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSACoderUtil.encryptByPublicKey(data, publicKey);
        System.out.println("加密后：" + ConvertsUtil.bytesToString(encodedData));
        byte[] decodedData = RSACoderUtil.decryptByPrivateKey(encodedData, privateKey);
        System.out.println("解密后：" + new String(decodedData));
    }

    static void testSign() throws Exception {
        String privateKey =loadPrivateKeyByFile("C:\\Users\\Administrator\\Desktop\\key\\privateKey.keystore");
        String publicKey =loadPrivateKeyByFile("C:\\Users\\Administrator\\Desktop\\key\\publicKey.keystore");
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSACoderUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：" + ConvertsUtil.bytesToString(encodedData));
        byte[] decodedData = RSACoderUtil.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后：" + new String(decodedData));
        System.err.println("私钥签名——公钥验证签名");

        byte[] sign = RSACoderUtil.signPriKey(Base64.encodeBase64String(encodedData), Base64.decodeBase64(privateKey));
        System.err.println("签名：" + Base64.encodeBase64String(sign));

        boolean status = RSACoderUtil.signPubKey(Base64.encodeBase64String(encodedData),Base64.decodeBase64(publicKey), sign);
        System.err.println("签名验证结果：" + status);
    }

    static void testHttpSign() throws Exception {
        String keystoreFile="C:\\Users\\Administrator\\Desktop\\keyStore\\weconex_server.keystore";
        String param = "id=ddmmz&name=萌妹纸";
        String privateKey =Base64.encodeBase64String(loadPrivateKeyByKeystoreFile(keystoreFile,"888999","weconex","888999"));
        String publicKey =Base64.encodeBase64String(loadPublicKeystoreFile(keystoreFile,"888999","weconex","888999"));
        byte[] encodedData = RSACoderUtil.encryptByPrivateKey(param.getBytes(), privateKey);
        System.out.println("加密后：" + ConvertsUtil.bytesToString(encodedData));

        byte[] decodedData = RSACoderUtil.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后：" + new String(decodedData));

        byte[] sign = RSACoderUtil.signPriKey(Base64.encodeBase64String(encodedData), Base64.decodeBase64(privateKey));
        System.err.println("签名：" + Base64.encodeBase64String(sign));

        boolean status = RSACoderUtil.signPubKey(Base64.encodeBase64String(encodedData),Base64.decodeBase64(publicKey), sign);
        System.err.println("签名验证结果：" + status);
    }



    public static String fixNumberAddPre0(Integer length,String number){
        String last = number;
        for(int i = 0;i<length-number.length();i++){
            last = "0" + last;
        }
        return last;
    }

    public static String getHexAmount(Integer len,String amount) {
        String temp = Integer.toHexString(Integer.parseInt(amount));
        return fixNumberAddPre0(len*2,temp).toUpperCase();
    }

    public static String getOrderId() {
        String dateToStr = DateUtil.praseDate(new Date(), "yyyyMMddhhmmssSSS");
        int num = new Random().nextInt(99)%(99-10+1) + 10;
        return dateToStr+num+ Calendar.getInstance().getTimeInMillis();
    }
}

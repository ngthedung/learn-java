package com.hkt.client.swingexp.app.license;

import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class DecryptAES {
    //khóa  mã hóa, giải mã

    private byte[] keyBytes;
    // dữ liệu cần giải mã
    private byte[] dataNeedDecrypt;
    // key giải mã
    private SecretKeySpec key;
    private Cipher cipher;
    private Base32 base32;

    /**
     * Khởi tại Giải mã AES
     * keyBytes: key User truyền vào để làm khóa giải mã(dạng byte)
     * dataEncrypted: dữ liệu cần giải mã
     */
    public DecryptAES(String key, String dataNeedEncrypt) {
        Security.addProvider(new BouncyCastleProvider());
        base32 = new Base32();
        initCipher(key, dataNeedEncrypt);
    }

    private void initCipher(String keyString, String dataNeedDecrypt) {
        //init key and data
        byte[] keyBytesInput = base32.string32_2_ByteArray(keyString);
        keyBytes = Arrays.copyOf(keyBytesInput, 16);
        this.dataNeedDecrypt = base32.string32_2_ByteArray(dataNeedDecrypt);

        // init cipher
        try {
            key = new SecretKeySpec(this.keyBytes, "AES");
            cipher = Cipher.getInstance("AES", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * hàm giải mã
     * trả về mảng byte(dữ liệu) được giải mã
     * @return 
     */
    public String decrypt() {
        try {
            int ctLength = dataNeedDecrypt[dataNeedDecrypt.length - 1];
            byte[] cipherText = new byte[dataNeedDecrypt.length - 1];
            System.arraycopy(dataNeedDecrypt, 0, cipherText, 0, cipherText.length);

            byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
            int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
            ptLength += cipher.doFinal(plainText, ptLength);
            byte[] dataAfterDecrypt = new byte[ptLength];
            System.arraycopy(plainText, 0, dataAfterDecrypt, 0, ptLength);
            return new String(dataAfterDecrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}


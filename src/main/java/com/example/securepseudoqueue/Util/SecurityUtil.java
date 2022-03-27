package com.example.securepseudoqueue.Util;

import com.example.securepseudoqueue.DAO.Transaction;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
public class SecurityUtil {

    public String encryptTransaction(Transaction transaction) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, IOException, InvalidKeySpecException, BadPaddingException {
        // prepare cipher
        var cipher = Cipher.getInstance(KeyUtil.CIPHER_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE,KeyUtil.getKeyFromPassword(KeyUtil.PASSWORD,KeyUtil.SALT));

        //encrypt
        var encryptedByte = cipher.doFinal(transaction.toString().getBytes());

        //encode
        var encryptedText = Base64.getEncoder().encodeToString(encryptedByte);

        return encryptedText;
    }

    public Transaction decryptTransaction(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException {
        // decode text
        var encryptedTextByte = Base64.getDecoder().decode(encryptedText);

        // prepare cipher
        var cipher = Cipher.getInstance(KeyUtil.CIPHER_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, KeyUtil.getKeyFromPassword(KeyUtil.PASSWORD,KeyUtil.SALT));

        // decrypt
        var decryptedText = new String(cipher.doFinal(encryptedTextByte));

        var jsonObject =  new JsonParser().parse(decryptedText).getAsJsonObject();
        var transaction = new Transaction(
                jsonObject.get("accountNumber").getAsString(),
                jsonObject.get("type").getAsString(),
                jsonObject.get("amount").getAsString(),
                jsonObject.get("currency").getAsString(),
                jsonObject.get("accountFrom").getAsString());

        return transaction;

    }

}

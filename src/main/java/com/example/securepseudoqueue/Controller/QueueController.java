package com.example.securepseudoqueue.Controller;

import com.example.securepseudoqueue.DAO.Transaction;
import com.example.securepseudoqueue.Service.QueueService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PostMapping("/sender")
    public String getMessage(@RequestBody JsonNode transaction) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, BadPaddingException {
        var trans = new Transaction(
                transaction.get("Account Number").textValue(),
                transaction.get("Type").textValue(),
                transaction.get("Amount").textValue(),
                transaction.get("Currency").textValue(),
                transaction.get("AccountFrom").textValue());
        queueService.sendTransaction(trans);
        return "SUCCESS";
    }

    @PostMapping("/receiver")
    public String receiveMessage(@RequestBody String encryptedTransaction) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException {
        System.out.println(encryptedTransaction);
        queueService.receiveTransaction(encryptedTransaction);
        return "SUCCESS";
    }
}

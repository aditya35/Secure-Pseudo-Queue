package com.example.securepseudoqueue.Service;

import com.example.securepseudoqueue.Constant.UrlConstant;
import com.example.securepseudoqueue.DAO.Transaction;
import com.example.securepseudoqueue.Repository.TransactionRepository;
import com.example.securepseudoqueue.Util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class QueueService {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void sendTransaction(Transaction transaction) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        var encryptedTransaction = securityUtil.encryptTransaction(transaction);
        ResponseEntity<String> response = restTemplate.postForEntity(UrlConstant.RECIEVE_URL,encryptedTransaction,String.class);
    }

    public void receiveTransaction(String encryptedTransaction) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException {
        var transaction = securityUtil.decryptTransaction(encryptedTransaction);
        transactionRepository.save(transaction);
    }
}

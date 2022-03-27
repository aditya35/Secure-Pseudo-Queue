package com.example.securepseudoqueue.DAO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name="AccountNumber")
    private String accountNumber;

    @Column(name = "Type")
    private String type;

    @Column(name = "Amount")
    private String amount;

    @Column(name = "Currency")
    private String currency;

    @Column(name = "AccountFrom")
    private String accountFrom;

    public Transaction(String accountNumber, String type, String amount, String currency, String accountFrom) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.accountFrom = accountFrom;
    }

    @Override
    public String toString() {
        var jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("accountNumber",accountNumber);
        jsonObject.put("type",type);
        jsonObject.put("amount",amount);
        jsonObject.put("currency",currency);
        jsonObject.put("accountFrom",accountFrom);
        return  jsonObject.toString();
    }
}

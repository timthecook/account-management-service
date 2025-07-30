package com.tim.accountservice.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false, unique = true)
    private Long accountNumber;

    private double balance;

    @ElementCollection
    private List<Long> userId = new ArrayList<>();

    @ElementCollection
    private List<Long> transactionId = new ArrayList<>();

    private String nickname;

    public Account() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public Long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Long accountNumber) { this.accountNumber = accountNumber; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public List<Long> getUserId() { return userId; }
    public void setUserId(List<Long> userId) { this.userId = userId; }

    public List<Long> getTransactionId() { return transactionId; }
    public void setTransactionId(List<Long> transactionId) { this.transactionId = transactionId; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}

package com.tim.accountservice.dto;

import com.tim.accountservice.model.AccountType;

import java.util.Collections;
import java.util.List;

public record AccountDto(
        Long id,
        AccountType accountType,
        Long accountNumber,
        double balance,
        List<Long> userId,
        List<Long> transactionId,
        String nickname
) {
    public AccountDto(Long id, AccountType accountType, Long accountNumber, double balance,
                      List<Long> userId, List<Long> transactionId, String nickname) {
        this.id = id;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId == null ? Collections.emptyList() : Collections.unmodifiableList(userId);
        this.transactionId = transactionId == null ? Collections.emptyList() : Collections.unmodifiableList(transactionId);
        this.nickname = nickname;
    }

    public Double getId() {
        return 0.0;
    }
}

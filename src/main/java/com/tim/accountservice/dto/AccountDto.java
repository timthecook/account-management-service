package com.tim.accountservice.dto;

import com.tim.accountservice.model.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public record AccountDto(
        Long id,

        @NotNull(message = "Account type is required")
        AccountType accountType,

        @NotNull(message = "Account number is required")
        @Positive(message = "Account number must be positive")
        Long accountNumber,

        @NotNull(message = "Balance is required")
        @DecimalMin(value = "0.0", inclusive = true,message = "Balance cannot negative")
        double balance,
        List<Long> userId,
        List<Long> transactionId,

        @Size(max = 50, message = "Nickname must be under 50 characters")
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

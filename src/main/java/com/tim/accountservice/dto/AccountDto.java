package com.tim.accountservice.dto;

import com.tim.accountservice.model.AccountType;
import jakarta.validation.constraints.*;

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
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 50, message = "Email must be under 50 characters")
        String email,
        @Size(max = 50, message = "Nickname must be under 50 characters")
        String nickname
) {
    public AccountDto(Long id, AccountType accountType, Long accountNumber, double balance,
                      List<Long> userId, List<Long> transactionId, String email, String nickname) {
        this.id = id;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId == null ? Collections.emptyList() : Collections.unmodifiableList(userId);
        this.transactionId = transactionId == null ? Collections.emptyList() : Collections.unmodifiableList(transactionId);
        this.nickname = nickname;
        this.email = email;
    }

}

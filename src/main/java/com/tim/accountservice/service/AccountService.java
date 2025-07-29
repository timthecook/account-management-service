package com.tim.accountservice.service;

import com.tim.accountservice.dto.AccountDto;

import com.tim.accountservice.exception.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    AccountDto getAccountById(Long id);
    List<AccountDto> getAccounts();
    AccountDto createAccount(AccountDto accountDto);
    AccountDto updateAccount(long id, AccountDto accountDto);
    void deleteAccount(Long accountId);
}

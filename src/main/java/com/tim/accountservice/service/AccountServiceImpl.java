package com.tim.accountservice.service;

import com.tim.accountservice.dto.AccountDto;
import com.tim.accountservice.mapper.AccountMapper;
import com.tim.accountservice.model.Account;
import com.tim.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tim.accountservice.exception.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found."));
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDto> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public AccountDto updateAccount(long id, AccountDto accountDto){
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found."));

        existingAccount.setAccountType(accountDto.accountType());
        existingAccount.setAccountNumber(accountDto.accountNumber());
        existingAccount.setBalance(accountDto.balance());
        existingAccount.setNickname(accountDto.nickname());

        if(accountDto.userId() != null){
            existingAccount.setUserId((new ArrayList<>(accountDto.userId())));
        } else {
            existingAccount.setUserId(null);
        }

        if(accountDto.transactionId() != null){
            existingAccount.setTransactionId(new ArrayList<>(accountDto.transactionId()));
        } else{
            existingAccount.setTransactionId(null);
        }

        Account updated = accountRepository.save(existingAccount);

        return accountMapper.toDto(updated);
    }

    @Override
    public void deleteAccount(Long accountId){
        if (!accountRepository.existsById(accountId)) {
            throw new AccountNotFoundException("Account not found.");
        }
        accountRepository.deleteById(accountId);
    }
}

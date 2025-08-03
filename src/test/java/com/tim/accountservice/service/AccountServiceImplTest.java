package com.tim.accountservice.service;

import com.tim.accountservice.dto.AccountDto;
import com.tim.accountservice.mapper.AccountMapper;
import com.tim.accountservice.model.Account;
import com.tim.accountservice.model.AccountType;
import com.tim.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountById_whenExists_shouldReturnDTO() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);

        AccountDto dto = new AccountDto(
                accountId,
                AccountType.CHECKING,
                123456789L,
                100.00,
                List.of(1L, 2L),
                List.of(10L, 20L),
                "Test Email",
                "Test Nickname"
        );

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(dto);

        // Act
        AccountDto result = accountService.getAccountById(accountId);

        // Assert
        assertNotNull(result);
        assertEquals(accountId, result.id());
        assertEquals("Test Email", result.email());
        assertEquals("Test Nickname", result.nickname());
    }

    @Test
    void getAccountById_whenNotFound_shouldThrowException() {
        // Arrange
        Long accountId = 42L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> accountService.getAccountById(accountId));
    }
    @Test
    void createAccount_shouldSaveAndReturnDto() {
        // Arrange
        AccountDto inputDto = new AccountDto(
                null,
                AccountType.CHECKING,
                123456789L,
                250.0,
                List.of(1L),
                List.of(11L),
                "new.email@example.com",
                "New Nickname"
        );

        Account accountEntity = new Account();
        accountEntity.setAccountNumber(123456789L);
        accountEntity.setBalance(250.0);
        accountEntity.setAccountType(AccountType.CHECKING);
        accountEntity.setNickname("New Account");

        Account savedEntity = new Account();
        savedEntity.setId(10L);
        savedEntity.setAccountNumber(123456789L);
        savedEntity.setBalance(250.0);
        savedEntity.setAccountType(AccountType.CHECKING);
        savedEntity.setNickname("New Account");

        AccountDto outputDto = new AccountDto(
                10L,
                AccountType.CHECKING,
                123456789L,
                250.0,
                List.of(1L),
                List.of(11L),
                "new.email@example.com",
                "New Nickname"
        );

        when(accountMapper.toEntity(inputDto)).thenReturn(accountEntity);
        when(accountRepository.save(accountEntity)).thenReturn(savedEntity);
        when(accountMapper.toDto(savedEntity)).thenReturn(outputDto);

        // Act
        AccountDto result = accountService.createAccount(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.id());
        assertEquals("new.email@example.com", result.email());
        assertEquals("New Nickname", result.nickname());

    }
    @Test
    void updateAccount_shouldUpdateAndReturnDto() {
        // Arrange
        Long accountId = 5L;

        Account existingAccount = new Account();
        existingAccount.setId(accountId);
        existingAccount.setAccountNumber(111L);
        existingAccount.setBalance(50.0);
        existingAccount.setAccountType(AccountType.SAVINGS);
        existingAccount.setNickname("Old Nickname");

        AccountDto updateDto = new AccountDto(
                accountId,
                AccountType.CHECKING,
                222L,
                300.0,
                List.of(3L),
                List.of(30L),
                "New Email",
                "Updated Nickname"
        );

        Account updatedEntity = new Account();
        updatedEntity.setId(accountId);
        updatedEntity.setAccountNumber(222L);
        updatedEntity.setBalance(300.0);
        updatedEntity.setAccountType(AccountType.CHECKING);
        updatedEntity.setNickname("Updated Nickname");

        AccountDto resultDto = updateDto;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(updatedEntity);
        when(accountMapper.toDto(updatedEntity)).thenReturn(resultDto);

        // Act
        AccountDto result = accountService.updateAccount(accountId, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Nickname", result.nickname());
        assertEquals(222L, result.accountNumber());
        assertEquals(AccountType.CHECKING, result.accountType());
        assertEquals(300.0, result.balance());
    }
    @Test
    void deleteAccount_shouldDeleteAccount() {
        Long accountId = 7L;
        when(accountRepository.existsById(accountId)).thenReturn(true);
        accountService.deleteAccount(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }
    @Test
    void deleteAccount_whenNotFound_shouldThrowException() {
        Long accountId = 99L;
        when(accountRepository.existsById(accountId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> accountService.deleteAccount(accountId));
        verify(accountRepository, never()).deleteById(anyLong());
    }
    @Test
    void updateAccount_whenNotFound_shouldThrowException() {
        Long accountId = 999L;
        AccountDto updateDto = new AccountDto(
                accountId,
                AccountType.CHECKING,
                123456L,
                150.0,
                List.of(1L),
                List.of(2L),
                "New Email",
                "Doesn't Matter"
        );
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> accountService.updateAccount(accountId, updateDto));
        verify(accountRepository, times(1)).findById(accountId);
    }
}

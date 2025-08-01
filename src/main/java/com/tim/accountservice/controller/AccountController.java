package com.tim.accountservice.controller;

import com.tim.accountservice.dto.AccountDto;
import com.tim.accountservice.repository.AccountRepository;
import com.tim.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tim.accountservice.exception.AccountNotFoundException;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"," http://localhost:3001"})
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Controller", description = "API for managing accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Operation(summary = "Get account by ID", description = "Fetches a specific account using its ID")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id){
        AccountDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts")
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Search account", description = "Searches accounts using a keyword")
    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchAccounts(@RequestParam String keyword){
        List<AccountDto> results = accountService.searchAccounts(keyword);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Create a new account", description = "Creates a new account and returns the created account")
    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountDto account) {
        AccountDto createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @Operation(summary = "Update existing account", description = "Updates an account using its ID")
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDto account){
        AccountDto updated = accountService.updateAccount(id, account);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an account", description = "Deletes the account assciated with the provided ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}

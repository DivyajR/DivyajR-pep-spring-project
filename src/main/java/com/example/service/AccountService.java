package com.example.service;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.util.*;




@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> registerAccount(Account account) {
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return Optional.empty();
        }

        return Optional.of(accountRepository.save(account));
    }

    public Optional<Account> login(String username, String password){

        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isPresent() && account.get().getPassword().equals(password)){
            return account;   
            
        }
        else
            return Optional.empty();
    }
}

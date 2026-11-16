package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.Account;
import rukavanov.dev.model.AccountProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final AccountProperties accountProperties;
    private int accountId;

    public AccountService(AccountProperties accountProperties, UserService userService) {
        this.accountProperties = accountProperties;
    }

    public Account addAccount(int userId) {
        Account account = new Account(++accountId, userId, accountProperties.getMoneyAmount());
        accounts.put(account.getId(), account);
        return account;
    }

    public Optional<Account> getAccountById(int accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    public double getCommission() {
        return accountProperties.getCommission();
    }

}

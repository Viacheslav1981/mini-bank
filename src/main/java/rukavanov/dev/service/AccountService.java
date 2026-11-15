package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.Account;
import rukavanov.dev.model.AccountProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final AccountProperties accountProperties;
    private int accountId;

    private final UserService userService;

    public AccountService(AccountProperties accountProperties, UserService userService) {
        this.accountProperties = accountProperties;
        this.userService = userService;
    }

    public Account createAccount(String login) {
        Account account = new Account(userService.getUserByLogin(login).getId());
        account.setId(++accountId);
        account.setMoneyAmount(accountProperties.getMoneyAmount());
        accounts.put(account.getId(), account);
        userService.getUserByLogin(login).getAccountList().add(account);
        return account;
    }

    public int depositMoney(int accountId, int depositMoney) {
        Account account = accounts.get(accountId);
        int allAmount = account.getMoneyAmount() + depositMoney;
        account.setMoneyAmount(allAmount);
        return allAmount;
    }

    public int withdrawMoney(int accountId, int withdrawMoney) {
        Account account = accounts.get(accountId);
        int allMoneyAmount = account.getMoneyAmount();
        if (withdrawMoney > allMoneyAmount) {
            allMoneyAmount = 0;
            return allMoneyAmount;
        } else {
            allMoneyAmount = allMoneyAmount - withdrawMoney;
        }
        account.setMoneyAmount(allMoneyAmount);
        return allMoneyAmount;
    }

    public int getAllMoneyAmount() {
        return accountProperties.getMoneyAmount();
    }

//    public List<Account> getAccounts(String login) {
//        for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
//            entry.getValue()
//        }
//    }


}

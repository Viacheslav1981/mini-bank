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

    public Account addAccount(int userId) {
        Account account = new Account(++accountId, userId, accountProperties.getMoneyAmount());
        accounts.put(account.getId(), account);
        return account;
    }

    public Account getAccountById(int accountId) {
        return accounts.get(accountId);
    }

    public double getCommission() {
        return accountProperties.getCommission();
    }

//    public int withdrawMoney(int accountId, int withdrawMoney) {
//        Account account = accounts.get(accountId);
//        int allMoneyAmount = account.getMoneyAmount();
//        if (withdrawMoney > allMoneyAmount) {
//            allMoneyAmount = 0;
//            return allMoneyAmount;
//        } else {
//            allMoneyAmount = allMoneyAmount - withdrawMoney;
//        }
//        account.setMoneyAmount(allMoneyAmount);
//        return allMoneyAmount;
//    }

//    public String transferMoney(int fromAccountId, int toAccountId, int transferMoney) {
//        Account fromAccount = accounts.get(fromAccountId);
//        Account toAccount = accounts.get(toAccountId);
//        double commission = accountProperties.getCommission();
//        double transferMoneyWithCommission = transferMoney * (1 - commission);
//        String aboutTransfer = "";
//
//        int allMoneyAmount = fromAccount.getMoneyAmount();
//        if (transferMoney > allMoneyAmount) {
//            return aboutTransfer;
//        }
//
//        if (fromAccount.getUserId() == toAccount.getUserId()) {
//            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - transferMoney);
//            toAccount.setMoneyAmount(toAccount.getMoneyAmount() + transferMoney);
//            aboutTransfer = "(no commission same user)";
//        } else {
//            fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - transferMoney);
//            toAccount.setMoneyAmount((int) (toAccount.getMoneyAmount() + transferMoneyWithCommission));
//            aboutTransfer = ", commission is " + commission + "%"
//                + ", recipient received: " + transferMoneyWithCommission;
//        }
//        return aboutTransfer;
//    }

    public int getAllMoneyAmount() {
        return accountProperties.getMoneyAmount();
    }


}

package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.Account;
import rukavanov.dev.model.User;

import java.util.List;

@Service
public class OperationService {

    private final UserService userService;
    private final AccountService accountService;

    public OperationService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void createUser(String login) {
        User existingUser = userService.getUserByLogin(login);
        if (existingUser == null) {
            userService.addUser(login);
            int userId = userService.getUserByLogin(login).getId();

            Account account = accountService.addAccount(userId);
            userService.getUserById(userId).getAccountList().add(account);
            System.out.println("User created:");
            System.out.println(userService.getUserByLogin(login));
        } else {
            System.out.println("User " + login + " created already!");
        }
    }

    public void createAccount(int userId) {
        Account account = accountService.addAccount(userId);
        userService.getUserById(userId).getAccountList().add(account);
        System.out.println("Account created:");
        System.out.println(account);
    }

    public void getAllUsers() {
        System.out.println("All users:");
        for (int i = 0; i < userService.getUsers().size(); i++) {
            System.out.println(userService.getUsers().get(i));
        }
    }

    public void depositMoney(int accountId, int depositMoney) {
        try {
            Account account = accountService.getAccountById(accountId)
                    .orElseThrow(() -> new NullPointerException("Account with id " + accountId + " not found!"));
            int allMoneyAmount = account.getMoneyAmount() + depositMoney;
            account.setMoneyAmount(allMoneyAmount);
            System.out.println("Deposited " + depositMoney + " to account " +
                    accountId + ". New balance: " + allMoneyAmount);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    public void closeAccount(int accountId) {
        Account accountToClose = null;
        User user = null;
        int allMoneyAccountToClose = 0;
        try {
            accountToClose = accountService.getAccountById(accountId)
                    .orElseThrow(() -> new NullPointerException("Account with id " + accountId + " not found!"));
            user = userService.getUserById(accountToClose.getUserId());
            allMoneyAccountToClose = accountToClose.getMoneyAmount();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        List<Account> accountList = user.getAccountList();
        if (accountList.size() == 1) {
            System.out.println("You can not close this only one account!");
            return;
        }
        accountList.remove(accountToClose);
        Account firstAccount = accountList.getFirst();
        int firstAccountMoney = firstAccount.getMoneyAmount();
        firstAccount.setMoneyAmount(firstAccountMoney + allMoneyAccountToClose);

        System.out.println("Closed account " + accountId + ". Remaining balance "
                + allMoneyAccountToClose + " transferred to account " + firstAccount.getId() + ".");

    }

    public void withdrawMoney(int accountId, int withdrawMoney) {
        try {
            Account account = accountService.getAccountById(accountId)
                    .orElseThrow(() -> new NullPointerException("Account with id " + accountId + " not found!"));

            int allMoneyAmount = account.getMoneyAmount();
            if (withdrawMoney > allMoneyAmount) {
                System.out.println("Withdrew only " + allMoneyAmount + " to account " +
                        accountId + ". New balance: " + 0);
                account.setMoneyAmount(allMoneyAmount);
            } else {
                allMoneyAmount = allMoneyAmount - withdrawMoney;
                System.out.println("Withdrew " + withdrawMoney + " to account " +
                        accountId + ". New balance: " + allMoneyAmount);
                account.setMoneyAmount(allMoneyAmount);
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferMoney(int fromAccountId, int toAccountId, int transferMoney) {
        String aboutTransfer;
        try {
            Account fromAccount = accountService.getAccountById(fromAccountId)
                    .orElseThrow(() -> new NullPointerException("Account with id " + fromAccountId + " not found!"));
            Account toAccount = accountService.getAccountById(toAccountId)
                    .orElseThrow(() -> new NullPointerException("Account with id " + toAccountId + " not found!"));
            double commission = accountService.getCommission();
            double transferMoneyWithCommission = transferMoney * (1 - commission);

            int allMoneyAmount = fromAccount.getMoneyAmount();
            if (transferMoney > allMoneyAmount) {
                System.out.println("Not enough money to transfer from " + fromAccountId + " to " + toAccountId);
                return;
            }

            if (fromAccount.getUserId() == toAccount.getUserId()) {
                fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - transferMoney);
                toAccount.setMoneyAmount(toAccount.getMoneyAmount() + transferMoney);
                aboutTransfer = "(no commission same user)";
            } else {
                fromAccount.setMoneyAmount(fromAccount.getMoneyAmount() - transferMoney);
                toAccount.setMoneyAmount((int) (toAccount.getMoneyAmount() + transferMoneyWithCommission));
                aboutTransfer = ", commission is " + commission + "%"
                        + ", recipient received: " + transferMoneyWithCommission;
            }
            System.out.println("Transfer completed from account " + fromAccountId + " to account "
                    + toAccountId + ". Transfer amount is " + transferMoney + " "
                    + aboutTransfer);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}


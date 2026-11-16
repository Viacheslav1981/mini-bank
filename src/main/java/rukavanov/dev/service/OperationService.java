package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.Account;

@Service
public class OperationService {

    private final UserService userService;
    private final AccountService accountService;
    private int userId;

    public OperationService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void createUser(String login) {
        userService.addUser(login);
        userId = userService.getUserByLogin(login).getId();
        Account account = accountService.addAccount(userId);
        userService.getUserById(userId).getAccountList().add(account);
        System.out.println("User created:");
        System.out.println(userService.getUserByLogin(login));
    }

    public void createAccount(int userId) {
        Account account = accountService.addAccount(userId);
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
        Account account = accountService.getAccountById(accountId);
        int allMoneyAmount = account.getMoneyAmount() + depositMoney;
        account.setMoneyAmount(allMoneyAmount);
        System.out.println("Deposited " + depositMoney + " to account " +
                accountId + ". New balance: " + allMoneyAmount);
    }

    public void withdrawMoney(int accountId, int withdrawMoney) {
        Account account = accountService.getAccountById(accountId);
        int allMoneyAmount = account.getMoneyAmount();
        if (withdrawMoney > allMoneyAmount) {
            System.out.println("Withdrew only" + allMoneyAmount + " to account " +
                    accountId + ". New balance: " + 0);
            account.setMoneyAmount(allMoneyAmount);
        } else {
            allMoneyAmount = allMoneyAmount - withdrawMoney;
            System.out.println("Withdrew " + withdrawMoney + " to account " +
                    accountId + ". New balance: " + allMoneyAmount);
            account.setMoneyAmount(allMoneyAmount);
        }
    }

    public void transferMoney(int fromAccountId, int toAccountId, int transferMoney) {
        Account fromAccount = accountService.getAccountById(fromAccountId);
        Account toAccount = accountService.getAccountById(toAccountId);
        double commission = accountService.getCommission();
        double transferMoneyWithCommission = transferMoney * (1 - commission);
        String aboutTransfer = "";

        int allMoneyAmount = fromAccount.getMoneyAmount();
        if (transferMoney > allMoneyAmount) {
            System.out.println("Not enough money to transfer from " + fromAccountId + " to " + toAccountId);
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
    }

}


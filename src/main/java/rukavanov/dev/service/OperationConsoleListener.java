package rukavanov.dev.service;

import org.springframework.stereotype.Service;
import rukavanov.dev.model.Account;
import rukavanov.dev.model.User;

@Service
public class OperationConsoleListener {

    private final UserService userService;
    private final AccountService accountService;

    public OperationConsoleListener(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void createUser(String login) {
        userService.createUser(login);
        accountService.createAccount(login);
        System.out.println("User created:");
        System.out.println(userService.getUserByLogin(login));
    }

    public void createAccount(int userId) {
        User user = userService.getUserById(userId);
        Account account = accountService.createAccount(user.getLogin());
        System.out.println("Account created:");
        System.out.println(account);
    }

    public void getAllUsers() {
        for (int i = 0; i < userService.getUsers().size(); i++) {
            System.out.println(userService.getUsers().get(i));
        }
    }

    public void depositMoney(int accountId, int depositMoney) {
        int allMoneyAmount = accountService.depositMoney(accountId, depositMoney);
        System.out.println("Deposited " + depositMoney + " to account " +
                accountId + ". New balance: " + allMoneyAmount);
    }

    public void withdrawMoney(int accountId, int withdrawMoney) {
        int allMoneyAmount = accountService.getAllMoneyAmount();
        if (withdrawMoney > allMoneyAmount) {
            System.out.println("Withdrew " + allMoneyAmount + " to account " +
                    accountId + ". New balance: " + 0);
        } else {
            allMoneyAmount = accountService.withdrawMoney(accountId, withdrawMoney);
            System.out.println("Withdrew " + withdrawMoney + " to account " +
                    accountId + ". New balance: " + allMoneyAmount);
        }

    }

    public void transferMoney(int fromAccountId,
                              int toAccountId, int transferMoney) {

        String aboutCommission = accountService.transferMoney(fromAccountId, toAccountId, transferMoney);

        if (aboutCommission.isEmpty()) {
            System.out.println("Not enough money to transfer from " + fromAccountId + " to " + toAccountId);
        } else {
            System.out.println("Transfer completed from " + fromAccountId + " to "
                    + toAccountId + ". Transfer amount is " + transferMoney + " "
                    + aboutCommission);
        }
    }


}


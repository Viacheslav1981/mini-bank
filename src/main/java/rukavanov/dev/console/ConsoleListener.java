package rukavanov.dev.console;

import org.springframework.stereotype.Component;
import rukavanov.dev.model.Account;
import rukavanov.dev.model.Command;
import rukavanov.dev.model.User;
import rukavanov.dev.service.AccountService;
import rukavanov.dev.service.OperationConsoleListener;
import rukavanov.dev.service.UserService;

import java.util.Scanner;

@Component
public class ConsoleListener {

    private final OperationConsoleListener operationConsoleListener;

    private int accountId;
    private int depositMoney;
    private int withdrawMoney;

    public ConsoleListener(OperationConsoleListener operationConsoleListener) {
        this.operationConsoleListener = operationConsoleListener;
    }

    public void start() {
        System.out.println("MiniBank started. Type EXIT to stop.");
        System.out.println("Available commands: ");

        for (Command command : Command.values()) {
            System.out.println("- " + command);
        }
        System.out.println();

        try {
            while (true) {
                System.out.println("Enter command: ");
                Scanner scanner = new Scanner(System.in);
                Command command = Command.valueOf(scanner.nextLine().strip().toUpperCase());

                switch (command) {
                    case USER_CREATE:
                        System.out.println("Enter login: ");
                        String login = scanner.nextLine();
                        operationConsoleListener.createUser(login);
                        break;
                    case ACCOUNT_CREATE:
                        System.out.println("Enter user id: ");
                        int userId = scanner.nextInt();
                        operationConsoleListener.createAccount(userId);
                        break;
                    case SHOW_ALL_USERS:
                        System.out.println("All users:");
                        operationConsoleListener.getAllUsers();
                        break;
                    case ACCOUNT_DEPOSIT:
                        System.out.println("Enter account id: ");
                        accountId = scanner.nextInt();
                        System.out.println("Enter amount to deposit: ");
                        depositMoney = scanner.nextInt();
                        operationConsoleListener.depositMoney(accountId, depositMoney);
                        break;
                    case ACCOUNT_WITHDRAW:
                        System.out.println("Enter account id: ");
                        accountId = scanner.nextInt();
                        System.out.println("Enter amount to withdraw: ");
                        withdrawMoney = scanner.nextInt();
                        operationConsoleListener.withdrawMoney(accountId, withdrawMoney);
                        break;

                    case EXIT:
                        return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command. Please restart application.");

        }

    }


}




package rukavanov.dev.console;

import org.springframework.stereotype.Component;
import rukavanov.dev.model.Command;
import rukavanov.dev.service.OperationService;

import java.util.Scanner;

@Component
public class ConsoleListener {

    private final OperationService operationService;

    private int accountId;
    private int depositMoney;
    private int withdrawMoney;
    private int transferMoney;
    private int fromAccountId;
    private int toAccountId;

    public ConsoleListener(OperationService operationService) {
        this.operationService = operationService;
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
                        operationService.createUser(login);
                        break;
                    case ACCOUNT_CREATE:
                        System.out.println("Enter user id: ");
                        int userId = scanner.nextInt();
                        operationService.createAccount(userId);
                        break;
                    case SHOW_ALL_USERS:
                        operationService.getAllUsers();
                        break;
                    case ACCOUNT_DEPOSIT:
                        System.out.println("Enter account id: ");
                        accountId = scanner.nextInt();
                        System.out.println("Enter amount to deposit: ");
                        depositMoney = scanner.nextInt();
                        operationService.depositMoney(accountId, depositMoney);
                        break;
                    case ACCOUNT_WITHDRAW:
                        System.out.println("Enter account id: ");
                        accountId = scanner.nextInt();
                        System.out.println("Enter amount to withdraw: ");
                        withdrawMoney = scanner.nextInt();
                        operationService.withdrawMoney(accountId, withdrawMoney);
                        break;
                    case ACCOUNT_TRANSFER:
                        System.out.println("Enter source account id: ");
                        fromAccountId = scanner.nextInt();
                        System.out.println("Enter destination account id: ");
                        toAccountId = scanner.nextInt();
                        System.out.println("Enter amount to transfer: ");
                        transferMoney = scanner.nextInt();
                        operationService.transferMoney(fromAccountId, toAccountId, transferMoney);
                        break;
                    case EXIT:
                        System.out.println("MiniBank stopped.");
                        return;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command. Please restart application.");

        }

    }


}




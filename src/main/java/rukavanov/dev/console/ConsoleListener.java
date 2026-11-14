package rukavanov.dev.console;

import rukavanov.dev.model.Command;
import rukavanov.dev.service.UserService;

import java.util.Scanner;

public class ConsoleListener {

    private final UserService userService;

    public ConsoleListener(UserService userService) {
        this.userService = userService;
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
                        userService.createUser(login);
                        System.out.println("User created:");
                        System.out.println(userService.getUserByLogin(login));
                        break;
                    case EXIT:
                        System.exit(0);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid command. Please restart application.");

        }

    }


}




package rukavanov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rukavanov.dev.config.AppConfig;
import rukavanov.dev.console.ConsoleListener;
import rukavanov.dev.service.AccountService;
import rukavanov.dev.service.OperationConsoleListener;
import rukavanov.dev.service.UserService;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationConsoleListener operationConsoleListener = context.getBean(OperationConsoleListener.class);

        ConsoleListener consoleListener = new ConsoleListener(operationConsoleListener);
        consoleListener.start();


    }
}

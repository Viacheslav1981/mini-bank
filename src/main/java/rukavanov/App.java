package rukavanov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import rukavanov.dev.config.AppConfig;
import rukavanov.dev.console.ConsoleListener;
import rukavanov.dev.service.OperationService;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationService operationService = context.getBean(OperationService.class);

        ConsoleListener consoleListener = new ConsoleListener(operationService);
        consoleListener.start();

    }
}

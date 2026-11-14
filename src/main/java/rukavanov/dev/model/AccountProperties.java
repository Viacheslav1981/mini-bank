package rukavanov.dev.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {

    private final int moneyAmount;
    private final double commission;

    public AccountProperties(
            @Value("${account.default-amount}") int moneyAmount,
            @Value("${account.transfer-commission}") double commission) {
        this.moneyAmount = moneyAmount;
        this.commission = commission;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public double getCommission() {
        return commission;
    }
}

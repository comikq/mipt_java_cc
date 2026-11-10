import java.math.BigDecimal;
import java.math.RoundingMode;


public interface WithdrawalOperations {

    BigDecimal withdraw(BigDecimal balance, BigDecimal amount, BankType bank);

    default BigDecimal applyCommission(BigDecimal amount, BankType bank) {
        if (amount == null || bank == null) {
            return new BigDecimal("0.00");
        } else {
            return (amount.multiply(bank.getCommission())).setScale(2, RoundingMode.HALF_UP);
        }
    }
}

import java.math.BigDecimal;

public class CashMachine implements WithdrawalOperations, DepositOperations {
    @Override
    public BigDecimal deposit(BigDecimal balance, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            return balance;
        } else {
            return balance.add(amount);
        }
    }

    @Override
    public BigDecimal withdraw(BigDecimal balance, BigDecimal amount, BankType bank) {
        if (amount == null || amount.signum() <= 0) {
            return balance;
        } else {
            BigDecimal commission = applyCommission(amount, bank);
            BigDecimal fullAmount = amount.add(commission);
            if (fullAmount.compareTo(balance) > 0) {
                System.out.println("Недостаточно средств на балансе");
                return balance;
            } else {
                return balance.subtract(fullAmount);
            }
        }

    }
}
import java.math.BigDecimal;

public class Account {
    int cardnumber;
    int PIN;
    BigDecimal balance;
    BankType bank;

    Account(int cardnumber, int PIN, BigDecimal balance, BankType bank) {
        if (!(10000 <= cardnumber && cardnumber <= 99999)) {
            throw new IllegalArgumentException("Номер карты должен состоять из 5 цифр");
        } else if (!(100 <= PIN && PIN <= 999)) {
            throw new IllegalArgumentException("ПИН-код должен состоять из 3 цифр");
        } else if (balance == null || balance.signum() < 0) {
            throw new IllegalArgumentException("Некорректный баланс");
        } else {
            if (bank == null) {
                this.bank = BankType.NEO;

            } else {
                this.bank = bank;
            }
            this.PIN = PIN;
            this.cardnumber = cardnumber;
            this.balance = balance;
        }
    }

    public int getCardnumber() { return cardnumber; }
    public int getPIN() { return PIN; }
    public BigDecimal getBalance() { return balance; }
    public BankType getBank() { return bank; }
    public String toString() {return bank.getName() + " Карта:" + cardnumber + " Баланс:" + balance; }
}

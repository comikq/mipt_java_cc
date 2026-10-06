import java.util.Scanner;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int card;
        int PIN;
        BigDecimal amount;

        Account account = new Account(67067,607, new BigDecimal("10000.00"), BankType.NEO );

        System.out.println("Здравствуйте!");
        while (true) {
            System.out.println("Введите номер вашей карты:");
            if (!sc.hasNextInt()) {
                System.out.println("Неверный формат ввода");
                sc.next();
            } else {
                card = sc.nextInt();
                break;
            }
        }

        while (true) {
            System.out.println("Введите ваш PIN-код:");
            if (!sc.hasNextInt()) {
                System.out.println("Неверный формат ввода");
                sc.next();
            } else {
                PIN = sc.nextInt();
                break;
            }
        }

        if (card != account.getCardnumber() || PIN != account.getPIN()) {
            System.out.println("Номер карты или PIN-код неверны");
        } else {
            CashMachine cashMachine = new CashMachine();
            while (true) {
                System.out.println("Введите сумму вноса:");
                if (!sc.hasNextBigDecimal()) {
                    System.out.println("Неверный формат ввода");
                    sc.next();
                } else {
                    amount = sc.nextBigDecimal();
                    if (amount.scale() > 2) {
                        System.out.println("Допускается не более двух знаков после запятой");
                    } else {
                        break;
                    }
                }
            }

            account.balance = cashMachine.deposit(account.getBalance(),amount);
            System.out.println("Ваш новый баланс: " + account.getBalance());

            while (true) {
                System.out.println("Введите сумму снятия:");
                if (!sc.hasNextBigDecimal()) {
                    System.out.println("Неверный формат ввода");
                    sc.next();
                } else {
                    amount = sc.nextBigDecimal();
                    if (amount.scale() > 2) {
                        System.out.println("Допускается не более двух знаков после запятой");
                    } else {
                        break;
                    }
                }
            }

            account.balance = cashMachine.withdraw(account.getBalance(), amount, account.getBank());
            System.out.println("Ваш новый баланс: " + account.getBalance());
        }


    }

}

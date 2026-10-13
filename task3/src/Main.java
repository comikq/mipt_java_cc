import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hero[] fighters = new Hero[2];
        System.out.println("Добро пожаловать!");
        for (int i = 1; i <= 2; i++) {
            while (fighters[i - 1] == null) {
                try {
                    System.out.println("Введите класс героя номер " + i + ": 1 - Воин, 2 - Маг");

                    int heroType = sc.nextInt();

                    if (heroType == 1) {
                        System.out.println("Введите параметры вашего персонажа через пробел: Имя, Максимальное здоровье, Базовую атаку, Броню");
                        fighters[i - 1] = new Warrior(sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt());
                    } else if (heroType == 2) {
                        System.out.println("Введите параметры вашего персонажа через пробел: Имя, Максимальное здоровье, Базовую атаку, Максимальную ману");
                        fighters[i - 1] = new Mage(sc.next(), sc.nextInt(), sc.nextInt(), sc.nextInt());

                    } else {
                        System.out.println("Неверный тип персонажа");
                    }
                } catch (IllegalArgumentException | InputMismatchException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    sc.nextLine();
                }
            }
        }

        sc.close();

        Arena arena = new Arena(fighters[0], fighters[1]);

        arena.startTournament();
    }
}
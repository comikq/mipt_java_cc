public class Arena {

    private final Hero fighter1;
    private final Hero fighter2;
    private int roundCounter = 0;

    public Arena(Hero fighter1, Hero fighter2) {
        if (fighter1 == null || fighter2 == null) {
            throw new NullPointerException("Переданы не все герои!");
        }
        if (fighter1 == fighter2) {
            throw new IllegalArgumentException("Нельзя драться с самим собой");
        }
        this.fighter1 = fighter1;
        this.fighter2 = fighter2;
    }

    public void startTournament() {
        while (fighter1.isAlive() && fighter2.isAlive()) {
            roundCounter += 1;
            System.out.println("Раунд " + roundCounter);
            Hero attacker;
            Hero defender;
            if (Math.random() < 0.5) {
                attacker = fighter1;
                defender = fighter2;
            } else {
                attacker = fighter2;
                defender = fighter1;
            }

            ActionType action = attacker.makeTurn(defender);
            System.out.println("Игрок " + attacker.getName() + " совершил действие: " + action.getDescription());

            if (!defender.isAlive()) {
                break;
            }
            action = defender.makeTurn(attacker);
            System.out.println("Игрок " + defender.getName() + " совершил действие: " + action.getDescription());

            System.out.println(fighter1);
            System.out.println(fighter2);
        }
        Hero winner;
        if (fighter1.isAlive()) {
            winner = fighter1;
        } else {
            winner = fighter2;
        }
        System.out.println("Победитель: " + winner.getName() + "!");
    }
}

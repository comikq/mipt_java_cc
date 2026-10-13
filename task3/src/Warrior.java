public class Warrior extends Hero implements Castable, Restable {

    private final int armor;

    public Warrior(String name, int maxHealth, int baseAttack, int armor) {
        super(name, maxHealth, baseAttack);
        if (armor < MIN_STAT_VALUE) {
            throw new IllegalArgumentException("Некорректные данные создания героя");
        }
        this.armor = armor;
    }

    @Override
    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Отрицательное значение урона");
        }
        if (damage > armor) {
            damage -= armor;
        } else {
            damage = 1;
        }
        super.takeDamage(damage);
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            throw new NullPointerException("Пустой параметр цели");
        }
        System.out.println(getName() + " наносит удар!");
        target.takeDamage(getBaseAttack());
    }

    @Override
    public ActionType makeTurn(Hero target) {
        if (target == null) {
            throw new NullPointerException("Пустой параметр цели");
        }
        if (canCast()) {
            castSpecialSkill(target);
            return ActionType.SPECIAL_SKILL;
        } else if (needsRest()) {
            rest();
            return ActionType.REST;
        } else {
            attack(target);
            return ActionType.BASE_ATTACK;
        }
    }

    @Override
    public boolean canCast() {
        return ( (long) getHealth() * 2 < getMaxHealth());
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            throw new NullPointerException("Пустой параметр цели");
        }
        int damage = getBaseAttack() + armor * 2;
        if (canCast()) {
            System.out.println(getName() + " совершает удар щитом!");
            target.takeDamage(damage);
        } else {
            System.out.println(getName() + " попытался ударить щитом, но потерял равновесие...");
        }
    }

    @Override
    public boolean needsRest() {
        return ((long) getHealth() * 100 < (long) getMaxHealth() * 15);
    }

    @Override
    public void rest() {
        int healed = getHealth();
        heal(armor * 2);
        healed = getHealth() - healed;
        System.out.println(getName() + " восстановил " + healed + " здоровья!");
    }

    @Override
    public String toString() {
        return (getName() + ": Здоровье: " + getHealth());
    }
}

public class Mage extends Hero implements Castable, Restable {

    private final int maxMana;
    private int mana;

    public Mage(String name, int maxHealth, int baseAttack, int maxMana) {
        super(name, maxHealth, baseAttack);
        if (maxMana < MIN_STAT_VALUE) {
            throw new IllegalArgumentException("Некорректные данные создания героя");
        }
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    @Override
    public void attack(Hero target) {
        if (target == null) {
            throw new NullPointerException("Пустой параметр цели");
        }
        if (mana >= 10) {
            int damage = getBaseAttack() * 2;
            System.out.println(getName() + " использует заклинание \"Магический выстрел\"!");
            mana -= 10;
            target.takeDamage(damage);
        } else {
            int damage = getBaseAttack() / 2;
            System.out.println(getName() + " ударяет противника посохом и восстанавливает 5 единиц маны!");
            target.takeDamage(damage);
            if ((long) mana + 5 > maxMana) {
                mana = maxMana;
            } else {
                mana += 5;
            }
        }
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
        return mana >= 25;
    }

    @Override
    public void castSpecialSkill(Hero target) {
        if (target == null) {
            throw new NullPointerException("Пустой параметр цели");
        }
        if (canCast()) {
            int damage = getBaseAttack() * 3;
            mana -= 25;
            System.out.println(getName() + " использует заклинание \"Огненная глыба\"!");
            target.takeDamage(damage);
        } else {
            System.out.println(getName() + " попытался использовать заклинание \"Огненная глыба\", но у него заплелся язык...");
        }
    }

    @Override
    public boolean needsRest() { return (mana == 0 || (long) getHealth() * 100 < (long) getMaxHealth() * 30); }

    @Override
    public void rest() {
        mana = maxMana;
        System.out.println(getName() + " восстановил ману!");
        int healed = getHealth();
        heal(getBaseAttack());
        healed = getHealth() - healed;
        System.out.println(getName() + " восстановил " + healed + " здоровья!");
    }

    @Override
    public String toString() {
        return (getName() + ": Здоровье: " + getHealth() + ", Мана: " + mana);
    }
}

public abstract class Hero {
    protected static final int MIN_STAT_VALUE = 1;
    private final String name;
    private final int maxHealth;
    private final int baseAttack;
    private int health;

    public Hero(String name, int maxHealth, int baseAttack) {
        if (maxHealth < MIN_STAT_VALUE || baseAttack < MIN_STAT_VALUE || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Некорректные данные создания героя");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.baseAttack = baseAttack;
        this.health = maxHealth;
    }

    public String getName() { return name; }
    public int getMaxHealth() { return maxHealth; }
    public int getBaseAttack() { return baseAttack; }
    public int getHealth() { return health; }

    public abstract void attack(Hero target);
    public abstract ActionType makeTurn(Hero target);

    public void takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Отрицательное значение урона");
        }
        if (health > damage) {
            System.out.println(name + " получил " + damage + " урона!");
            health -= damage;
        } else {
            health = 0;
            System.out.println(name + " пал в бою!");
        }
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Отрицательное значение лечения");
        }
        if ((long) health + (long) amount <= maxHealth) {
            health += amount;
        } else {
            health = maxHealth;
        }
    }

    public boolean isAlive() { return (health > 0); }

}
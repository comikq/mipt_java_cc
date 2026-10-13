public enum ActionType {
    BASE_ATTACK("Базовая атака"),
    SPECIAL_SKILL("Особое умение"),
    REST("Пропуск хода");

    private final String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
}

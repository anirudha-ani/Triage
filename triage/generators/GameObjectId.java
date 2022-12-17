package triage.generators;

public enum GameObjectId {
    BACKGROUND,
    HIDDEN_RECTANGLE_HITBOX,
    player, // Intentionally lower case to make it compatible with legacy code

    SAMURAI,
    HUNTRESS,

    WIZARD,
    WARRIOR,
    START_BUTTON, // Carefull to include either "BUTTON" or "button" in the id of a Button
    LOAD_BUTTON,
    SETTINGS_BUTTON,

    SHOPPING_BUTTON,
    TEXT,

    IMAGE,
    BULLET,
}

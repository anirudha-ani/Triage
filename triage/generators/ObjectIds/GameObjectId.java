package triage.generators.ObjectIds;

public enum GameObjectId {
    BACKGROUND,
    HIDDEN_RECTANGLE_HITBOX,
    player, // Intentionally lower case to make it compatible with legacy code
    GROUND_SENTRY,

    AIR_SENTRY,

    SAMURAI,
    HUNTRESS,

    WIZARD,
    WARRIOR,
    MUMMY,
    START_BUTTON, // Carefull to include either "BUTTON" or "button" in the id of a Button
    LOAD_BUTTON,
    SETTINGS_BUTTON,

    SHOPPING_BUTTON,

    PAUSE_BUTTON,

    EXIT_BUTTON,

    USE_BUTTON,
    UNLOCK_BUTTON,
    TEXT,

    IMAGE,
    BULLET,
}

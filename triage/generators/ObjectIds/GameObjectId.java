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
    SHURIKEN,
    START_BUTTON, // Carefull to include either "BUTTON" or "button" in the id of a Button
    LOAD_BUTTON,
    SETTINGS_BUTTON,

    SHOPPING_BUTTON,

    PAUSE_BUTTON,

    EXIT_BUTTON,

    USEITEM_BUTTON,
    UNLOCK_BUTTON,

    LEVEL1_BUTTON,
    LEVEL2_BUTTON,
    TEXT,

    IMAGE,
    BULLET,
}

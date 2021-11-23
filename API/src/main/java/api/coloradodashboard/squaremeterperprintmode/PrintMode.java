package api.coloradodashboard.squaremeterperprintmode;

/**
 * Enumeration of all print modes.
 */
public enum PrintMode {
    MAX_SPEED("Max speed"),
    HIGH_SPEED("High speed"),
    PRODUCTION("Production"),
    HIGH_QUALITY("High quality"),
    SPECIALTY("Specialty"),
    BACKLIT("Backlit"),
    RELIANCE("Reliance"),
    OTHER("Other");

    private String displayName;

    PrintMode(String displayName) { this.displayName = displayName; }

    public String displayName() { return displayName; }

    @Override public String toString() { return displayName; }
}

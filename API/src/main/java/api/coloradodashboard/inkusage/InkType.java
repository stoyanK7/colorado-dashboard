package api.coloradodashboard.inkusage;

/**
 * Enumeration of all ink types.
 */
public enum InkType {
    CYAN("Cyan"),
    MAGENTA("Magenta"),
    YELLOW("Yellow"),
    BLACK("Black");

    private String displayName;

    InkType(String displayName) { this.displayName = displayName; }

    public String displayName() { return displayName; }

    @Override public String toString() { return displayName; }
}

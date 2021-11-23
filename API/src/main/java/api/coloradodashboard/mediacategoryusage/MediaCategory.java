package api.coloradodashboard.mediacategoryusage;

/**
 * Enumeration of all media categories.
 */
public enum MediaCategory {
    FILM("Film"),
    LIGHT_PAPER("Light paper"),
    HEAVY_PAPER("Heavy paper"),
    LIGHT_BANNER("Light banner"),
    TEXTILE("Textile"),
    MONOMERIC_VINYL("Monomeric vinyl"),
    CANVAS("Canvas"),
    POLYMERIC_AND_CAST_VINYL("Polymeric and cast vinyl"),
    HEAVY_BANNER("Heavy banner"),
    PAPER("Paper"),
    THICK_FILM("Thick film");

    private String displayName;

    MediaCategory(String displayName) { this.displayName = displayName; }

    public String displayName() { return displayName; }

    @Override public String toString() { return displayName; }
}

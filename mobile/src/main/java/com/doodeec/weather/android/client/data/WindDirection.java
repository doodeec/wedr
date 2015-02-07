package com.doodeec.weather.android.client.data;

/**
 * Enum for expressing wind direction with 16-point compass
 * http://mapzone.ordnancesurvey.co.uk/mapzone/PagesHomeworkHelp/docs/mapabilitycompassesanddirections.pdf
 *
 * @author Dusan Bartos
 */
public enum WindDirection {
    North("N"),
    NorthNorthEast("NNE"),
    NorthEast("NE"),
    EastNorthEast("ENE"),
    East("E"),
    EastSouthEast("ESE"),
    SouthEast("SE"),
    SouthSouthEast("SSE"),
    South("S"),
    SouthSouthWest("SSW"),
    SouthWest("SW"),
    WestSouthWest("WSW"),
    West("W"),
    WestNorthWest("WNW"),
    NorthWest("NW"),
    NorthNorthWest("NNW");

    private String abbreviation;

    private WindDirection(String abbr) {
        this.abbreviation = abbr;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Static method for obtaining enum value from its abbreviated string value
     *
     * @param abbr abbreviation
     * @return enum value
     */
    public static WindDirection forAbbreviation(String abbr) {
        if (abbr == null) return null;
        for (WindDirection direction : values()) {
            if (direction.abbreviation.equals(abbr)) return direction;
        }
        return null;
    }
}

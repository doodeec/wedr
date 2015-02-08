package com.doodeec.weather.android.util;

import android.content.SharedPreferences;

import com.doodeec.weather.android.WedrApplication;

/**
 * Helper wrapper around shared preferences and their enums
 *
 * @author Dusan Bartos
 */
public class WedrPreferences {

    public static final String PREFERENCE_LENGTH_UNIT = "length_unit";
    public static final String PREFERENCE_TEMP_UNIT = "temperature_unit";

    /**
     * Preference enum for displaying length units
     */
    public enum LengthUnit {
        Meter("Meters"),
        Mile("Miles");

        private String mValue;

        private LengthUnit(String value) {
            mValue = value;
        }

        public static LengthUnit forValue(String value) {
            if (value == null) return null;
            for (LengthUnit unit : values()) {
                if (unit.mValue.equals(value)) return unit;
            }
            return null;
        }
    }

    /**
     * Preference enum for displaying temperature units
     */
    public enum TemperatureUnit {
        Celsius("Celsius"),
        Fahrenheit("Fahrenheit");

        private String mValue;

        private TemperatureUnit(String value) {
            mValue = value;
        }

        public static TemperatureUnit forValue(String value) {
            if (value == null) return null;
            for (TemperatureUnit unit : values()) {
                if (unit.mValue.equals(value)) return unit;
            }
            return null;
        }
    }

    /**
     * Reads length unit preference and translates it to enum
     *
     * @return length unit enum
     */
    public static LengthUnit getLengthUnit() {
        SharedPreferences preferences = WedrApplication.getDefaultSharedPreferences();
        return LengthUnit.forValue(preferences.getString(PREFERENCE_LENGTH_UNIT, ""));
    }

    /**
     * Reads temperature unit preference and translates it to enum
     *
     * @return temp unit enum
     */
    public static TemperatureUnit getTemperatureUnit() {
        SharedPreferences preferences = WedrApplication.getDefaultSharedPreferences();
        return TemperatureUnit.forValue(preferences.getString(PREFERENCE_TEMP_UNIT, ""));
    }
}

package com.doodeec.weather.android.database.model;

import android.content.ContentValues;

/**
 * Interface which serves to unite objects which can be saved to DB
 *
 * @author Dusan Bartos
 */
public interface IDatabaseSavable {
    /**
     * Gets content values to be saved to DB
     *
     * @return content values
     */
    ContentValues getContentValues();

    /**
     * Gets table name which content should be saved to
     *
     * @return table name
     */
    String getTableName();

    /**
     * Gets name of column which should be used to find if object is saved already
     * - to distinguish if insert/update should be used for the operation
     *
     * @return primary column name
     */
    String getPrimaryColumnName();

    /**
     * Gets value for primary column for this object
     * Useful for updates
     *
     * @return value of primary column attribute
     */
    String getPrimaryColumnValue();
}

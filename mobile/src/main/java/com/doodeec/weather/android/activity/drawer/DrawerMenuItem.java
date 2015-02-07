package com.doodeec.weather.android.activity.drawer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Simple class for holding drawer menu item objects
 *
 * @author Dusan Bartos
 */
public class DrawerMenuItem {
    private int mTitleResource;
    private int mIconResource;

    public DrawerMenuItem(@StringRes int titleRes, @DrawableRes int iconRes) {
        mTitleResource = titleRes;
        mIconResource = iconRes;
    }

    public int getTitleResource() {
        return mTitleResource;
    }

    public int getIconResource() {
        return mIconResource;
    }
}

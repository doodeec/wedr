package com.doodeec.weather.android.activity.drawer;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodeec.weather.android.R;

/**
 * Drawer view holder provides recyclable view holder according viewHolder pattern to custom drawer items
 *
 * @author Dusan Bartos
 */
public class DrawerViewHolder extends RecyclerView.ViewHolder {

    TextView mItemName;
    ImageView mItemIcon;

    public DrawerViewHolder(View v) {
        super(v);

        mItemIcon = (ImageView) v.findViewById(R.id.menu_item_icon);
        mItemName = (TextView) v.findViewById(R.id.menu_item_label);
    }

    public void setLabel(@StringRes int labelRes) {
        mItemName.setText(labelRes);
    }

    public void setIcon(@DrawableRes int iconRes) {
        mItemIcon.setImageResource(iconRes);
    }
}

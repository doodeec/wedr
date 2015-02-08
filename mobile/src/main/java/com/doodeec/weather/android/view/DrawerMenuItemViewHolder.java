package com.doodeec.weather.android.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doodeec.weather.android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Drawer view holder provides recyclable view holder according viewHolder pattern to custom drawer items
 *
 * @author Dusan Bartos
 */
public class DrawerMenuItemViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.menu_item_icon)
    ImageView mItemIcon;
    @InjectView(R.id.menu_item_label)
    TextView mItemName;

    public DrawerMenuItemViewHolder(View v) {
        super(v);

        ButterKnife.inject(this, v);
        
        if (mItemIcon == null || mItemName == null) {
            throw new AssertionError("Drawer menu item view holder has invalid layout");
            
        }
    }

    public void setLabel(@StringRes int labelRes) {
        mItemName.setText(labelRes);
    }

    public void setIcon(@DrawableRes int iconRes) {
        mItemIcon.setImageResource(iconRes);
    }
}

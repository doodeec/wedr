package com.doodeec.weather.android.activity.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.weather.android.R;

/**
 * @author Dusan Bartos
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerViewHolder> {

    DrawerMenuItem[] mMenuItems;
    LayoutInflater mInflater;

    public DrawerAdapter(Context context, DrawerMenuItem[] items) {
        mInflater = LayoutInflater.from(context);
        if (items != null) {
            mMenuItems = items;
        } else {
            mMenuItems = new DrawerMenuItem[0];
        }
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerViewHolder(mInflater.inflate(R.layout.drawer_item_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        DrawerMenuItem menuItem = mMenuItems[position];

        holder.setIcon(menuItem.getIconResource());
        holder.setLabel(menuItem.getTitleResource());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.length;
    }
}

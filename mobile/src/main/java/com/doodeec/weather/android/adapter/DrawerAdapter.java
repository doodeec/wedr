package com.doodeec.weather.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.activity.drawer.DrawerMenuItem;
import com.doodeec.weather.android.view.DrawerMenuItemViewHolder;

/**
 * Adapter for drawer menu items
 *
 * @author Dusan Bartos
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerMenuItemViewHolder> {

    private DrawerMenuItem[] mMenuItems;
    private LayoutInflater mInflater;

    public DrawerAdapter(Context context, DrawerMenuItem[] items) {
        mInflater = LayoutInflater.from(context);
        if (items != null) {
            mMenuItems = items;
        } else {
            mMenuItems = new DrawerMenuItem[0];
        }
    }

    @Override
    public DrawerMenuItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerMenuItemViewHolder(mInflater.inflate(R.layout.drawer_item_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(DrawerMenuItemViewHolder holder, int position) {
        DrawerMenuItem menuItem = mMenuItems[position];

        holder.setIcon(menuItem.getIconResource());
        holder.setLabel(menuItem.getTitleResource());
    }

    @Override
    public int getItemCount() {
        return mMenuItems.length;
    }

    public DrawerMenuItem getItem(int position) {
        return mMenuItems[position];
    }
}

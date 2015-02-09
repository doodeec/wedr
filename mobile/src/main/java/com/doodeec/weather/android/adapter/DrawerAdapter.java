package com.doodeec.weather.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.doodeec.weather.android.R;
import com.doodeec.weather.android.activity.drawer.DrawerMenuItem;
import com.doodeec.weather.android.view.DrawerMenuFooterViewHolder;
import com.doodeec.weather.android.view.DrawerMenuItemViewHolder;

/**
 * Adapter for drawer menu items
 *
 * @author Dusan Bartos
 */
public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new DrawerMenuItemViewHolder(
                    mInflater.inflate(R.layout.drawer_item_view_holder, parent, false));
        } else {
            return new DrawerMenuFooterViewHolder(
                    mInflater.inflate(R.layout.drawer_footer_view_holder, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrawerMenuItemViewHolder) {
            DrawerMenuItem menuItem = mMenuItems[position];
            DrawerMenuItemViewHolder viewHolder = (DrawerMenuItemViewHolder) holder;

            viewHolder.setIcon(menuItem.getIconResource());
            viewHolder.setLabel(menuItem.getTitleResource());
        }
    }

    @Override
    public int getItemCount() {
        return mMenuItems.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position == mMenuItems.length - 1 ? TYPE_FOOTER : TYPE_ITEM;
    }

    public DrawerMenuItem getItem(int position) {
        return mMenuItems[position];
    }
}

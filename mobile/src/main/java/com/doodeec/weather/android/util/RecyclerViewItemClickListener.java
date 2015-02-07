package com.doodeec.weather.android.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Since recycler view does not have default item click listener, this is a little helper which
 * makes using item click and item long tap possible
 * https://gist.github.com/doodeec/2d863c7e309792f99728
 */
public class RecyclerViewItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        /**
         * Fires when recycler view receives a single tap event on any item
         *
         * @param view     tapped view
         * @param position item position in the list
         */
        public void onItemClick(View view, int position);

        /**
         * Fires when recycler view receives a long tap event on item
         *
         * @param view     long tapped view
         * @param position item position in the list
         */
        public void onItemLongClick(View view, int position);
    }

    GestureDetector mGestureDetector;
    ExtendedGestureListener mGestureListener;

    public RecyclerViewItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureListener = new ExtendedGestureListener();
        mGestureDetector = new GestureDetector(context, mGestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null) {
            mGestureListener.setHelpers(childView, view.getChildPosition(childView));
            mGestureDetector.onTouchEvent(e);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    /**
     * Extended Gesture listener react for both item clicks and item long clicks
     */
    private class ExtendedGestureListener extends GestureDetector.SimpleOnGestureListener {
        private View view;
        private int position;

        public void setHelpers(View view, int position) {
            this.view = view;
            this.position = position;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mListener.onItemClick(view, position);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mListener.onItemLongClick(view, position);
        }
    }
}

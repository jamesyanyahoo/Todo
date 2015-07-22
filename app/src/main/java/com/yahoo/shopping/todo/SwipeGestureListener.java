package com.yahoo.shopping.todo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SwipeGestureListener extends SimpleOnGestureListener {
    private ListView mlistView;
    private int mCurrentPos = -1;

    public SwipeGestureListener(ListView mlistView) {
        this.mlistView = mlistView;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mCurrentPos = mlistView.pointToPosition((int) e2.getX(), (int) e2.getY());
        final View lvItem = mlistView.getChildAt(mCurrentPos);

        if (lvItem != null) { // ensure the lvItem not null
            Context context = lvItem.getContext();
            if (context instanceof OnListItemFlingListener) {
                OnListItemFlingListener listener = (OnListItemFlingListener) context;
                listener.onListItemFling(mCurrentPos);
            }
        }

        return true;
    }

    public int getCurrentPos() {
        return mCurrentPos;
    }
}

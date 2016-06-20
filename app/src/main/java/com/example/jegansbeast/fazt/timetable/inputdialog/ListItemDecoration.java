package com.example.jegansbeast.fazt.timetable.inputdialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by JEGAN'S BEAST on 6/19/2016.
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public ListItemDecoration(Context c,int id){
        mDivider = ContextCompat.getDrawable(c, id);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        int childcount = parent.getChildCount();

        for (int i = 0; i < childcount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

}

package com.example.android.utility;

import android.app.Fragment;
import android.graphics.Point;
import android.support.design.widget.TabLayout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.ViewFlipper;

/**
 * Created by keyurgolani on 3/17/17.
 */

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

    private static int SWIPE_MIN_DISTANCE;
    private static int SWIPE_MAX_OFF_PATH;
    private static int SWIPE_THRESHOLD_VELOCITY = 10;

    TabHost mDetailsTab;

    public MyGestureDetector(TabHost mDetailsTab, Fragment caller) {
        this.mDetailsTab = mDetailsTab;
        Point size = new Point();
        caller.getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        SWIPE_MIN_DISTANCE = width / 4;
        SWIPE_MAX_OFF_PATH = width;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        System.out.println(" in onFling() :: ");
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            if(mDetailsTab.getCurrentTab() == 0) {
                mDetailsTab.setCurrentTab(1);
            }
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            if(mDetailsTab.getCurrentTab() == 1) {
                mDetailsTab.setCurrentTab(0);
            }
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }

}

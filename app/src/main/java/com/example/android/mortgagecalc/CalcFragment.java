package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.android.utility.SimpleGestureFilter;

/**
 * Created by keyurgolani on 3/16/17.
 */

public class CalcFragment extends Fragment implements SimpleGestureFilter.SimpleGestureListener{

    TabHost mDetailsTab;
    private SimpleGestureFilter detector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calc_layout, container, false);
        mDetailsTab = (TabHost) rootView.findViewById(R.id.details_tab);
        mDetailsTab.setup();

        TabHost.TabSpec spec = mDetailsTab.newTabSpec("Loan Info");
        spec.setContent(R.id.loan_tab);
        spec.setIndicator("Loan Info");
        mDetailsTab.addTab(spec);

        spec = mDetailsTab.newTabSpec("Property Info");
        spec.setContent(R.id.property_tab);
        spec.setIndicator("Property Info");
        mDetailsTab.addTab(spec);

        detector = new SimpleGestureFilter(this.getActivity(),this);

        return rootView;
    }

    public void dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                break;

        }
        Toast.makeText(this.getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this.getActivity(), "Double Tap", Toast.LENGTH_SHORT).show();
    }
}

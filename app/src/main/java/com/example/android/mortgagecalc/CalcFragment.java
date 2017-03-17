package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.android.utility.MyGestureDetector;
import com.example.android.utility.SimpleGestureFilter;

/**
 * Created by keyurgolani on 3/16/17.
 */

public class CalcFragment extends Fragment /*implements SimpleGestureFilter.SimpleGestureListener*/{

    TabHost mDetailsTab;
//    private SimpleGestureFilter detector;
    Button mSaveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calc_layout, container, false);
        mDetailsTab = (TabHost) rootView.findViewById(R.id.details_tab);
        mSaveButton = (Button) rootView.findViewById(R.id.save_button);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Do a lot of validation and Save The Mortgage
            }
        });

        mDetailsTab.setup();

        final GestureDetector gestureDetector;
        gestureDetector = new GestureDetector(new MyGestureDetector(mDetailsTab, this));

        TabHost.TabSpec spec = mDetailsTab.newTabSpec("Loan Info");
        spec.setContent(R.id.loan_tab);
        spec.setIndicator("Loan Info");
        mDetailsTab.addTab(spec);

        spec = mDetailsTab.newTabSpec("Property Info");
        spec.setContent(R.id.property_tab);
        spec.setIndicator("Property Info");
        mDetailsTab.addTab(spec);
        mDetailsTab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(gestureDetector.onTouchEvent(event)) {
                    return false;
                } else {
                    return true;
                }
            }
        });

//        detector = new SimpleGestureFilter(this.getActivity(),this);

        return rootView;
    }

//    public void dispatchTouchEvent(MotionEvent me){
//        // Call onTouchEvent of SimpleGestureFilter class
//        this.detector.onTouchEvent(me);
//    }

//    @Override
//    public void onSwipe(int direction) {
//        String str = "";
//
//        switch (direction) {
//
//            case SimpleGestureFilter.SWIPE_RIGHT :
//                if(mDetailsTab.getCurrentTab() == 1) {
//                    mDetailsTab.setCurrentTab(0);
//                }
//                break;
//            case SimpleGestureFilter.SWIPE_LEFT :
//                if(mDetailsTab.getCurrentTab() == 0) {
//                    mDetailsTab.setCurrentTab(1);
//                }
//                break;
//            case SimpleGestureFilter.SWIPE_DOWN :
//                break;
//            case SimpleGestureFilter.SWIPE_UP :
//                break;
//        }
//    }
//
//    @Override
//    public void onDoubleTap() {
//        // Do Nothing
//    }
}

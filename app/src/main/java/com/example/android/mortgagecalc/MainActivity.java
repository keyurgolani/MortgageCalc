package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mCalcFrame;
    private FrameLayout mMyMortFrame;
    private static final int CALC_FRAGMENT = -1;
    private static final int MORT_FRAGMENT = 1;
    FragmentManager fm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calc:
                    setFragment(CALC_FRAGMENT);
                    return true;
                case R.id.navigation_my_mort:
                    setFragment(MORT_FRAGMENT);
                    return true;
            }
            return false;
        }

    };

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent me){
//        ((CalcFragment) fm.findFragmentByTag("active")).dispatchTouchEvent(me);
//        return super.dispatchTouchEvent(me);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        setFragment(0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(int fragment_id) {
        if(fragment_id == CALC_FRAGMENT) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.tab, new CalcFragment(), "active");
            ft.commit();
        } else if(fragment_id == MORT_FRAGMENT) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.tab, new MortFragment(), "active");
            ft.commit();
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.tab, new CalcFragment(), "active");
            ft.commit();
        }
    }

}

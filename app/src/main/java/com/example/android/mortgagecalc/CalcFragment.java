package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.utility.MyGestureDetector;
import com.example.android.utility.SimpleGestureFilter;

import me.grantland.widget.AutofitHelper;

import static android.R.attr.value;

/**
 * Created by keyurgolani on 3/16/17.
 */

public class CalcFragment extends Fragment {

    TabHost mDetailsTab;
    Button mSaveButton;
    Button mClearButton;
    TextView mResultTextView;
    EditText mPropertyPriceEditView;
    EditText mDownPaymentEditView;
    EditText mAPREditView;
    EditText mPeriodEditView;

    EditText mStreet;
    EditText mCity;
    String mState;
    EditText mZip;
    String mType;
    RadioButton mHouseButton;
    RadioButton mTownHouse;
    RadioButton mCondo;




    public void SaveMortgage(){

        Mortgage new_mort = new Mortgage();
        new_mort.setInterest(Double.parseDouble(mAPREditView.getText().toString()));
        new_mort.setDownpayment(Double.parseDouble(mDownPaymentEditView.getText().toString()));
        new_mort.setPeriod(Integer.parseInt(mPeriodEditView.getText().toString()));
        new_mort.setPrice(Double.parseDouble(mPropertyPriceEditView.getText().toString()));
//        new_mort.setMonthlypayment(Double.parseDouble(mResultTextView.getText().toString()));

        new_mort.setStreet(mStreet.getText().toString());
        new_mort.setCity(mCity.getText().toString());
        new_mort.setState(mState);
        new_mort.setType(mType);
        new_mort.setZip(Integer.parseInt(mZip.getText().toString()));

        new_mort.toString();





    }

    public void calculateMortgage() {
        double price = 0.0;
        double downPayment = 0.0;
        double apr = 0.0;
        double period = 0.0;
        if(mPropertyPriceEditView.getText() != null && mPropertyPriceEditView.getText().length() > 0
                && mDownPaymentEditView.getText() != null && mDownPaymentEditView.getText().length() > 0
                && mAPREditView.getText() != null && mAPREditView.getText().length() > 0
                && mPeriodEditView.getText() != null && mPeriodEditView.getText().length() > 0) {
            price = Double.parseDouble(String.valueOf(mPropertyPriceEditView.getText()));
            downPayment = Double.parseDouble(String.valueOf(mDownPaymentEditView.getText()));
            apr = Double.parseDouble(String.valueOf(mAPREditView.getText()));
            period = Double.parseDouble(String.valueOf(mPeriodEditView.getText()));
            double mortgage = (price - downPayment) * (apr / 12) * Math.pow(1 + (apr / 12), (period * 12)) / (Math.pow(1 + (apr / 12), (period * 12)) - 1);
            mResultTextView.setText(String.format("$%.2f", mortgage));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calc_layout, container, false);
        mDetailsTab = (TabHost) rootView.findViewById(R.id.details_tab);
        mSaveButton = (Button) rootView.findViewById(R.id.save_button);
        mClearButton = (Button) rootView.findViewById(R.id.clear_button);
        mResultTextView = (TextView) rootView.findViewById(R.id.mortgage_value);
        mPropertyPriceEditView = (EditText) rootView.findViewById(R.id.price_value);
        mDownPaymentEditView = (EditText) rootView.findViewById(R.id.downpayment_value);
        mAPREditView = (EditText) rootView.findViewById(R.id.apr_value);
        mPeriodEditView = (EditText) rootView.findViewById(R.id.period_value);

        mStreet = (EditText)rootView.findViewById(R.id.street_value);
        mCity = (EditText) rootView.findViewById(R.id.city_value);

        Spinner mySpinner=(Spinner) rootView.findViewById(R.id.state_spinner);
        mState = mySpinner.getSelectedItem().toString();


        mZip = (EditText) rootView.findViewById(R.id.zip_value);

       RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.type_group);
        rg.check(R.id.house_type);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            }

            public void onRadioButtonClicked(View view) {
                // Is the button now checked?
                boolean checked = ((RadioButton) view).isChecked();

                // Check which radio button was clicked
                switch(view.getId()) {
                    case R.id.house_type:
                        if (checked)
                            mType = "House";
                        break;
                    case R.id.townhouse_type:
                        if (checked)
                            mType = "TownHouse";
                        break;
                    case R.id.condo_type:
                        if (checked)
                            mType = "Condo";
                        break;
                }
            }

        });



        mType = ((RadioButton)rootView.findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
        mHouseButton = (RadioButton)rootView.findViewById(R.id.house_type);
        mTownHouse = (RadioButton)rootView.findViewById(R.id.townhouse_type);
        mCondo = (RadioButton)rootView.findViewById(R.id.condo_type);


        AutofitHelper.create(mResultTextView);

        mPeriodEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMortgage();
            }
        });



        mAPREditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMortgage();
            }
        });




        mDownPaymentEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMortgage();
            }
        });




        mPropertyPriceEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMortgage();
            }
        });




        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Do a lot of validation and Save The Mortgage
                SaveMortgage();
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

        return rootView;
    }


}

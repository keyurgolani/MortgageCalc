package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.utility.MyGestureDetector;

import me.grantland.widget.AutofitHelper;

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
    EditText mStreetEditView;
    EditText mCityEditView;
    Spinner mStateSpinner;
    EditText mZipEditView;
    RadioGroup mHouseTypeGroup;
    Mortgage editMortgage = null;

    //private method of your class
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void SaveMortgage() {
        if(mPropertyPriceEditView.getText().toString().equals("") || mPropertyPriceEditView.getText().toString() == null
                || mDownPaymentEditView.getText().toString().equals("") || mDownPaymentEditView.getText().toString() == null
                || mAPREditView.getText().toString().equals("") || mAPREditView.getText().toString() == null
                || mPeriodEditView.getText().toString().equals("") || mPeriodEditView.getText().toString() == null
                || mStreetEditView.getText().toString().equals("") || mStreetEditView.getText().toString() == null
                || mCityEditView.getText().toString().equals("") || mCityEditView.getText().toString() == null
                || mZipEditView.getText().toString().equals("") || mZipEditView.getText().toString() == null) {
            if(mPropertyPriceEditView.getText().toString().equals("") || mPropertyPriceEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(0);
                mPropertyPriceEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Property Price", Toast.LENGTH_SHORT)
                        .show();
            } else if(mDownPaymentEditView.getText().toString().equals("") || mDownPaymentEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(0);
                mDownPaymentEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Down Payment Amount", Toast.LENGTH_SHORT)
                        .show();
            } else if(mAPREditView.getText().toString().equals("") || mAPREditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(0);
                mAPREditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Annual Percent Rate", Toast.LENGTH_SHORT)
                        .show();
            } else if(mPeriodEditView.getText().toString().equals("") || mPeriodEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(0);
                mPeriodEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Loan Period", Toast.LENGTH_SHORT)
                        .show();
            } else if(mStreetEditView.getText().toString().equals("") || mStreetEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(1);
                mStreetEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Street Address", Toast.LENGTH_SHORT)
                        .show();
            } else if(mCityEditView.getText().toString().equals("") || mCityEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(1);
                mCityEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill City", Toast.LENGTH_SHORT)
                        .show();
            } else if(mZipEditView.getText().toString().equals("") || mZipEditView.getText().toString() == null) {
                mDetailsTab.setCurrentTab(1);
                mZipEditView.requestFocus();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Please fill Zip", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            if(editMortgage == null) {
                Mortgage mortgage = new Mortgage();
                mortgage.setInterest(Double.parseDouble(mAPREditView.getText().toString()));
                mortgage.setDownpayment(Double.parseDouble(mDownPaymentEditView.getText().toString()));
                mortgage.setPeriod(Integer.parseInt(mPeriodEditView.getText().toString()));
                mortgage.setPrice(Double.parseDouble(mPropertyPriceEditView.getText().toString()));
                mortgage.setAddress(mStreetEditView.getText().toString());
                mortgage.setCity(mCityEditView.getText().toString());
                mortgage.setState(mStateSpinner.getSelectedItem().toString());

                mortgage.setType(mHouseTypeGroup.getCheckedRadioButtonId());

                mortgage.setZip(Integer.parseInt(mZipEditView.getText().toString()));
                mortgage.validate();
                if(mortgage.isValid()) {
                    new MortgageDAO(this.getActivity()).createMortgage(mortgage);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please make sure to provide valid address!", Toast.LENGTH_LONG)
                            .show();
                }
            } else {
                editMortgage.validate();
                editMortgage.setInterest(Double.parseDouble(mAPREditView.getText().toString()));
                editMortgage.setDownpayment(Double.parseDouble(mDownPaymentEditView.getText().toString()));
                editMortgage.setPeriod(Integer.parseInt(mPeriodEditView.getText().toString()));
                editMortgage.setPrice(Double.parseDouble(mPropertyPriceEditView.getText().toString()));
                editMortgage.setAddress(mStreetEditView.getText().toString());
                editMortgage.setCity(mCityEditView.getText().toString());
                editMortgage.setState(mStateSpinner.getSelectedItem().toString());

                editMortgage.setType(mHouseTypeGroup.getCheckedRadioButtonId());

                editMortgage.setZip(Integer.parseInt(mZipEditView.getText().toString()));
                if(editMortgage.isValid()) {
                    new MortgageDAO(this.getActivity()).updateMortgage(editMortgage);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please make sure to provide valid address!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
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
            double mortgage = (price - downPayment)
                    * (apr / 1200)
                    * Math.pow(1 + (apr / 1200), (period * 12))
                    / (Math.pow(1 + (apr / 1200), (period * 12)) - 1);
            if(Double.isNaN(mortgage) || Double.isInfinite(mortgage)) {
                mResultTextView.setText("∞");
            } else {
                mResultTextView.setText(String.format("$%.2f", mortgage));
            }
        } else {
            mResultTextView.setText("$0.00");
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
        mStreetEditView = (EditText)rootView.findViewById(R.id.street_value);
        mCityEditView = (EditText) rootView.findViewById(R.id.city_value);
        mStateSpinner = (Spinner) rootView.findViewById(R.id.state_spinner);
        mStateSpinner.setSelection(0);
        mZipEditView = (EditText) rootView.findViewById(R.id.zip_value);
        mHouseTypeGroup = (RadioGroup) rootView.findViewById(R.id.type_group);

        Bundle args = getArguments();
        if(args != null) {
            editMortgage = (Mortgage) getArguments().getSerializable("mortgage");
            mPropertyPriceEditView.setText(editMortgage.getPrice()+"");
            mDownPaymentEditView.setText(editMortgage.getDownpayment()+"");
            mAPREditView.setText(editMortgage.getInterest()+"");
            mPeriodEditView.setText(editMortgage.getPeriod()+"");
            mStreetEditView.setText(editMortgage.getAddress());
            mCityEditView.setText(editMortgage.getCity()+"");
            mStateSpinner.setSelection(getIndex(mStateSpinner, editMortgage.getState()));
            mHouseTypeGroup.check(editMortgage.getType());
            Double mortgage = editMortgage.getMortgageAmount();
            if(Double.isNaN(mortgage) || Double.isInfinite(mortgage)) {
                mResultTextView.setText("∞");
            } else {
                mResultTextView.setText(String.format("$%.2f", mortgage));
            }
            mZipEditView.setText(editMortgage.getZip()+"");
        }


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

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMortgage = null;
                mPropertyPriceEditView.setText("");
                mDownPaymentEditView.setText("");
                mAPREditView.setText("");
                mPeriodEditView.setText("");
                mStreetEditView.setText("");
                mCityEditView.setText("");
                mStateSpinner.setSelection(0);
                mZipEditView.setText("");
                mHouseTypeGroup.check(0);
                mResultTextView.setText("$0.00");
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

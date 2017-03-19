package com.example.android.mortgagecalc;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keyurgolani on 3/16/17.
 */

public class MortFragment extends Fragment {

    ListView mMortgageList;
    ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mort_layout, container, false);
        mMortgageList = (ListView) rootView.findViewById(R.id.mortgage_list);
        List<Mortgage> mortgages = new MortgageDAO(this.getActivity()).getAllMortgages();
        List<String> mortgages_list = new ArrayList<>();
        for (int i = 0; i < mortgages.size(); i++) {
            mortgages_list.add(mortgages.get(i).getAddress());
        }
        adapter=new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                mortgages_list);
        mMortgageList.setAdapter(adapter);

        mMortgageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition = position;
                String  itemValue = (String) mMortgageList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getActivity().getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });

        return rootView;
    }
}

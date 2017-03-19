package com.example.android.mortgagecalc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by keyurgolani on 3/16/17.
 */

public class MortFragment extends Fragment {

    ListView mMortgageList;
    ArrayAdapter<String> adapter;
    MapView mMapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mort_layout, container, false);
        mMortgageList = (ListView) rootView.findViewById(R.id.mortgage_list);
        final List<Mortgage> mortgages = new MortgageDAO(this.getActivity()).getAllMortgages();
        final List<String> mortgages_list = new ArrayList<>();
        final List<Long> mortgages_id_list = new ArrayList<>();
        for (int i = 0; i < mortgages.size(); i++) {
            mortgages_list.add(mortgages.get(i).getAddress());

        }
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                mortgages_list);
        mMortgageList.setAdapter(adapter);

        mMortgageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int itemPosition = position;
                String itemValue = (String) mMortgageList.getItemAtPosition(position);
                final Mortgage current_mortgage = mortgages.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Mortgage Details");
                builder.setMessage("\nType:"+"\t"+current_mortgage.getType() +
                        "\nStreet Address:"+"\t"+current_mortgage.getAddress() +
                        "\nCity:"+"\t"+current_mortgage.getCity() +
                        "\nLoan Amount:"+"\t"+(current_mortgage.getPrice()-current_mortgage.getDownpayment()) +
                        "\nAPR:"+"\t"+current_mortgage.getInterest() +
                        "\nMonthly Payment:"+"\t" + current_mortgage.getMortgageAmount()
                );

                builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Bundle args = new Bundle();
                        args.putSerializable("mortgage", current_mortgage);
                        CalcFragment calcFragment = new CalcFragment();
                        calcFragment.setArguments(args);
                        ft.replace(R.id.tab,calcFragment);
                        ft.commit();
                    }
                });

                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        new MortgageDAO(getActivity()).deleteMortgage(current_mortgage);
                        MortFragment mortFragment = new MortFragment();
                        ft.replace(R.id.tab,mortFragment);
                        ft.commit();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }

        });
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}

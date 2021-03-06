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
import android.util.Log;
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
import com.google.android.gms.maps.model.Marker;
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

                int type_number = current_mortgage.getType();
                String current_mortgage_type= "";
                if(type_number == 2131624081){
                    current_mortgage_type = "House";

                }else if(type_number == 2131624082){
                    current_mortgage_type = "Town House";
                }else if(type_number == 2131624083){
                    current_mortgage_type = "Condo";
                }

                String msg = "\nType:"+current_mortgage_type +
                        "\nStreet Address:"+current_mortgage.getAddress() +
                        "\nCity:"+current_mortgage.getCity() +
                        "\nLoan Amount:"+"$"+(current_mortgage.getPrice()-current_mortgage.getDownpayment()) +
                        "\nAPR:"+current_mortgage.getInterest()+"%" +
                        "\nMonthly Payment:"+ String.format("$%.2f", current_mortgage.getMortgageAmount());


                builder.setTitle("Mortgage Details");
                builder.setMessage(msg);

                builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity activity = (MainActivity)getActivity();
                        Bundle args = new Bundle();
                        args.putSerializable("mortgage", current_mortgage);
                        CalcFragment calcFragment = new CalcFragment();
                        calcFragment.setArguments(args);
                        activity.setFragment(activity.CALC_FRAGMENT, calcFragment);
                    }
                });

                builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity activity = (MainActivity)getActivity();
                        new MortgageDAO(getActivity()).deleteMortgage(current_mortgage);
                        activity.setFragment(activity.MORT_FRAGMENT, new MortFragment());
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

                for(Mortgage mortgage: mortgages) {
                    Marker m = googleMap
                            .addMarker(new MarkerOptions()
                                    .position(new LatLng(mortgage.getLatitude(),
                                            mortgage.getLongitude()))
                                    .title(mortgage.getAddress()
                                            + " "
                                            + mortgage.getCity()
                                            + " "
                                            + mortgage.getState()
                                            + " "
                                            + mortgage.getZip())
                                    .snippet(mortgage.getMortgageAmount()+""));
                    m.setTag(mortgage);
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        final Mortgage current_mortgage = (Mortgage) marker.getTag();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        int type_number = current_mortgage.getType();
                        String current_mortgage_type= "";
                        if(type_number == 2131624081){
                            current_mortgage_type = "House";

                        }else if(type_number == 2131624082){
                            current_mortgage_type = "Town House";
                        }else if(type_number == 2131624083){
                            current_mortgage_type = "Condo";
                        }

                        String msg = "\nType:"+current_mortgage_type +
                                "\nStreet Address:"+current_mortgage.getAddress() +
                                "\nCity:"+current_mortgage.getCity() +
                                "\nLoan Amount:"+"$"+(current_mortgage.getPrice()-current_mortgage.getDownpayment()) +
                                "\nAPR:"+current_mortgage.getInterest()+"%" +
                                "\nMonthly Payment:"+ String.format("$%.2f", current_mortgage.getMortgageAmount());


                        builder.setTitle("Mortgage Details");
                        builder.setMessage(msg);

                        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Bundle args = new Bundle();
                                args.putSerializable("mortgage", current_mortgage);
                                CalcFragment calcFragment = new CalcFragment();
                                calcFragment.setArguments(args);
                                MainActivity activity = (MainActivity)getActivity();
                                activity.setFragment(activity.CALC_FRAGMENT, calcFragment);
                            }
                        });

                        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                MainActivity activity = (MainActivity)getActivity();
                                new MortgageDAO(getActivity()).deleteMortgage(current_mortgage);
                                activity.setFragment(activity.MORT_FRAGMENT, new MortFragment());
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    }
                });

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

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(mortgages.get(0).getLatitude(), mortgages.get(0).getLongitude())).zoom(12).build();
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

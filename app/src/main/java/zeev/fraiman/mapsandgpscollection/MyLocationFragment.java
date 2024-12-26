package zeev.fraiman.mapsandgpscollection;

import static android.content.Context.LOCATION_SERVICE;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocationFragment extends Fragment {

    Button bRenew;
    TextView tvMyLoc, tvMyCountry, tvMyAddress, tvMyCity;
    String stMy = "My location";
    LocationManager locman;
    String provider;
    LocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        locman = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        provider = locman.getBestProvider(new Criteria(), true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_location, container, false);
        tvMyLoc = view.findViewById(R.id.tvMyLoc);
        tvMyCountry= view.findViewById(R.id.tvMyCountry);
        tvMyCity=view.findViewById(R.id.tvMyCity);
        tvMyAddress=view.findViewById(R.id.tvMyAddress);
        bRenew = view.findViewById(R.id.bRenew);

        bRenew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyLocation();
            }
        });
        return view;
    }

    private void showMyLocation() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location!=null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    tvMyLoc.setText("" + latitude + "\n" + longitude);
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null && addresses.size() > 0) {
                            Address address = addresses.get(0);
                            String country = address.getCountryName();
                            tvMyCountry.setText("Country: "+country);
                            String city=address.getLocality();
                            tvMyCity.setText("City: "+city);
                            String my_address=address.getAddressLine(0);
                            tvMyAddress.setText("Address: "+my_address);
                        } else {
                            Toast.makeText(getActivity(), "Not found information\\nabout location", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else
                    Toast.makeText(getActivity(), "Not found information\\nabout location", Toast.LENGTH_SHORT).show();
                    //tvMy.setText("Not found information\nabout location");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "Not found GPS data", Toast.LENGTH_SHORT).show();
            return;
        }
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, locationListener);

    }
}
package zeev.fraiman.mapsandgpscollection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class TraceFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    double lat1, lon1, lat2, lon2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trace, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.trace);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        lat1 = 60.3946482;//-90+180*Math.random();
        lon1 = 5.3234818;//-90+180*Math.random();
        LatLng latLng = new LatLng(lat1, lon1);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("My marker")
                .snippet("it's marker for our teacher's curs");
        googleMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                lat2 = latLng.latitude;
                lon2 = latLng.longitude;
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("Stop")
                        .snippet("Stop route");
                googleMap.addMarker(markerOptions);
                LatLng point1 = new LatLng(lat1, lon1);
                LatLng point2 = new LatLng(lat2, lon2);
                PolylineOptions lineOptions = new PolylineOptions()
                        .add(point1)
                        .add(point2);
                Polyline polyline = mMap.addPolyline(lineOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point1, 13));

            }
        });
    }
}

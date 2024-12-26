package zeev.fraiman.mapsandgpscollection;

import static android.content.Context.MODE_PRIVATE;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MapMarkerFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap mMap;
    double latitude,longitude;
    Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map_marker, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(51.5074, -0.1278); // London
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                Toast.makeText(getActivity(), ""+latitude+"\n"+longitude, Toast.LENGTH_SHORT).show();
                writeToFile(""+latitude,""+longitude);
                saveInDB(latitude,longitude);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(latLng)
                        .title("My marker")
                        .snippet("it's marker for our teacher's curs");
                googleMap.addMarker(markerOptions);
            }
        });
    }

    private void saveInDB(double latitude, double longitude) {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                HelperDB helperDB=new HelperDB(getActivity());
                SQLiteDatabase db=helperDB.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put("Lat",""+latitude);
                contentValues.put("Lon",""+longitude);
                db.insert("My_GPS",null, contentValues);
                db.close();
            }
        });
        thread.start();
    }

    public void writeToFile(String lat, String lon) {
        try {
            FileOutputStream fos = getActivity().openFileOutput("my_gps.txt", Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.append(latitude+"\n"+longitude+"\n");
            outputStreamWriter.close();
            fos.close();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
package zeev.fraiman.mapsandgpscollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest.permission.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Context context;
    Button bMyLoc, bByAddress, blatLon, bMapFragment, bTraceFragment;
    MyLocationFragment myLocationFragment;
    ByAddressFragment byAddressFragment;
    ByLatLonFragment byLatLonFragment;
    MapMarkerFragment mapMarkerFragment;
    TraceFragment traceFragment;
    LocationManager locman;

    private GoogleMap mMap;
    private ActivityResultLauncher<Intent> mapLauncher;
    private final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        bMyLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocationFragment = new MyLocationFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FLforF, myLocationFragment);
                ft.commit();

            }
        });

        blatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byLatLonFragment = new ByLatLonFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FLforF, byLatLonFragment);
                ft.commit();
            }
        });

        bByAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byAddressFragment = new ByAddressFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FLforF, byAddressFragment);
                ft.commit();
            }
        });

        bMapFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapMarkerFragment=new MapMarkerFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FLforF, mapMarkerFragment);
                ft.commit();
            }
        });

        bTraceFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traceFragment=new TraceFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.FLforF, traceFragment);
                ft.commit();
            }
        });
    }

    private void initComponents() {
        context=this;
        bMyLoc= (Button) findViewById(R.id.bMyLoc);
        bByAddress= (Button) findViewById(R.id.bByAddress);
        blatLon= (Button) findViewById(R.id.bLatLon);
        bMapFragment= (Button) findViewById(R.id.bMapFragment);
        bTraceFragment= (Button) findViewById(R.id.bTraceFragment);

        File file = new File(context.getFilesDir(), "mypass.txt");
        boolean exists = file.exists();
        if (exists) {
            Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "No", Toast.LENGTH_SHORT).show();
        }


        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        boolean isExists = sharedPreferences.contains("key");

        if (isExists) {
            Toast.makeText(context, "Found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
        }
    }
}
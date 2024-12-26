package zeev.fraiman.mapsandgpscollection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ByLatLonFragment extends Fragment {

    Button bByGeo;
    EditText etLat, etLon;
    String stLat="", stLon="", geoUriString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_by_lat_lon, container, false);
        etLat= (EditText) view.findViewById(R.id.etLat);
        etLon= (EditText) view.findViewById(R.id.etLon);
        bByGeo= (Button) view.findViewById(R.id.bByGeo);
        bByGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stLat = etLat.getText().toString();
                stLon = etLon.getText().toString();
                geoUriString = "geo:" + stLat + "," + stLon + "?z=10";
                Uri geoUri = Uri.parse(geoUriString);
                Intent map = new Intent(Intent.ACTION_VIEW, geoUri);
                startActivity(map);
            }
        });
        return view;
    }
}
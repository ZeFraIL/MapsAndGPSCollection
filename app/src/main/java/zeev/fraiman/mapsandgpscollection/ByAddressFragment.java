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

public class ByAddressFragment extends Fragment {

    EditText etStreet, etCity, etCountry;
    String stStreet="", stCity="", stCountry="", geoUriString;
    Button bByAdr;
    Uri geoUri;
    Intent map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_by_address, container, false);
        initComponents(view);
        bByAdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stStreet=etStreet.getText().toString();
                stCity=etCity.getText().toString();
                stCountry=etCountry.getText().toString();
                geoUriString = "geo:0,0?q="+stStreet+", "+stCity+", "+stCountry+"&z=10";
                geoUri = Uri.parse(geoUriString);
                map = new Intent(Intent.ACTION_VIEW, geoUri);
                startActivity(map);

            }
        });
        return view;
    }

    private void initComponents(View view) {
        etStreet=view.findViewById(R.id.etStreet);
        etCity=view.findViewById(R.id.etCity);
        etCountry=view.findViewById(R.id.etCountry);
        bByAdr=view.findViewById(R.id.bAdr);
    }
}
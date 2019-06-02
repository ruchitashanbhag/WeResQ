package com.example.myheremapssdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Map map;
    MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Search for the map fragment to finish setup by calling init().

        // mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps",
                "android.intent.action.MAIN");
        mapFragment=(MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    map = mapFragment.getMap();
                    map.setCenter(new GeoCoordinate(37.7397, -121.4252, 0.0), Map.Animation.NONE);
                    map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
                    MapMarker defaultMarker = new MapMarker();
                    defaultMarker.setCoordinate(new GeoCoordinate(37.7397, -121.4252, 0.0));
                    map.addMapObject(defaultMarker);
                }

                else {
                    Toast.makeText(getApplicationContext(),"Map could not be loaded",Toast.LENGTH_LONG).show();
                    Log.e("ERR", error.getDetails());
                }
            }

        });
    }
}


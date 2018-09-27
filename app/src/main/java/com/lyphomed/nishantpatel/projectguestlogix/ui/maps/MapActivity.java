package com.lyphomed.nishantpatel.projectguestlogix.ui.maps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyphomed.nishantpatel.projectguestlogix.R;

public class MapActivity extends AppCompatActivity {

    /**
     * Call this method to get intent to start {@link MapActivity}
     *
     * @param context Context of activity from which we want to start {@link MapActivity}
     * @return starter intent
     */
    public static Intent getStarterIntent(Context context) {
        return new Intent(context, MapActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }
}

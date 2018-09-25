package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lyphomed.nishantpatel.projectguestlogix.R;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}

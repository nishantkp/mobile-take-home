package com.lyphomed.nishantpatel.projectguestlogix.utils;

import android.content.Context;

import com.lyphomed.nishantpatel.projectguestlogix.R;

import java.io.InputStream;

/**
 * Provides input streams for populating database tables from csv files
 */
public class TableDataCreation {
    private Context mContext;

    public TableDataCreation(Context context) {
        mContext = context;
    }

    public InputStream provideAirlineInputStream() {
        return mContext.getResources().openRawResource(R.raw.airlines);
    }

    public InputStream provideAirportInputStream() {
        return mContext.getResources().openRawResource(R.raw.airports);
    }

    public InputStream provideRoutesInputStream() {
        return mContext.getResources().openRawResource(R.raw.routes);
    }
}

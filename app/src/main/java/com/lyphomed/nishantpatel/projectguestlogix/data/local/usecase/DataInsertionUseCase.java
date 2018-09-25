package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Airlines;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Routes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.Observable;

/**
 * Data insertion use case to load data into database form csv file
 */
public class DataInsertionUseCase {

    private AirlinesDatabase mDatabase;
    private static final String LOG_TAG = DataInsertionUseCase.class.getSimpleName();

    public DataInsertionUseCase(AirlinesDatabase database) {
        mDatabase = database;
    }

    public Observable<Boolean> fillAirportData(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadAirportTable(inputStream));
    }

    public Observable<Boolean> fillAirlinesTable(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadAirlinesTable(inputStream));
    }

    public Observable<Boolean> fillRouteTable(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadRoutesTable(inputStream));
    }

    private boolean loadAirportTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Airports airports;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                airports = new Airports(row[0], row[1], row[2], row[3], row[4], row[5]);
                mDatabase.getAirliesDao().insertAirports(airports);
            }
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error reading CSV file for Airport Data", e);
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream");
            }
        }
    }

    private boolean loadAirlinesTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Airlines airlines;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                airlines = new Airlines(row[0], row[1], row[2], row[3]);
                mDatabase.getAirliesDao().insertAirlines(airlines);
            }
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error reading CSV file for Airlines Data", e);
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream");
            }
        }
    }

    private boolean loadRoutesTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Routes routes;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                routes = new Routes(row[0], row[1], row[2]);
                mDatabase.getAirliesDao().insertRoutes(routes);
            }
            return true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error reading CSV file for Routes Data", e);
            return false;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream");
            }
        }
    }
}

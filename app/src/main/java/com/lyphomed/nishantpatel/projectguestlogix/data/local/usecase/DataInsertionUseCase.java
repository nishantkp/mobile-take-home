package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airlines;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;

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

    // TAG for log messages
    private static final String LOG_TAG = DataInsertionUseCase.class.getSimpleName();

    public DataInsertionUseCase(AirlinesDatabase database) {
        mDatabase = database;
    }

    /**
     * Call this method to insert airport data from csv file to database table
     * <p>
     * NOTE: This method will return Observable, so perform operation on different thread
     * subscribe on Schedulers.io() and get the results on AndroidSchedulers.mainThread()
     *
     * @param inputStream InputStream for airports data csv file
     * @return true if data insertion is successful, false otherwise
     */
    public Observable<Boolean> fillAirportData(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadAirportTable(inputStream));
    }

    /**
     * Call this method to insert airlines data from csv file to database table
     * <p>
     * NOTE: This method will return Observable, so perform operation on different thread
     * subscribe on Schedulers.io() and get the results on AndroidSchedulers.mainThread()
     *
     * @param inputStream InputStream for airports data csv file
     * @return true if data insertion is successful, false otherwise
     */
    public Observable<Boolean> fillAirlinesTable(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadAirlinesTable(inputStream));
    }

    /**
     * Call this method to insert routes data from csv file to database table
     * <p>
     * NOTE: This method will return Observable, so perform operation on different thread
     * subscribe on Schedulers.io() and get the results on AndroidSchedulers.mainThread()
     *
     * @param inputStream for airports data csv file
     * @return true if data insertion is successful, false otherwise
     */
    public Observable<Boolean> fillRouteTable(final InputStream inputStream) {
        return Observable.fromCallable(() -> loadRoutesTable(inputStream));
    }

    /**
     * Use this method to insert Airport data into database's "airports" table
     * <p>
     * >> while loop will iterate through each line, separate that line by ","
     * create object and insert into database table
     *
     * @param inputStream InputStream for Airports data csv file
     * @return true if data insertion is success, false otherwise
     */
    private boolean loadAirportTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Airports airports;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                airports = new Airports(row[0], row[1], row[2], row[3], row[4], row[5]);
                Log.i("Airport", row[0] + row[1] + row[2]);
                mDatabase.getAirlinesDao().insertAirports(airports);
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


    /**
     * Use this method to insert Airlines data into database's "airlines" table
     * <p>
     * >> while loop will iterate through each line, separate that line by ","
     * create object and insert into database table
     *
     * @param inputStream InputStream for Airlines data csv file
     * @return true if data insertion is success, false otherwise
     */
    private boolean loadAirlinesTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Airlines airlines;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                airlines = new Airlines(row[0], row[1], row[2], row[3]);
                Log.i("Airlines", row[0] + row[1] + row[2]);
                mDatabase.getAirlinesDao().insertAirlines(airlines);
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

    /**
     * Use this method to insert Routes data into database's "routes" table
     * <p>
     * >> while loop will iterate through each line, separate that line by ","
     * create object and insert into database table
     *
     * @param inputStream InputStream for Routes data csv file
     * @return true if data insertion is success, false otherwise
     */
    private boolean loadRoutesTable(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Routes routes;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] row = line.split(",");
                routes = new Routes(row[0], row[1], row[2]);
                Log.i("Routes", row[0] + row[1] + row[2]);
                mDatabase.getAirlinesDao().insertRoutes(routes);
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

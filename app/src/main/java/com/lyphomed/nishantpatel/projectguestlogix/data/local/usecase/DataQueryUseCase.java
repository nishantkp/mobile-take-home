package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Data query use case, which deals with querying data from database tables
 */
public class DataQueryUseCase {
    private AirlinesDatabase mAirlinesDatabase;

    public DataQueryUseCase(AirlinesDatabase airlinesDatabase) {
        mAirlinesDatabase = airlinesDatabase;
    }

    /**
     * Use this method to get the direct path between origin and destination
     * <p>
     * NOTE: Don't forget to subscribe flowable on Schedules.io() and observe on
     * AndroidSchedulers.mainThread() to query database table on rx schedulers thread
     *
     * @param origin      IATA3 code of origin
     * @param destination IATA3 code of destination
     * @return Flowable list of Routes
     */
    public Flowable<List<Routes>> provideFlightDetails(String origin, String destination) {
        return mAirlinesDatabase.getAirliesDao().getDirectRoutes(origin, destination);
    }

    /**
     * Use this method to get the information on airport from IATA3 code
     * Information includes, name of airport, city, country, IATA3 code, latitude and longitude
     * <p>
     * NOTE: Don't forget to subscribe flowable on Schedules.io() and observe on
     * AndroidSchedulers.mainThread() to query database table on rx schedulers thread
     *
     * @param iata3 IATA3 code or airport whose information we are interested in
     * @return Flowable list of airports
     */
    public Flowable<List<Airports>> provideAirportFromIata3(String iata3) {
        return mAirlinesDatabase.getAirliesDao().getAirportsFromIata(iata3);
    }
}

package com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.ViaRoute;

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
        return mAirlinesDatabase.getAirlinesDao().getDirectRoutes(origin, destination);
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
        return mAirlinesDatabase.getAirlinesDao().getAirportsFromIata(iata3);
    }


    /**
     * This method will provide list of all the possible paths to "via" location
     * <p>
     * NOTE: Don't forget to subscribe flowable on Schedules.io() and observe on
     * AndroidSchedulers.mainThread() to query database table on rx schedulers thread
     *
     * @param origin      IATA3 code for origin
     * @param destination IATA3 code for destination
     * @return Flowable list of {@link Routes}
     */
    public Flowable<List<Routes>> provideAllPathsToViaLocation(String origin, String destination) {
        return mAirlinesDatabase.getAirlinesDao().getPossiblePathsToViaLocation(origin, destination);
    }

    /**
     * Call this method to get the all the possible routes with "Via" destination
     * <p>
     * Below method first finds the all possible routes from origin to "via" location
     * Then it uses flatMapIterable to loop through each route finds the "via" location to
     * <p>
     * NOTE: Don't forget to subscribe flowable on Schedules.io() and observe on
     * AndroidSchedulers.mainThread() to query database table on rx schedulers thread
     *
     * @param origin      IATA3 code for origin
     * @param destination IATA3 code for destination
     * @return Flowable {@link ViaRoute} object
     */
    public Flowable<ViaRoute> provideFullPathsWithViaDestination(String origin, String destination) {
        return provideAllPathsToViaLocation(origin, destination)
                .flatMapIterable(routesList -> routesList)
                .flatMap(routes -> Flowable.zip(
                        provideFlightDetails(origin, routes.getDestination()),
                        provideFlightDetails(routes.getDestination(), destination),
                        ViaRoute::new));
    }
}

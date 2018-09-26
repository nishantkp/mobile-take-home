package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataAvailabilityUseCase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataInsertionUseCase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataQueryUseCase;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.ViaRoute;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Manager which deals with all the business logic,
 * Use DataManager methods from Presenters to deal with any business related logic
 */
public class DataManager implements DataContract {
    private static DataInsertionUseCase mDataInsertionUseCase;
    private static DataAvailabilityUseCase sDataAvailabilityUseCase;
    private static DataQueryUseCase sDataQueryUseCase;
    private static DataManager sDataManager;
    private static AirlinesDatabase sAirlinesDatabase;

    // DataManager singleton
    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            mDataInsertionUseCase = new DataInsertionUseCase(sAirlinesDatabase);
            sDataAvailabilityUseCase = new DataAvailabilityUseCase(sAirlinesDatabase);
            sDataQueryUseCase = new DataQueryUseCase(sAirlinesDatabase);
        }
        return sDataManager;
    }

    private DataManager() {
        //Private constructor, so no one can make an object of DataManager
    }

    /**
     * NOTE: First off all, build the dataManager with airlines database
     * Then use getInstance() method to get DataManager instance
     *
     * @param airlinesDatabase AirlinesDatabase instance
     */
    public static void Build(AirlinesDatabase airlinesDatabase) {
        if (sAirlinesDatabase == null) {
            sAirlinesDatabase = airlinesDatabase;
        }
    }

    @Override
    public Observable<Boolean> fillAirportTable(InputStream inputStream) {
        return mDataInsertionUseCase.fillAirportData(inputStream);
    }

    @Override
    public Observable<Boolean> fillAirlinesTable(InputStream inputStream) {
        return mDataInsertionUseCase.fillAirlinesTable(inputStream);
    }

    @Override
    public Observable<Boolean> fillRoutesTable(InputStream inputStream) {
        return mDataInsertionUseCase.fillRouteTable(inputStream);
    }

    @Override
    public void checkDataAvailability(OnTaskCompletion callback) {
        sDataAvailabilityUseCase.checkDataAvailability(callback);
    }

    @Override
    public Flowable<List<Routes>> provideFlightDetails(String origin, String destination) {
        return sDataQueryUseCase.provideFlightDetails(origin, destination);
    }

    @Override
    public Maybe<Airports> provideAirportFromIata3(String iata3) {
        return sDataQueryUseCase.provideAirportFromIata3(iata3);
    }

    @Override
    public Flowable<List<Routes>> provideAllPathsToViaLocation(String origin, String destination) {
        return sDataQueryUseCase.provideAllPathsToViaLocation(origin, destination);
    }

    @Override
    public Flowable<ViaRoute> provideFullPathWithViaLocation(String origin, String destination) {
        return sDataQueryUseCase.provideFullPathsWithViaDestination(origin, destination);
    }
}

package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataAvailabilityUseCase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataInsertionUseCase;

import java.io.InputStream;

import io.reactivex.Observable;

/**
 * Manager which deals with all the business logic,
 * Use DataManager methods from Presenters to deal with any business related logic
 */
public class DataManager implements DataContract {
    private static DataInsertionUseCase mDataInsertionUseCase;
    private static DataAvailabilityUseCase sDataAvailabilityUseCase;
    private static DataManager sDataManager;
    private static AirlinesDatabase sAirlinesDatabase;

    // DataManager singleton
    public static DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            mDataInsertionUseCase = new DataInsertionUseCase(sAirlinesDatabase);
            sDataAvailabilityUseCase = new DataAvailabilityUseCase(sAirlinesDatabase);

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
}

package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.AirlinesDatabase;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.usecase.DataInsertionUseCase;

import java.io.InputStream;

import io.reactivex.Observable;

public class DataManager implements DataContract {
    private DataInsertionUseCase mDataInsertionUseCase;
    private static DataManager sDataManager;
    private static AirlinesDatabase sAirlinesDatabase;

    public DataManager getInstance() {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            mDataInsertionUseCase = new DataInsertionUseCase(sAirlinesDatabase);
        }
        return sDataManager;
    }

    private DataManager() {
    }

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
}

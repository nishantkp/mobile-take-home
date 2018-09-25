package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;

import java.io.InputStream;

import io.reactivex.Observable;

interface DataContract {
    Observable<Boolean> fillAirportTable(InputStream inputStream);

    Observable<Boolean> fillAirlinesTable(InputStream inputStream);

    Observable<Boolean> fillRoutesTable(InputStream inputStream);

    void checkDataAvailability(OnTaskCompletion callback);
}

package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

interface DataContract {
    Observable<Boolean> fillAirportTable(InputStream inputStream);

    Observable<Boolean> fillAirlinesTable(InputStream inputStream);

    Observable<Boolean> fillRoutesTable(InputStream inputStream);

    Flowable<List<Routes>> provideFlightDetails(String origin, String destination);

    Flowable<List<Airports>> provideAirportFromIata3(String iata3);

    void checkDataAvailability(OnTaskCompletion callback);
}

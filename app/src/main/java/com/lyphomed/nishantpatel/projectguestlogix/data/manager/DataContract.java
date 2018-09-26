package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.ViaRoute;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

interface DataContract {
    Observable<Boolean> fillAirportTable(InputStream inputStream);

    Observable<Boolean> fillAirlinesTable(InputStream inputStream);

    Observable<Boolean> fillRoutesTable(InputStream inputStream);

    Flowable<List<Routes>> provideFlightDetails(String origin, String destination);

    Maybe<Airports> provideAirportFromIata3(String iata3);

    Flowable<List<Routes>> provideAllPathsToViaLocation(String origin, String destination);

    Flowable<ViaRoute> provideFullPathWithViaLocation(String origin, String destination);

    void checkDataAvailability(OnTaskCompletion callback);
}

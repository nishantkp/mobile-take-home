package com.lyphomed.nishantpatel.projectguestlogix.data.manager;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks.OnTaskCompletion;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.ViaRoute;
import com.lyphomed.nishantpatel.projectguestlogix.utils.bws.Node;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

interface DataContract {
    Observable<Boolean> fillAirportTable(InputStream inputStream);

    Observable<Boolean> fillAirlinesTable(InputStream inputStream);

    Observable<Boolean> fillRoutesTable(InputStream inputStream);

    Flowable<List<Routes>> provideFlightDetails(String origin, String destination);

    Maybe<Airports> provideAirportFromIata3(String iata3);

    Flowable<List<Routes>> provideAllPathsToViaLocation(String origin, String destination);

    Flowable<ViaRoute> provideFullPathWithViaLocation(String origin, String destination);

    Flowable<List<Airports>> provideAirports();

    Flowable<List<Routes>> provideRoutes();

    Flowable<Map<String, Node>> provideNodesWithEdges();

    void checkDataAvailability(OnTaskCompletion callback);
}

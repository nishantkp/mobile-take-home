package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import com.lyphomed.nishantpatel.projectguestlogix.base.MvpView;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface DashboardContract {
    interface View extends MvpView {
        void onDisposable(Disposable disposable);

        void onDirectPath(List<Routes> directPathList);

        void onViaPaths(List<FullViaPath> viaPathList);

        void noPathsFound(String message);

        void onOriginAirportDetail(Airports airports);

        void onDestinationAirportDetail(Airports airports);

    }

    interface Presenter {
        void findFlightConnections(String origin, String destination);

        void findFlightPathWithViaLocation(String origin, String destination);

        void findFlightConnectionsBFS(String origin, String destination);

        void getOriginAirport(String iata3);

        void getDestinationAirport(String iata3);
    }
}

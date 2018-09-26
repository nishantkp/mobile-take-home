package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Dashboard presenter which deals with finding the flight connections either direct or via one stop
 * If usually finds shortest path meaning, if it has direct paths it doesn't bother finding
 * one stoppage flight connections
 */
public class DashboardPresenter
        extends BasePresenter<DashboardContract.View>
        implements DashboardContract.Presenter {

    private DataManager mDataManager;

    DashboardPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public DashboardContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(DashboardContract.View view) {
        super.attachView(view);
    }

    /**
     * Call this method to find the flight connections,
     * If it doesn't find the direct path it automatically calls the method to find path with one
     * stoppage
     *
     * @param origin      IATA3 code for the origin
     * @param destination IATA3 code for the destination
     */
    @Override
    public void findFlightConnections(String origin, String destination) {
        Disposable disposable =
                mDataManager.provideFlightDetails(origin, destination)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(paths -> {
                            if (!paths.isEmpty()) getView().onDirectPath(paths);
                            else findFlightPathWithViaLocation(origin, destination);
                        });
        getView().onDisposable(disposable);
    }

    /**
     * Call this method to find the flight connections with one stoppage
     *
     * @param origin      IATA3 code for origin
     * @param destination IATA3 code for destination
     */
    @Override
    public void findFlightPathWithViaLocation(String origin, String destination) {
        Disposable disposable =
                mDataManager.provideFullPathWithViaLocation(origin, destination)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(viaRoute -> {
                            List<Routes> firstRoutes = viaRoute.getfirstRoute();
                            List<Routes> secondRoutes = viaRoute.getSecondRoute();
                            List<FullViaPath> fullViaPathList = new ArrayList<>();
                            for (Routes routes1 : firstRoutes) {
                                for (Routes routes2 : secondRoutes) {
                                    fullViaPathList.add(
                                            new FullViaPath(routes1.getOrigin(),
                                                    routes1.getDestination(),
                                                    routes2.getDestination(),
                                                    routes1.getAirlineCode(),
                                                    routes2.getAirlineCode()));
                                }
                            }
                            if (fullViaPathList.isEmpty())
                                getView().noPathsFound("No flight connections found!");
                            else
                                getView().onViaPaths(fullViaPathList);
                        });
        getView().onDisposable(disposable);
    }

    @Override
    public void getDestinationAirport(String iata3) {
        Disposable disposable =
                mDataManager.provideAirportFromIata3(iata3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(airport -> getView().onDestinationAirportDetail(airport));
        getView().onDisposable(disposable);
    }

    @Override
    public void getOriginAirport(String iata3) {
        Disposable disposable =
                mDataManager.provideAirportFromIata3(iata3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(airport -> getView().onOriginAirportDetail(airport));
        getView().onDisposable(disposable);
    }
}

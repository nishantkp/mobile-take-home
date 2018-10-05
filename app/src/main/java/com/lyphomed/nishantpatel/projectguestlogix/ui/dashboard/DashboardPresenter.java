package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.TwoStopRoute;
import com.lyphomed.nishantpatel.projectguestlogix.utils.bws.Graph;
import com.lyphomed.nishantpatel.projectguestlogix.utils.bws.Node;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
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
                mDataManager.provideFullPathWithViaLocation(origin, destination).toObservable()
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

    /**
     * Call this method to get the destination airport details from IATA3 code
     *
     * @param iata3 destination airport IATA3 code
     */
    @Override
    public void getDestinationAirport(String iata3) {
        Disposable disposable =
                mDataManager.provideAirportFromIata3(iata3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(airport -> getView().onDestinationAirportDetail(airport));
        getView().onDisposable(disposable);
    }

    /**
     * Call this method to get the origin airport details from IATA3 code
     *
     * @param iata3 origin airport IATA3 code
     */
    @Override
    public void getOriginAirport(String iata3) {
        Disposable disposable =
                mDataManager.provideAirportFromIata3(iata3)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(airport -> getView().onOriginAirportDetail(airport));
        getView().onDisposable(disposable);
    }

    /**
     * Call this method to find the flight connections with least transfers
     * If there are paths from origin to destination like direct, one transfer, two transfer....
     * i.e transfer means "via" point
     * <p>
     * The method will give list of nodes containing least transfers
     *
     * @param origin      IATA3 code for origin
     * @param destination IATA3 code for destination
     */
    @Override
    public void findFlightConnectionsBFS(String origin, String destination) {
        Disposable disposable = mDataManager.provideNodesWithEdges()
                .flatMap(data -> {
                    // Create a graph for BFS
                    Graph graph = new Graph();
                    graph.setNodeLookUp(data);
                    return Flowable.just(graph.breadthFirstSearch(origin, destination));
                })
                .filter(data -> data.size() == 4) // Filter ensures that we only get two stoppage path
                .flatMap(this::makeFlightConnectionsFromTwoStoppage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fullViaPathList -> {
                    for (FullViaPath route : fullViaPathList) {
                        Log.i("PATH", "\nFirst Route:  departure from " + route.getOrigin() + " to " + route.getFirstVia() + " in " + route.getOriginToFirstViaFlight()
                                + "\nSecond Route:  departure from " + route.getFirstVia() + " to " + route.getSecondVia() + " in " + route.getFirstViaToSecondViaFlight()
                                + "\nThird Route:  departure from " + route.getSecondVia() + " to " + route.getDestination() + " in " + route.getSecondViaToDestinationFlight());
                    }
                });
        getView().onDisposable(disposable);
    }

    /**
     * Use this method to get all the available flight options between origin and destination with
     * two stops
     *
     * @param data list of nodes containing all the airports IATA3 codes
     * @return list of {@link FullViaPath} object, and each object contains origin, destination,
     * first via, second via IATA3 code and airline codes for origin-firstVia, firstVia-secondVia,
     * secondVia-destination
     */
    private Flowable<List<FullViaPath>> makeFlightConnectionsFromTwoStoppage(List<Node> data) {
        String departure = data.get(0).getNodeLabel();  // Origin IATA3 code
        String firstStop = data.get(1).getNodeLabel();  // First stop IATA3 code
        String secondStop = data.get(2).getNodeLabel(); // Second stop IATA3 code
        String arrival = data.get(3).getNodeLabel();    // Destination IATA3 code

        Flowable<List<Routes>> dToF = mDataManager.provideFlightDetails(departure, firstStop);
        Flowable<List<Routes>> fToS = mDataManager.provideFlightDetails(firstStop, secondStop);
        Flowable<List<Routes>> sToA = mDataManager.provideFlightDetails(secondStop, arrival);

        return Flowable.zip(dToF, fToS, sToA, TwoStopRoute::new)
                .flatMap(twoStopRoute -> {
                    List<Routes> firstRoute = twoStopRoute.getFirstRoute();
                    List<Routes> secondRoute = twoStopRoute.getSecondRoute();
                    List<Routes> thirdRoute = twoStopRoute.getThirdRoute();
                    List<FullViaPath> fullViaPaths = new ArrayList<>();

                    // Make FullViaPath object with details like,
                    // origin, destination, first via, second via and flight connecting them
                    for (Routes routes1 : firstRoute) {
                        for (Routes routes2 : secondRoute) {
                            for (Routes routes3 : thirdRoute) {
                                fullViaPaths.add(
                                        new FullViaPath(routes1.getOrigin(),
                                                routes1.getDestination(),
                                                routes2.getDestination(),
                                                routes3.getDestination(),
                                                routes1.getAirlineCode(),
                                                routes2.getAirlineCode(),
                                                routes3.getAirlineCode()
                                        )
                                );
                            }
                        }
                    }
                    return Flowable.just(fullViaPaths);
                });
    }
}

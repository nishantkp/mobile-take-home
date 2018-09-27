package com.lyphomed.nishantpatel.projectguestlogix.ui.maps;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter which gets the information on airports and provides a list of airport to
 * {@link MapsActivity}
 */
public class MapsPresenter extends BasePresenter<MapsContract.View>
        implements MapsContract.Presenter {

    private DataManager mDataManager;
    private List<Airports> mAirportsList;

    MapsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mAirportsList = new ArrayList<>();
    }

    @Override
    public MapsContract.View getView() {
        return super.getView();
    }

    @Override
    public void attachView(MapsContract.View view) {
        super.attachView(view);
    }

    /**
     * Call this method to get the airport details in direct path
     *
     * @param origin      origin airport IATA3
     * @param destination destination airport IATA3
     */
    @Override
    public void provideAirportDetails(String origin, String destination) {
        Maybe<Airports> one = mDataManager.provideAirportFromIata3(origin);
        Maybe<Airports> two = mDataManager.provideAirportFromIata3(destination);

        Disposable disposable =
                one.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(originAirport -> {
                            mAirportsList.add(originAirport);
                            two.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(destinationAirport -> {
                                        mAirportsList.add(destinationAirport);
                                        getView().onAirports(mAirportsList);
                                    });
                        });
        getView().onDisposable(disposable);
    }

    /**
     * Call this method to get the airport details when there is a via path
     *
     * @param origin      origin airport IATA3
     * @param via         via airport IATA3
     * @param destination destination airport IATA3
     */
    @Override
    public void provideAirportDetails(String origin, String via, String destination) {
        Maybe<Airports> one = mDataManager.provideAirportFromIata3(origin);
        Maybe<Airports> two = mDataManager.provideAirportFromIata3(via);
        Maybe<Airports> three = mDataManager.provideAirportFromIata3(destination);

        Disposable disposable =
                one.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(originAirport -> {
                            mAirportsList.add(originAirport);
                            two.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(viaAirport -> {
                                        mAirportsList.add(viaAirport);
                                        three.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(destinationAirport -> {
                                                    mAirportsList.add(destinationAirport);
                                                    getView().onAirports(mAirportsList);
                                                });
                                    });
                        });
        getView().onDisposable(disposable);
    }
}

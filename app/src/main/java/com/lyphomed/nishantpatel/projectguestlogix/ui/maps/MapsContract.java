package com.lyphomed.nishantpatel.projectguestlogix.ui.maps;

import com.lyphomed.nishantpatel.projectguestlogix.base.MvpView;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * {@link MapsActivity} and {@link MapsPresenter} contract class
 */
public interface MapsContract {
    interface View extends MvpView {
        void onDisposable(Disposable d);

        void onAirports(List<Airports> airports);
    }

    interface Presenter {
        void provideAirportDetails(String origin, String destination);

        void provideAirportDetails(String origin, String via, String destination);
    }
}

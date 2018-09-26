package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    // Disposable object for flowable to dispose in onDestroy()
    private CompositeDisposable mCompositeDisposable;

    /**
     * Call this method to get the starter intent to start {@link DashboardActivity}
     *
     * @param context context of activity from which intent is originated
     * @return starter intent
     */
    public static Intent getStarterIntent(Context context) {
        return new Intent(context, DashboardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mCompositeDisposable = new CompositeDisposable();
        DashboardPresenter dashboardPresenter = new DashboardPresenter(DataManager.getInstance());
        dashboardPresenter.attachView(this);

        dashboardPresenter.findFlightConnections("ABJ", "YYZ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDirectPath(List<Routes> directPathList) {
        for (Routes r : directPathList) {
            Log.i("Direct Path", r.getAirlineCode() + " " + r.getOrigin() + r.getDestination());
        }
    }

    @Override
    public void onViaPaths(List<FullViaPath> viaPathList) {
        for (FullViaPath fp : viaPathList) {
            Log.i("Via Path", fp.getOrigin() + " to " + fp.getVia() + " in " + fp.getOriginToViaFlight() + " AND "
                    + fp.getVia() + " to " + fp.getDestination() + " in " + fp.getViaToDestination());
        }
    }

    @Override
    public void noPathsFound(String message) {
        Log.i("No Paths", message);
    }
}

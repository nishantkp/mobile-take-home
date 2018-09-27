package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.lyphomed.nishantpatel.projectguestlogix.R;
import com.lyphomed.nishantpatel.projectguestlogix.config.PublicKeys;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;
import com.lyphomed.nishantpatel.projectguestlogix.databinding.ActivityDashboardBinding;
import com.lyphomed.nishantpatel.projectguestlogix.ui.adapter.FlightsAdapter;
import com.lyphomed.nishantpatel.projectguestlogix.ui.maps.MapsActivity;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.FullViaPath;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.UserQuery;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DashboardActivity extends AppCompatActivity
        implements DashboardContract.View, FlightsAdapter.OnDirectPathItemClick,
        FlightsAdapter.OnViaPathItemClick {

    // Disposable object for flowable to dispose in onDestroy()
    private CompositeDisposable mCompositeDisposable;
    private ActivityDashboardBinding mBinding;
    private FlightsAdapter mAdapter;

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        String origin = "";
        String destination = "";

        // Get the data from Intent
        if (getIntent() != null) {
            origin = getIntent().getStringExtra(PublicKeys.USER_ORIGIN).toUpperCase();
            destination = getIntent().getStringExtra(PublicKeys.USER_DESTINATION).toUpperCase();
            mBinding.dashboardHeader.setUserQuery(new UserQuery(origin, destination));
        }
        mCompositeDisposable = new CompositeDisposable();
        DashboardPresenter dashboardPresenter = new DashboardPresenter(DataManager.getInstance());
        dashboardPresenter.attachView(this);
        mAdapter = new FlightsAdapter(this, this);
        mBinding.dashboardRv.setLayoutManager(new LinearLayoutManager(this));
        mBinding.dashboardRv.setHasFixedSize(true);
        mBinding.dashboardRv.setAdapter(mAdapter);

        dashboardPresenter.findFlightConnections(origin, destination);
        dashboardPresenter.getOriginAirport(origin);
        dashboardPresenter.getDestinationAirport(destination);
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
        mBinding.dashboardEmptyText.setVisibility(View.GONE);
        mAdapter.updateDirectPath(directPathList, PublicKeys.DIRECT_PATH_LIST_ITEM);
    }

    @Override
    public void onViaPaths(List<FullViaPath> viaPathList) {
        mBinding.dashboardEmptyText.setVisibility(View.GONE);
        mAdapter.updateFullPath(viaPathList, PublicKeys.VIA_PATH_LIST_ITEM);
    }

    @Override
    public void noPathsFound(String message) {
        mBinding.dashboardEmptyText.setVisibility(View.VISIBLE);
        Log.i("No Paths", message);
    }

    @Override
    public void onOriginAirportDetail(Airports airports) {
        mBinding.dashboardHeader.dashboardOriginName.setText(airports.getAirportName());
    }

    @Override
    public void onDestinationAirportDetail(Airports airports) {
        mBinding.dashboardHeader.dashboardDestinationName.setText(airports.getAirportName());
    }

    @Override
    public void onItemClick(FullViaPath fullViaPath) {
        startActivity(MapsActivity.getStarterIntent(this).putExtra(PublicKeys.KEY_VIA_PATH, fullViaPath));
    }

    @Override
    public void onItemClick(Routes routes) {
        startActivity(MapsActivity.getStarterIntent(this).putExtra(PublicKeys.KEY_DIRECT_PATH, routes));
    }
}

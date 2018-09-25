package com.lyphomed.nishantpatel.projectguestlogix.ui.dashboard;

import com.lyphomed.nishantpatel.projectguestlogix.base.BasePresenter;
import com.lyphomed.nishantpatel.projectguestlogix.data.manager.DataManager;

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
}

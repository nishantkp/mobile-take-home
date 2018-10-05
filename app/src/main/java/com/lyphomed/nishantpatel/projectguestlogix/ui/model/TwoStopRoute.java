package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;

import java.util.List;

/**
 * POJO for list of routes from Origin to firstVia location
 * firstVia to secondVia and secondVia to destination
 * and from Via location to destination
 * <p>
 * FirstRoute : List of all paths from origin to "firstVia"
 * SecondRoute: List of all paths from "firstVia" to "secondVia"
 * ThirdRoute : List of all paths from "secondVia" to destination
 */
public class TwoStopRoute {
    private List<Routes> mFistRoute;
    private List<Routes> mSecondRoute;
    private List<Routes> mThirdRoute;

    public TwoStopRoute(List<Routes> fistRoute, List<Routes> secondRoute, List<Routes> thirdRoute) {
        this.mFistRoute = fistRoute;
        this.mSecondRoute = secondRoute;
        this.mThirdRoute = thirdRoute;
    }

    public List<Routes> getFirstRoute() {
        return mFistRoute;
    }

    public void setFistRoute(List<Routes> fistRoute) {
        this.mFistRoute = fistRoute;
    }

    public List<Routes> getSecondRoute() {
        return mSecondRoute;
    }

    public void setmSecondRoute(List<Routes> secondRoute) {
        this.mSecondRoute = secondRoute;
    }

    public List<Routes> getThirdRoute() {
        return mThirdRoute;
    }

    public void setThirdRoute(List<Routes> thirdRoute) {
        this.mThirdRoute = thirdRoute;
    }
}

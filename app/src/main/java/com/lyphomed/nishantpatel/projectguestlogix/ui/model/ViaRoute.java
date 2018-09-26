package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;

import java.util.List;

/**
 * POJO for list of routes from Origin to Via location
 * and from Via location to destination
 * <p>
 * FirstRoute : List of all paths from origin to "via"
 * SecondRoute: List of all paths from "via" to destination
 */
public class ViaRoute {
    private List<Routes> mFistRoute;
    private List<Routes> mSecondRoute;

    public ViaRoute(List<Routes> fistRoute, List<Routes> secondRoute) {
        this.mFistRoute = fistRoute;
        this.mSecondRoute = secondRoute;
    }

    public List<Routes> getfirstRoute() {
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
}

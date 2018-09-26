package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

/**
 * POJO for path between origin-via-destination
 * It also has flight information like flight code for origin to "via" and
 * "via" to destination
 */
public class FullViaPath {
    private String mOrigin;
    private String mVia;
    private String mDestination;
    private String mOriginToViaFlight;
    private String mViaToDestination;

    public FullViaPath(String origin, String via, String destination, String originToViaFlight, String viaToDestination) {
        this.mOrigin = origin;
        this.mVia = via;
        this.mDestination = destination;
        this.mOriginToViaFlight = originToViaFlight;
        this.mViaToDestination = viaToDestination;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        this.mOrigin = origin;
    }

    public String getVia() {
        return mVia;
    }

    public void setVia(String via) {
        this.mVia = via;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public String getOriginToViaFlight() {
        return mOriginToViaFlight;
    }

    public void setOriginToViaFlight(String originToViaFlight) {
        this.mOriginToViaFlight = originToViaFlight;
    }

    public String getViaToDestination() {
        return mViaToDestination;
    }

    public void setViaToDestination(String viaToDestination) {
        this.mViaToDestination = viaToDestination;
    }
}
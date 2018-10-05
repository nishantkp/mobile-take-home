package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import java.io.Serializable;

/**
 * POJO for path between origin-via-destination
 * It also has flight information like flight code for origin to "via" and
 * "via" to destination
 */
public class FullViaPath implements Serializable {
    private String mOrigin;
    private String mVia;
    private String mFirstVia;
    private String mSecondVia;
    private String mOriginToFirstViaFlight;
    private String mFirstViaToSecondViaFlight;
    private String mSecondViaToDestinationFlight;
    private String mDestination;
    private String mOriginToViaFlight;
    private String mViaToDestinationFlight;

    public FullViaPath(String origin, String via, String destination, String originToViaFlight, String viaToDestinationFlight) {
        this.mOrigin = origin;
        this.mVia = via;
        this.mDestination = destination;
        this.mOriginToViaFlight = originToViaFlight;
        this.mViaToDestinationFlight = viaToDestinationFlight;
    }

    // For two stoppages
    public FullViaPath(String origin, String firstVia, String secondVia, String destination, String originToFirstViaFlight,
                       String firstViaToSecondViaFlight, String secondViaToDestinationFLight) {
        this.mOrigin = origin;
        this.mFirstVia = firstVia;
        this.mSecondVia = secondVia;
        this.mDestination = destination;
        this.mOriginToFirstViaFlight = originToFirstViaFlight;
        this.mFirstViaToSecondViaFlight = firstViaToSecondViaFlight;
        this.mSecondViaToDestinationFlight = secondViaToDestinationFLight;
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

    public String getViaToDestinationFlight() {
        return mViaToDestinationFlight;
    }

    public void setViaToDestination(String viaToDestination) {
        this.mViaToDestinationFlight = viaToDestination;
    }

    public String getFirstVia() {
        return mFirstVia;
    }

    public void setFirstVia(String firstVia) {
        this.mFirstVia = firstVia;
    }

    public String getSecondVia() {
        return mSecondVia;
    }

    public void setmSecondVia(String secondVia) {
        this.mSecondVia = secondVia;
    }

    public String getOriginToFirstViaFlight() {
        return mOriginToFirstViaFlight;
    }

    public void setOriginToFirstViaFlight(String originToFirstViaFlight) {
        this.mOriginToFirstViaFlight = originToFirstViaFlight;
    }

    public String getFirstViaToSecondViaFlight() {
        return mFirstViaToSecondViaFlight;
    }

    public void setFirstViaToSecondViaFlight(String firstViaToSecondViaFlight) {
        this.mFirstViaToSecondViaFlight = firstViaToSecondViaFlight;
    }

    public String getSecondViaToDestinationFlight() {
        return mSecondViaToDestinationFlight;
    }

    public void setSecondViaToDestinationFlight(String secondViaToDestinationFlight) {
        this.mSecondViaToDestinationFlight = secondViaToDestinationFlight;
    }
}

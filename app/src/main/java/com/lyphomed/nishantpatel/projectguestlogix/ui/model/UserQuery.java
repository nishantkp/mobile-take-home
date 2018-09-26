package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * User query object for two-way data binding
 */
public class UserQuery extends BaseObservable {
    private String mOrigin;
    private String mDestination;

    public UserQuery(String origin, String destination) {
        mOrigin = origin;
        mDestination = destination;
    }

    public UserQuery() {
    }

    @Bindable
    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        this.mOrigin = origin;
        notifyPropertyChanged(BR.origin);
    }

    @Bindable
    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
        notifyPropertyChanged(BR.destination);
    }
}

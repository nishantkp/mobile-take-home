package com.lyphomed.nishantpatel.projectguestlogix.base;

public interface Presenter<T extends MvpView> {
    void attachView(T view);

    void detachView();
}

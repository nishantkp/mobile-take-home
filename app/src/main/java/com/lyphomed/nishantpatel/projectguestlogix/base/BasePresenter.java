package com.lyphomed.nishantpatel.projectguestlogix.base;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T view;

    public boolean isViewAvailable() {
        return this.view != null;
    }

    public T getView() {
        return this.view;
    }

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}

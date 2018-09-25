package com.lyphomed.nishantpatel.projectguestlogix.data.local.callbacks;

public interface OnTaskCompletion {
    void onTaskComplete();

    void onError(String message);
}

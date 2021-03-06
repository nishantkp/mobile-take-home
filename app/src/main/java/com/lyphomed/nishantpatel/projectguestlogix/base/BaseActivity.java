package com.lyphomed.nishantpatel.projectguestlogix.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Base activity
 */
public class BaseActivity extends AppCompatActivity {

    public CompositeDisposable baseCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseCompositeDisposable.dispose();
    }
}

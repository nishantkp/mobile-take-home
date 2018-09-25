package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Room database table named "airlines"
 */
@Entity(tableName = "airlines")
public class Airlines {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "two_digit_code")
    private String mTwoDigitCode;

    @ColumnInfo(name = "name")
    private String mAirlineName;

    @ColumnInfo(name = "three_digit_code")
    private String mTreeDigitCode;

    @ColumnInfo(name = "country")
    private String mCountry;

    public Airlines(@NonNull String twoDigitCode, String airlineName, String treeDigitCode, String country) {
        this.mTwoDigitCode = twoDigitCode;
        this.mAirlineName = airlineName;
        this.mTreeDigitCode = treeDigitCode;
        this.mCountry = country;
    }

    @NonNull
    public String getTwoDigitCode() {
        return mTwoDigitCode;
    }

    public void setmTwoDigitCode(@NonNull String twoDigitCode) {
        this.mTwoDigitCode = twoDigitCode;
    }

    public String getAirlineName() {
        return mAirlineName;
    }

    public void setmAirlineName(String airlineName) {
        this.mAirlineName = airlineName;
    }

    public String getTreeDigitCode() {
        return mTreeDigitCode;
    }

    public void setTreeDigitCode(String treeDigitCode) {
        this.mTreeDigitCode = treeDigitCode;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }
}

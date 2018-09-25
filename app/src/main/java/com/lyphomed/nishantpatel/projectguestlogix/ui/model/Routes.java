package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Room database table named "routes"
 */
@Entity(tableName = "routes")
public class Routes {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "airline_code")
    private String mAirlineCode;

    @ColumnInfo(name = "origin")
    private String mOrigin;

    @ColumnInfo(name = "destination")
    private String mDestination;

    public Routes(int id, String airlineCode, String origin, String destination) {
        this.mId = id;
        this.mAirlineCode = airlineCode;
        this.mOrigin = origin;
        this.mDestination = destination;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getAirlineCode() {
        return mAirlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.mAirlineCode = airlineCode;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        this.mOrigin = origin;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }
}

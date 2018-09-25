package com.lyphomed.nishantpatel.projectguestlogix.ui.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Room database table named "airports"
 */
@Entity(tableName = "airports")
public class Airports {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "name")
    private String mAirportName;

    @ColumnInfo(name = "city")
    private String mCity;

    @ColumnInfo(name = "country")
    private String mCountry;

    @ColumnInfo(name = "iata_3")
    private String mAirportCode;

    @ColumnInfo(name = "latitude")
    private String mLatitude;

    @ColumnInfo(name = "longitude")
    private String mLongitude;

    public Airports(int id, String airportName, String city, String country, String airportCode, String latitude, String longitude) {
        this.mId = id;
        this.mAirportName = airportName;
        this.mCity = city;
        this.mCountry = country;
        this.mAirportCode = airportCode;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getAirportName() {
        return mAirportName;
    }

    public void setAirportName(String airportName) {
        this.mAirportName = airportName;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getAirportCode() {
        return mAirportCode;
    }

    public void setAirportCode(String airportCode) {
        this.mAirportCode = airportCode;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        this.mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        this.mLongitude = longitude;
    }
}

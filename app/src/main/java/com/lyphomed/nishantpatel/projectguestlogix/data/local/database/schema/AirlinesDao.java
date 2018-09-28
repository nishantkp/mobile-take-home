package com.lyphomed.nishantpatel.projectguestlogix.data.local.database.schema;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airlines;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.data.local.database.model.Routes;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Database DAO : database interaction methods
 */
@Dao
public interface AirlinesDao {

    /**
     * Use this method to query all the airports in database
     */
    @Query("SELECT * FROM airports")
    Flowable<List<Airports>> getAllAirports();

    /**
     * Use this method to query all the airlines in database
     */
    @Query("SELECT * FROM airlines")
    Flowable<List<Airlines>> getAllAirlines();

    /**
     * Use this method to query all the direct routs by all airlines in database
     */
    @Query("SELECT * FROM routes")
    Flowable<List<Routes>> getAllRoutes();

    /**
     * This will return all the direct paths
     */
    @Query("SELECT * FROM routes WHERE origin LIKE :userOrigin AND destination LIKE :userDestination")
    Flowable<List<Routes>> getDirectRoutes(String userOrigin, String userDestination);

    /**
     * This method will return list all possible paths from origin to via point
     * From that we can easily extract information on "via" location
     */
    @Query("SELECT * FROM routes WHERE origin LIKE :userOrigin AND destination IN (SELECT origin FROM routes WHERE destination LIKE :userDestination) LIMIT 5")
    Flowable<List<Routes>> getPossiblePathsToViaLocation(String userOrigin, String userDestination);

    /**
     * THis method will return airports from IATA-3 codes
     */
    @Query("SELECT * FROM airports WHERE iata_3 LIKE :iata")
    Maybe<Airports> getAirportFromIata(String iata);

    /**
     * Use this method to insert entry into "airports" table
     */
    @Insert
    void insertAirports(Airports... airports);

    /**
     * Use this method to insert entry into "airlines" table
     */
    @Insert
    void insertAirlines(Airlines... airlines);

    /**
     * Use this method to insert entry into "routes" table
     */
    @Insert
    void insertRoutes(Routes... routes);

}

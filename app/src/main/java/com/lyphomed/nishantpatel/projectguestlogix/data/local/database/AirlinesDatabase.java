package com.lyphomed.nishantpatel.projectguestlogix.data.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.lyphomed.nishantpatel.projectguestlogix.config.AppConfig;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Airlines;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Airports;
import com.lyphomed.nishantpatel.projectguestlogix.ui.model.Routes;

/**
 * Room Database Schema
 */
@Database(
        entities = {Airlines.class, Airports.class, Routes.class},
        version = AppConfig.DATABASE_VERSION
)
public abstract class AirlinesDatabase extends RoomDatabase {

    private static AirlinesDatabase sAirlinesDatabase;

    public static AirlinesDatabase getInstance(Context context) {
        if (sAirlinesDatabase == null) {
            sAirlinesDatabase =
                    Room.databaseBuilder(context.getApplicationContext(), AirlinesDatabase.class, AppConfig.DATABASE_NAME)
                            .build();
        }
        return sAirlinesDatabase;
    }

    public abstract AirlinesDao getAirliesDao();
}

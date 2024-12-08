package com.example.papostnatal;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RekamMedis.class}, version = 2)
public abstract class RekamMedisDatabase extends RoomDatabase {
    public abstract RekamMedisDao rekamMedisDao();

}

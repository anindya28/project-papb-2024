package com.example.postnatalcare;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RekamMedisDao {
    @Query("SELECT * FROM RekamMedis")
    List<RekamMedis> getAll();

    @Insert
    void insertRekamMedis (RekamMedis rekamMedis);

    @Query("SELECT * FROM RekamMedis ORDER BY id DESC LIMIT 1")
    RekamMedis getLatestRekamMedis();

    @Query("SELECT * FROM RekamMedis ORDER BY id DESC LIMIT 3")
    List<RekamMedis> getLastThree();
}

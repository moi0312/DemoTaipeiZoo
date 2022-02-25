package com.edlo.mydemoapp.repository.local

import androidx.room.*
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData

@Dao
interface TaipeiZooDao {
    @Query("SELECT *, rowid FROM table_pavilion")
    suspend fun getAllPavilions(): List<PavilionData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPavilions(pavilions: List<PavilionData>)

    @Update
    suspend fun updatePavilion(pavilion: PavilionData)

    @Delete
    fun deletePavilion(pavilion: PavilionData)

    //plant

    @Query("SELECT *, rowid FROM table_plant")
    suspend fun getAllPlants(): List<PlantData>

    @Query("SELECT *, rowid FROM table_plant WHERE location LIKE (:location) ORDER BY rowid ASC")
    suspend fun findPlantByLocation(location: String): List<PlantData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plant: List<PlantData>)

    @Update
    suspend fun updatePlant(plant: PlantData)

    @Delete
    fun deletePlant(plant: PlantData)
}
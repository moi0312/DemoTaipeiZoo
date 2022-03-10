package com.edlo.mydemoapp.repository.local

import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TpzDbHelper @Inject constructor() {
    companion object {
        const val DB_ENABLED = true //
    }

    @Inject lateinit var taipeiZooDB: TaipeiZooDB
    private val dao by lazy { taipeiZooDB.taipeiZooDao() }

    suspend fun listPavilions(): List<PavilionData>? {
        return if(DB_ENABLED) dao.getAllPavilions()
            else null
    }

    suspend fun listPlants(): List<PlantData>? {
        return if(DB_ENABLED) dao.getAllPlants()
            else null
    }

    suspend fun insertPavilions(list: List<PavilionData>): List<PavilionData>? {
        return if(DB_ENABLED) {
            dao.insertAllPavilions(list)
            listPavilions()
        } else null
    }

    suspend fun insertPlants(list: List<PlantData>): List<PlantData>? {
        return if (DB_ENABLED) {
            dao.insertAllPlants(list)
            listPlants()
        } else null
    }

}
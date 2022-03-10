package com.edlo.mydemoapp.repository

import com.edlo.mydemoapp.repository.local.TpzDbHelper
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.TpzApiHelper
import com.edlo.mydemoapp.repository.data.PavilionData
import com.edlo.mydemoapp.repository.data.PlantData
import com.edlo.mydemoapp.repository.net.TPZBaseResponse
import javax.inject.Inject
import javax.inject.Singleton

typealias TPZApiResponse<R> = ApiResult<TPZBaseResponse<R>, Error>
typealias TPZResponse<R> = ApiResult<R, Error>

@Singleton
class TPZRepositoryHelper @Inject constructor() {

    @Inject lateinit var tpzApiHelper: TpzApiHelper
    @Inject lateinit var tpzDBHelper: TpzDbHelper

    suspend fun listPavilions(): TPZResponse<List<PavilionData>> {
        return when (val apiResult = tpzApiHelper.listPavilions()) {
            is ApiResult.Success -> {
                var result = tpzDBHelper.insertPavilions(apiResult.body)
                if(result == null) {
                    result = apiResult.body
                }
                ApiResult.Success(result)
            }
            is ApiResult.NetworkError -> {
                ApiResult.Success(tpzDBHelper.listPavilions() ?: arrayListOf())
            }
            else -> { apiResult }
        }
    }

    suspend fun listPlants(): TPZResponse<List<PlantData>> {
        return when (val apiResult = tpzApiHelper.listPlants()) {
            is ApiResult.Success -> {
                var result = tpzDBHelper.insertPlants(apiResult.body)
                if(result == null) {
                    result = apiResult.body
                }
                ApiResult.Success(result)
            }
            is ApiResult.NetworkError -> {
                ApiResult.Success(tpzDBHelper.listPlants() ?: arrayListOf())
            }
            else -> { apiResult }
        }
    }

    suspend fun listPlantsByLocation(location: String, localFirst: Boolean = false): TPZResponse<List<PlantData>> {
        if(localFirst) {
            val localResult = tpzDBHelper.listPlants(location)
            if (!localResult.isNullOrEmpty()) {
                return ApiResult.Success(localResult)
            }
        }
        return when (val apiResult = tpzApiHelper.listPlants()) {
            is ApiResult.Success -> {
                var result = tpzDBHelper.insertPlants(apiResult.body, location)
                if(result == null) {
                    result = apiResult.body.filter { it.location.contains(location) }
                }
                ApiResult.Success(result)
            }
            is ApiResult.NetworkError -> {
                ApiResult.Success(tpzDBHelper.listPlants(location) ?: arrayListOf())
            }
            else -> { apiResult }
        }
    }

}
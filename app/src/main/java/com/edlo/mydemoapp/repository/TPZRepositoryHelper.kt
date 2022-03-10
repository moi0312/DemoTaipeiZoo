package com.edlo.mydemoapp.repository

import com.edlo.mydemoapp.repository.local.TpzDbHelper
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.TpzApiHelper
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.TPZBaseResponse
import com.edlo.mydemoapp.util.Log
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

}
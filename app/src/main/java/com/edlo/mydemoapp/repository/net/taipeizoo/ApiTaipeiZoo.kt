package com.edlo.mydemoapp.repository.net.taipeizoo

import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.TPZBaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

object ApiTaipeiZoo {
    const val BASE_URL = "https://data.taipei/api/"

    const val SCOPE_RESOURCE_AQUIRE = "resourceAquire"

    const val GET_PLANT_INFO = "v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=$SCOPE_RESOURCE_AQUIRE"
    const val GET_PAVILION_INTRO = "v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=$SCOPE_RESOURCE_AQUIRE"
}

interface ApiTaipeiZooService {

    @GET(ApiTaipeiZoo.GET_PLANT_INFO)
    suspend fun listPlants(@Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): TPZResponse<List<PlantData>>

    @GET(ApiTaipeiZoo.GET_PAVILION_INTRO)
    suspend fun listPavilions(@Query("limit") limit: Int? = null, @Query("offset") offset: Int? = null): TPZResponse<List<PavilionData>>
}

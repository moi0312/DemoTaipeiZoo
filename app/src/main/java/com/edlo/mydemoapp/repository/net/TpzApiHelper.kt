package com.edlo.mydemoapp.repository.net

import com.edlo.mydemoapp.BuildConfig
import com.edlo.mydemoapp.repository.TPZApiResponse
import com.edlo.mydemoapp.repository.TPZResponse
import com.edlo.mydemoapp.repository.data.PavilionData
import com.edlo.mydemoapp.repository.data.PlantData
import com.edlo.mydemoapp.repository.local.TaipeiZooDB
import com.edlo.mydemoapp.repository.net.taipeizoo.ApiTaipeiZoo
import com.edlo.mydemoapp.repository.net.taipeizoo.ApiTaipeiZooService
import com.edlo.mydemoapp.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TpzApiHelper @Inject constructor() {

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit

    private var service: ApiTaipeiZooService

    init {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                var reqBuilder = chain.request().newBuilder()
                    .url(chain.request().url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                chain.proceed(reqBuilder.build())
            }
        if( BuildConfig.PRINT_LOG ) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY))
        }
        okHttpClient = okHttpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(ApiTaipeiZoo.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiCallAdapterFactory())
            .client(okHttpClient)
            .build()
        service = retrofit.create(ApiTaipeiZooService::class.java)
    }

    private fun <T> handleApiResult(apiResult: TPZApiResponse<T>): TPZResponse<TPZBaseResponse<T>> {
        return when (apiResult) {
            is ApiResult.Success -> {
                ApiResult.Success(apiResult.body)
            }
            is ApiResult.NetworkError -> {
                ApiResult.NetworkError(apiResult.error)
            }
            is ApiResult.ApiError -> {
                Log.e(msg = "listPavilions fail: ApiError -> code: ${apiResult.code}, ${apiResult.body}" )
                ApiResult.ApiError(apiResult.body, apiResult.code)
            }
            is ApiResult.GenericError -> {
                apiResult.error?.let {
                    Log.e(msg = "listPavilions fail: GenericError -> ${it.message}" )
                }
                ApiResult.GenericError(apiResult.error)
            }
        }
    }

    suspend fun listPavilions(): TPZResponse<List<PavilionData>> {
        return when (val apiResult = handleApiResult(service.listPavilions())) {
            is ApiResult.Success -> {
                ApiResult.Success(apiResult.body.result.results)
            }
            else -> { apiResult as TPZResponse<List<PavilionData>> }
        }
    }

    suspend fun listPlants(): TPZResponse<List<PlantData>> {
        return when (val apiResult = handleApiResult(service.listPlants())) {
            is ApiResult.Success -> {
                ApiResult.Success(apiResult.body.result.results)
            }
            else -> { apiResult as TPZResponse<List<PlantData>> }
        }
    }
}
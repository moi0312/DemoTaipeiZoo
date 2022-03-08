package com.edlo.mydemoapp.repository.net.taipeizoo

import com.edlo.mydemoapp.BuildConfig
import com.edlo.mydemoapp.repository.net.ApiCallAdapterFactory
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.TPZBaseResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

typealias TPZResponse<R> = ApiResult<TPZBaseResponse<R>, Error>

@Singleton
class ApiTaipeiZooHelper @Inject constructor() {

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

    suspend fun listPavilions(): TPZResponse<List<PavilionData>> {
        return service.listPavilions()
    }

    suspend fun listPlants(): TPZResponse<List<PlantData>> {
        return service.listPlants()
    }
}
package com.edlo.mydemoapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edlo.mydemoapp.repository.local.TaipeiZooDB
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.taipeizoo.ApiTaipeiZooHelper
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import com.edlo.mydemoapp.ui.base.BaseViewModel
import com.edlo.mydemoapp.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaipeiZooViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var apiTaipeiZooHelper: ApiTaipeiZooHelper
    @Inject lateinit var taipeiZooDB: TaipeiZooDB

    private val _currentSelectedPavilion = MutableLiveData<PavilionData>()
    val currentSelectedPavilion: LiveData<PavilionData> = _currentSelectedPavilion
    fun setCurrentSelectedPavilion(data: PavilionData?) {
        _currentSelectedPavilion.postValue(data)
        if (data != null) {
            onPavilionSelected.onNext(data)
        }
    }

    private val _currentSelectedPlant = MutableLiveData<PlantData>()
    val currentSelectedPlant: LiveData<PlantData> = _currentSelectedPlant
    fun setCurrentSelectedPlant(data: PlantData?) {
        _currentSelectedPlant.postValue(data)
        if (data != null) {
            onPlantSelected.onNext(data)
        }
    }

    val onPavilionSelected: PublishSubject<PavilionData> = PublishSubject.create()
    val onPlantSelected: PublishSubject<PlantData> = PublishSubject.create()

    private var pavilions: MutableLiveData<ArrayList<PavilionData>> = MutableLiveData(ArrayList())
    fun getPavilions(): LiveData<ArrayList<PavilionData>> { return pavilions }

    private var plants: MutableLiveData<ArrayList<PlantData>> = MutableLiveData(ArrayList())
    fun getPlants(): LiveData<ArrayList<PlantData>> { return plants }

    fun listPavilions(forceReload: Boolean = false) {
        if (!forceReload && (pavilions.value != null && pavilions.value!!.size>0)) {
            viewModelScope.launch {
                postPavilionsFromLocal()
            }
            return
        }
        onLoading.onNext(true)
        viewModelScope.launch {
            var dao = taipeiZooDB.taipeiZooDao()
            when (val apiResult = apiTaipeiZooHelper.listPavilions()) {
                is ApiResult.Success -> {
                    val baseResponse = apiResult.body.result
                    val pavilions = baseResponse.results
                    dao.insertAllPavilions(pavilions)
                    postPavilionsFromLocal()
                }
                is ApiResult.NetworkError -> {
                    postPavilionsFromLocal()
                }
                is ApiResult.ApiError -> {
                    onLoading.onNext(false)
                    Log.e(msg = "listData fail: ApiError -> code: ${apiResult.code}, ${apiResult.body}" )
                }
                is ApiResult.GenericError -> {
                    onLoading.onNext(false)
                    apiResult.error?.let {
                        Log.e(msg = "listData fail: GenericError -> ${it.message}" )
                    }
                }
            }
        }
    }

    fun listPlants(forceReload: Boolean = false) {
        if(!forceReload && (plants.value != null && plants.value!!.size>0)) {
            viewModelScope.launch {
                postPlantsFromLocal()
            }
            return
        }
        onLoading.onNext(true)
        viewModelScope.launch {
            var dao = taipeiZooDB.taipeiZooDao()
            when (val apiResult = apiTaipeiZooHelper.listPlants()) {
                is ApiResult.Success -> {
                    val baseResponse = apiResult.body.result
                    dao.insertAllPlants(baseResponse.results)
                    postPlantsFromLocal()
                }
                is ApiResult.NetworkError -> {
                    postPlantsFromLocal()
                }
                is ApiResult.ApiError -> {
                    onLoading.onNext(false)
                    Log.e(msg = "listData fail: ApiError -> code: ${apiResult.code}, ${apiResult.body}" )
                }
                is ApiResult.GenericError -> {
                    onLoading.onNext(false)
                    apiResult.error?.let {
                        Log.e(msg = "listData fail: GenericError -> ${it.message}" )
                    }
                }
            }
        }
    }

    private suspend fun postPavilionsFromLocal() {
        viewModelScope.launch {
            var dao = taipeiZooDB.taipeiZooDao()
            val result = dao.getAllPavilions() as ArrayList<PavilionData>
            pavilions.postValue(result)
            onLoading.onNext(false)
        }
    }

    private suspend fun postPlantsFromLocal() {
        viewModelScope.launch {
            var dao = taipeiZooDB.taipeiZooDao()
            val result = currentSelectedPavilion.value?.let { dao.findPlantByLocation(it?.name) } as ArrayList<PlantData>
            plants.postValue(result)
            onLoading.onNext(false)
        }
    }
}

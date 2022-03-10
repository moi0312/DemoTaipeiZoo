package com.edlo.mydemoapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edlo.mydemoapp.repository.TPZRepositoryHelper
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.data.PavilionData
import com.edlo.mydemoapp.repository.data.PlantData
import com.edlo.mydemoapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaipeiZooViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var tpzRepositoryHelper: TPZRepositoryHelper

    private var pavilions: MutableLiveData<ArrayList<PavilionData>> = MutableLiveData(ArrayList())
    fun getPavilions(): LiveData<ArrayList<PavilionData>> { return pavilions }

    private var allPlants: MutableLiveData<ArrayList<PlantData>> = MutableLiveData(ArrayList())
    private var plants: MutableLiveData<ArrayList<PlantData>> = MutableLiveData(ArrayList())
    fun getPlants(): LiveData<ArrayList<PlantData>> { return plants }

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

    fun listPavilions(forceReload: Boolean = false) {
        if(!forceReload && pavilions.value != null && pavilions.value!!.size > 0) {
            return
        }
        onLoading.onNext(true)
        viewModelScope.launch {
            when (val apiResult = tpzRepositoryHelper.listPavilions()) {
                is ApiResult.Success -> {
                    pavilions.postValue(apiResult.body as ArrayList<PavilionData>?)
                    onLoading.onNext(false)
                }
                else -> { onLoading.onNext(false) }
            }
        }
    }

    fun listPlants() {
        onLoading.onNext(true)
        plants.postValue(arrayListOf())
        viewModelScope.launch {
            val location = currentSelectedPavilion.value!!.name
            if(allPlants.value != null && allPlants.value!!.size > 0) {
                when (val localResult = tpzRepositoryHelper.listPlantsByLocation(location, true)) {
                    is ApiResult.Success -> {
                        plants.postValue(localResult.body as ArrayList<PlantData>?)
                        onLoading.onNext(false)
                    }
                    else -> { onLoading.onNext(false) }
                }
            } else {
                when (val apiResult = tpzRepositoryHelper.listPlants()) {
                    is ApiResult.Success -> {
                        allPlants.postValue(apiResult.body as ArrayList<PlantData>?)
                        plants.postValue( apiResult.body.filter { it.location.contains(location) } as ArrayList<PlantData>?)
                        onLoading.onNext(false)
                    }
                    else -> { onLoading.onNext(false) }
                }
            }

//            when (val apiResult = tpzRepositoryHelper.listPlantsByLocation(currentSelectedPavilion.value!!.name, localFirst)) {
//                is ApiResult.Success -> {
//                    plants.postValue(apiResult.body as ArrayList<PlantData>?)
//                    onLoading.onNext(false)
//                }
//                else -> { onLoading.onNext(false) }
//            }
        }
    }
}

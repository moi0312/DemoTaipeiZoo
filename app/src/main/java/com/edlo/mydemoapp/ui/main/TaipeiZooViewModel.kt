package com.edlo.mydemoapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.edlo.mydemoapp.repository.TPZRepositoryHelper
import com.edlo.mydemoapp.repository.net.ApiResult
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PavilionData
import com.edlo.mydemoapp.repository.net.taipeizoo.data.PlantData
import com.edlo.mydemoapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaipeiZooViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var tpzRepositoryHelper: TPZRepositoryHelper

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

    fun listPavilions() {
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
        viewModelScope.launch {
            when (val apiResult = tpzRepositoryHelper.listPlants()) {
                is ApiResult.Success -> {
                    plants.postValue(apiResult.body as ArrayList<PlantData>?)
                    onLoading.onNext(false)
                }
                else -> { onLoading.onNext(false) }
            }
        }
    }
}

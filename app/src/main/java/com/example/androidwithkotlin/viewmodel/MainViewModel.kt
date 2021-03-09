package com.example.androidwithkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidwithkotlin.model.Repository
import com.example.androidwithkotlin.model.RepositoryImpl
import com.example.androidwithkotlin.model.Weather
import java.lang.Thread.sleep

class MainViewModel(    // подписываемся на неё
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),   // данные на которые подписываемся
    private val repositoryImpl: Repository = RepositoryImpl() // возвращает погоду
) :
    ViewModel() { // возвращаемый тип

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true) // получение данных

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            val listWeather: List<Weather> = if (isRussian) // если изменились данные
                repositoryImpl.getWeatherFromLocalStorageRus()
            else
                repositoryImpl.getWeatherFromLocalStorageWorld()

            liveDataToObserve.postValue(AppState.Success(listWeather))
        }.start()
    }

}

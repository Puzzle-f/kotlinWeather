package com.example.androidwithkotlin.view

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.androidwithkotlin.R
import com.example.androidwithkotlin.databinding.FragmentDetailsBinding
import com.example.androidwithkotlin.model.Weather
import com.example.androidwithkotlin.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val YOUR_API_KEY = "4b457ddd-6e15-42cb-865c-4c0aad7ce140"

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        arguments?.getParcelable<Weather>(BUNDLE_EXTRA)?.let { weather ->
//            weather.city.also { city ->
//                binding.cityName.text = city.city
//                binding.cityCoordinates.text = String.format(
//                    getString(R.string.city_coordinates),
//                    city.lat.toString(),
//                    city.lon.toString()
//                )
//                binding.temperatureValue.text = weather.temperature.toString()
//                binding.feelsLikeValue.text = weather.feelsLike.toString()
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        loadWeather()
    }

    companion object {
        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun displayWeather(weatherDTO: WeatherDTO) {    // отображает полученные данные
        with(binding) {
            mainView.visibility = View.VISIBLE
            loadingLayout.visibility = View.GONE
            val city = weatherBundle.city
            cityName.text = city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                city.lat.toString(),
                city.lon.toString()
            )
            weatherCondition.text = weatherDTO.fact?.condition
            temperatureValue.text = weatherDTO.fact?.temp.toString()
            feelsLikeValue.text = weatherDTO.fact?.feels_like.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadWeather() {
        try {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
            val handler = Handler()  // запомнили главный поток
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection  // объявление HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection // открытие Connection
                    urlConnection.requestMethod = "GET"  // устанавливаем метод GET для запроса
                    urlConnection.addRequestProperty(   // устанавливаем нащ ключ. добавляется заголовок в запрос
                        "X-Yandex-API-Key", // ?????
                        YOUR_API_KEY
                    )
                    urlConnection.readTimeout = 10000   // установка таймаута для ограничения времени загрузки
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
                    val weatherDTO: WeatherDTO =
                        Gson().fromJson(getLines(bufferedReader), WeatherDTO::class.java)   // fromJson десериализует Gson в указанный тип (WeatherDTO)
                    handler.post { displayWeather(weatherDTO) } // в основном потоке отображаем данные. post добавляет в очередь на выполнение в потоке задачу
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    //Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            //Обработка ошибки
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)  // аннотация нужна для вызова метода lines(), так как метод добавлен только с 30 API
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }



}

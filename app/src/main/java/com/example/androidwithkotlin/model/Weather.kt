package com.example.androidwithkotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity()

): Parcelable

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)

fun getWorldCities()=  listOf(
            Weather(City("Лондон", 51.5085300, -0.1257400)),
            Weather(City("Токио", 35.6895000, 139.6917100)),
            Weather(City("Париж", 48.8534100, 2.3488000)),
            Weather(City("Берлин", 52.52000659999999, 13.404953999999975)),
            Weather(City("Рим", 41.9027835, 12.496365500000024)),
            Weather(City("Минск", 53.90453979999999, 27.561524400000053)),
            Weather(City("Стамбул", 41.0082376, 28.97835889999999)),
            Weather(City("Вашингтон", 38.9071923, -77.03687070000001)),
            Weather(City("Киев", 50.4501, 30.523400000000038)),
            Weather(City("Пекин", 39.90419989999999, 116.40739630000007))
    )

fun getRussianCities() = listOf(
            Weather(City("Ростов-на-Дону", 47.2357137, 39.701505)),
            Weather(City("Новочеркасск", 47.422052, 40.093725)),
            Weather(City("Шахты", 47.709601, 40.215797)),
            Weather(City("Горная", 47.709601, 40.215797)),
            Weather(City("Сулин", 47.894709, 40.057990)),
            Weather(City("Зверево", 48.042848, 40.126396)),
            Weather(City("Лихая", 48.152019, 40.182379)),
            Weather(City("Каменская", 48.320515, 40.268923)),
            Weather(City("Миллерово", 48.921730, 40.394849)),
            Weather(City("Кутейниково", 49.397223, 40.469463)),
            Weather(City("Зайцевка", 49.686674, 40.000929)),
            Weather(City("Журавка", 50.046609, 40.595766)),
            Weather(City("Россошь", 50.198256, 39.562632)),
            Weather(City("Сагуны", 50.596918, 39.725748)),
            Weather(City("Лиски", 50.987298, 39.497099)),
            Weather(City("Москва", 55.755826, 37.617299900000035)),
            Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038)),
            Weather(City("Хабаровск", 48.480223, 135.071917)),
            Weather(City("Сочи", 43.585472, 39.723089))
    )

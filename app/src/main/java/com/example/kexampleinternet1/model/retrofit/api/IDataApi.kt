package com.example.kexampleinternet1.model.retrofit.api

import com.example.kexampleinternet1.BuildConfig
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataApi {

    // Касательно api_key - в gradle добавлен код на groove, который отрабатывается во время
    // компиляции, а именно автоматически генерирует файл BuildConfig и добавляет туда
    // константу - public static final String NASA_API_KEY = "DEMO_KEY";


    // тут то, что в запросе api после слэша
    @GET( "/planetary/apod?date=2022-05-02")
    fun getApi(@Query("api_key") key: String = BuildConfig.NASA_API_KEY): Single<APODResponseDTO>
    // DTO тут в треугольных скобках
    // Single тут из RXJAVA

    // ?date=2022-05-02 в запросе поставлен, т.к. не всегда today - это картинка (может видео)

}
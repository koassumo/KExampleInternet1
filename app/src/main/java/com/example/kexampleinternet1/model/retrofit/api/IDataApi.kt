package com.example.kexampleinternet1.model.retrofit.api

import com.example.kexampleinternet1.BuildConfig
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataApi {

    // !!! Все классы, относящиеся к Retrofit в этом проекте, помечены на конце -Api

    // class APODLoader удален.

    // Касательно api_key - в gradle добавлен код на groove, который отрабатывается во время
    // компиляции, а именно автоматически генерирует файл BuildConfig и добавляет туда
    // константу - public static final String NASA_API_KEY = "DEMO_KEY";


    // тут то, что в запросе api после слэша
    @GET( "/planetary/apod")
    fun getApi(@Query("api_key") key: String = BuildConfig.NASA_API_KEY): Single<APODResponseApi>
    // DTO тут в треугольных скобках
    // Single тут из RXJAVA

}
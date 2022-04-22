package com.example.kexampleinternet1.model.retrofit.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataApi {

    // !!! Все классы, относящиеся к Retrofit в этом проекте, помечены на конце -Api

    // тут то, что в запросе api после слэша
    @GET( "/planetary/apod?api_key=DEMO_KEY")
    fun getApi(): Single<APODResponseApi>
    // DTO тут в треугольных скобках
    // Single тут из RXJAVA
}
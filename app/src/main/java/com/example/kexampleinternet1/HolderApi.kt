package com.example.kexampleinternet1

import com.example.kexampleinternet1.model.retrofit.api.IDataApi
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class HolderApi {

//    var interceptor = Interceptor { chain ->
//        val newRequest: Request =
//            chain.request().newBuilder().addHeader("api_key", "NASA_API_KEY").build()
//        chain.proceed(newRequest)
//    }

//    OkHttpClient.Builder builder = new OkHttpClient.Builder();
//    builder.interceptors().add(interceptor);
//    OkHttpClient client = builder.build();

    // собираем полный api-запрос в объект dataApi:
    // здесь определяем базовый url + подключаем интерфейс IDataApi из модели

    val dataApi: IDataApi by lazy {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()

        Retrofit.Builder()
            .baseUrl("https://api.nasa.gov")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IDataApi::class.java)
    }


}
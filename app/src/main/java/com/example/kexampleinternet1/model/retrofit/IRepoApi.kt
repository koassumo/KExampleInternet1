package com.example.kexamplerecycleview.model.repo


import com.example.kexampleinternet1.model.retrofit.api.APODResponseApi
import io.reactivex.rxjava3.core.Single

interface IRepoApi {
    fun getRepoApi(): Single<APODResponseApi>
}
package com.example.kexamplerecycleview.model.repo


import com.example.kexampleinternet1.model.retrofit.api.APODResponseDTO
import io.reactivex.rxjava3.core.Single

interface IRetrofitAPODRepo {
    fun getRepoApi(): Single<APODResponseDTO>
}
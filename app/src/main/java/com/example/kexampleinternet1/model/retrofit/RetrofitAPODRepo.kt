package com.example.kexamplerecycleview.model.repo.retrofit

import com.example.kexampleinternet1.model.retrofit.api.IDataApi
import com.example.kexamplerecycleview.model.repo.IRepoApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


data class RetrofitAPODRepo(val api: IDataApi) : IRepoApi {
    override fun getRepoApi() =
        api.getApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())



}
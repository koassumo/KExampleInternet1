package com.example.kexampleinternet1.model.retrofit.api

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class APODResponseDTO(

    // тут такой вариант DTO

    @Expose
    @SerializedName ("copyright")
    val copyright: String?,

    @Expose
    @SerializedName ("date")   //здесь как на сервере, если ниже будет другое имя
    val date: String?,              //здесь можно другое имя

    @Expose
    val explanation: String?,

    @Expose
    val url: String?,

    @Expose
    val hdurl: String?,

    @Expose
    val mediaType: String?,

    @Expose
    val serviceVersion: String?,

    @Expose
    val title: String?,
): Parcelable

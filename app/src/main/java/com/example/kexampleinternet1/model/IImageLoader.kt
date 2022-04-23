package com.example.kexampleinternet1.model

interface IImageLoader <T>{
    fun loadInto (url: String, container: T)
}
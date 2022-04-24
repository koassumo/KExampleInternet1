package com.example.kexampleinternet1.model

interface IGladeImageLoader <T>{
    fun loadInto (url: String, container: T)
}
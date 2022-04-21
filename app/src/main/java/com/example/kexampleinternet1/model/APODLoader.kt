package com.example.kexampleinternet1.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class APODLoader(
    // Для лоудерера по сути лисенер является обезличенным.
    // Лоудер знает, что лисенер есть, у него 2 метода (callback-а) и в них нужно что-то отправить.
    // Лоудер знает методы, пч интерфейс листенера описан у него же в классе,
    // ну а фрагмент знает о методах, пч что он создает листенера и оверрайдит методы.
    // А уже каждый фрагмент наполняет листенера различными особенностями
    // (которые работают по получению callback от лоудера)
    private val onLoadListener: APODLoaderListener
) {

    // главная задача фрагмента - получить данные для отображения,
    // данные могут грузиться какое-то время, нужно чтобы за этим кто-то следил.
    // Прежде чем создать лоудера - создаем листенер на основе интерфейса в классе лоудера
    // Интерфейс будем использовать в качестве метода обратного вызова (callback-уведомление)
    // с результатами загрузки
    interface APODLoaderListener {
        fun onLoaded(apodResponseDTO: APODResponseDTO)
        fun onFailed(throwable: Throwable)
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun loadAPOD() {
        try {
            val uri =
            // URL("https://api.weather.yandex.ru/v2/informers?lat=${weatherBundle.city.lat}&lon=${weatherBundle.city.lon}")
                //URL("https://api.nasa.gov/planetary/apod?api_key=a8bApA....")
                URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")
            //val handler = Handler()

            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    //urlConnection.addRequestProperty("api_key", BuildConfig.NASA_API_KEY)
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))
                    //urlConnection.addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)

                    // преобразование ответа от сервера (JSON) в модель данных (DTO)
                    val apodResponseDTO: APODResponseDTO =
                        Gson().fromJson(getLines(bufferedReader), APODResponseDTO::class.java)
                    //handler.post { displayWeather(apodResponseDTO) }

                    onLoadListener.onLoaded(apodResponseDTO)
//                    requireActivity().runOnUiThread {
//                        //tv_title_apod.text = apodResponseDTO.fact?.title
//                        //iv_url_apod.setImageResource(R.drawable.apod_temp)
//                        //displayWeather(apodResponseDTO)
//                    }

                } catch (e: Exception) {
                    Log.e(
                        "",
                        "Fail connection---------------------------------------------------------",
                        e
                    )
                    e.printStackTrace()
                    //Обработка ошибки
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e(
                "",
                "Fail URI++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++",
                e
            )
            e.printStackTrace()
            //Обработка ошибки
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }


}
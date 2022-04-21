package com.example.kexampleinternet1.ui.detailed

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.kexampleinternet1.R
import com.example.kexampleinternet1.model.APODResponseDTO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.detailed_fragment.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class DetailedFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedFragment()
    }

    private lateinit var viewModel: DetailedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailedViewModel::class.java)
        loadAPOD()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadAPOD() {
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

                    requireActivity().runOnUiThread {
                        tv_title_apod.text = apodResponseDTO.title
                        tv_copyright_apod.text = "\u00A9 ${apodResponseDTO.copyright}"
                        tv_date_apod.text = apodResponseDTO.date
                        tv_explanation_apod.text = apodResponseDTO.explanation
                        //tv_title_apod.text = apodResponseDTO.fact?.title
                        //iv_url_apod.setImageResource(R.drawable.apod_temp)
                        //displayWeather(apodResponseDTO)
                    }

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
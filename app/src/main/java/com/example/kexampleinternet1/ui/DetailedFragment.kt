package com.example.kexampleinternet1.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import coil.load
import com.example.kexampleinternet1.HolderApi
import com.example.kexampleinternet1.R
import com.example.kexampleinternet1.model.retrofit.api.APODResponseDTO
import com.example.kexamplerecycleview.model.repo.retrofit.RetrofitAPODRepo
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.detailed_fragment.*

// В данном проекте некот.классы для retrofit помечены также как Api.

// 0. Не забываем про permission и про app.properties с ключом api

// Для работы retrofit:
// 1. Зависимости в gradle (включая rxjava)
// 2. В моделе создать DTO версию объекта для распарсинга. Особенности - анотации (@Parcelize():
//    Parcelable, @Expose, @SerializedName).
// 3. Рядом интерфейс (IDataApi), в котором сам запрос api (в строке url -это вторая часть после слэш)

// 4. Создать ApiHolder (HolderApi), который с помощью билдера собирает полный api-запрос в объект dataApi:
//    базовый url, находящийся в ApiHolder, + подключается интерфейс IDataApi из модели
// 5. Создаем класс обращения к серверу (RetrofitAPODRepo) на основе интерфейса IRepoApi
//    Данный класс использует rxjava, schedulers, автоматизирует все соединение
// 6. Во фрагменте оформляется подписка на single rxjava (т.е. [2] и [4])
//    Кстати callback [1] получается, что тоже прямо во фрагменте задается


class DetailedFragment : Fragment() {

    companion object {
        fun newInstance() = DetailedFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detailed_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anySingle: Single<APODResponseDTO> = RetrofitAPODRepo(HolderApi().dataApi).getRepoApi()
        anySingle.subscribe({
            println(it)
            displayData(it)
        }, {
            println("onError: ${it.message}-----------------------------------------------")
        })
    }


    private fun displayData(apodResponseDTO: APODResponseDTO) {
        iv_url_apod.load (apodResponseDTO.url)
        tv_title_apod.text = apodResponseDTO.title
        tv_copyright_apod.text = "\u00A9 ${apodResponseDTO.copyright}"
        tv_date_apod.text = apodResponseDTO.date
        tv_explanation_apod.text = apodResponseDTO.explanation
    }

}
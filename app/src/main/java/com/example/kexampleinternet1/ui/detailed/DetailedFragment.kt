package com.example.kexampleinternet1.ui.detailed

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.kexampleinternet1.HolderApi
import com.example.kexampleinternet1.R
import com.example.kexampleinternet1.model.APODLoader
import com.example.kexampleinternet1.model.APODResponseDTO
import com.example.kexampleinternet1.model.retrofit.api.APODResponseApi
import com.example.kexamplerecycleview.model.repo.retrofit.RetrofitAPODRepo
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.detailed_fragment.*


// Для работы retrofit:
// 1. Зависимости в gradle (включая rxjava)
// 2. В моделе создать DTO версию объекта для распарсинга. Особенности - анотации (@Parcelize():
//    Parcelable, @Expose, @SerializedName). В данном проекте все классы для retrofit помечены как Api.
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


//        // 1) создаем листенер на основе интерфейса в классе лоудера и
//        // переопределяем его методы
//        val myLoadListener: APODLoader.APODLoaderListener =
//            object : APODLoader.APODLoaderListener {
//                override fun onLoaded(apodResponseDTO: APODResponseDTO) {
//                    // "особенность", "имплементация" конкретного листенера -
//                    // По аналогии с MVVM это действие [4]
//                    displayData(apodResponseDTO)
//                }
//
//                override fun onFailed(throwable: Throwable) {
//                    //Обработка ошибки
//                }
//            }
//
//        // 2) создаем сам лоудер, передаем ему листенера
//        // По аналогии с MVVM это, наверное действия [2] и [1]
//        val loader = APODLoader(myLoadListener)
//        loader.loadAPOD()
//        // т.е. запускаем лоудер

        val anySingle: Single<APODResponseApi> = RetrofitAPODRepo(HolderApi().dataApi).getRepoApi()
        anySingle.subscribe({
            println(it)
            displayData(it)
        }, {
            println("onError: ${it.message}-----------------------------------------------")
        })



    }

//    private fun displayData (apodResponseDTO: APODResponseDTO) {
//        requireActivity().runOnUiThread {
//            tv_title_apod.text = apodResponseDTO.title
//            tv_copyright_apod.text = "\u00A9 ${apodResponseDTO.copyright}"
//            tv_date_apod.text = apodResponseDTO.date
//            tv_explanation_apod.text = apodResponseDTO.explanation
//        }
//    }

    private fun displayData (apodResponseApi: APODResponseApi) {
            tv_title_apod.text = apodResponseApi.title
            tv_copyright_apod.text = "\u00A9 ${apodResponseApi.copyright}"
            tv_date_apod.text = apodResponseApi.date
            tv_explanation_apod.text = apodResponseApi.explanation
    }


}
package com.example.kexampleinternet1.ui.detailed

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.kexampleinternet1.R
import com.example.kexampleinternet1.model.APODLoader
import com.example.kexampleinternet1.model.APODResponseDTO
import kotlinx.android.synthetic.main.detailed_fragment.*

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


        // 1) создаем листенер на основе интерфейса в классе лоудера и
        // переопределяем его методы
        val myLoadListener: APODLoader.APODLoaderListener =
            object : APODLoader.APODLoaderListener {
                override fun onLoaded(apodResponseDTO: APODResponseDTO) {
                    // "особенность", "имплементация" конкретного листенера -
                    // По аналогии с MVVM это действие [4]
                    displayData(apodResponseDTO)
                }

                override fun onFailed(throwable: Throwable) {
                    //Обработка ошибки
                }
            }

        // 2) создаем сам лоудер, передаем ему листенера
        // По аналогии с MVVM это, наверное действия [2] и [1]
        val loader = APODLoader(myLoadListener)
        loader.loadAPOD()
        // т.е. запускаем лоудер
    }

    private fun displayData (apodResponseDTO: APODResponseDTO) {
        requireActivity().runOnUiThread {
            tv_title_apod.text = apodResponseDTO.title
            tv_copyright_apod.text = "\u00A9 ${apodResponseDTO.copyright}"
            tv_date_apod.text = apodResponseDTO.date
            tv_explanation_apod.text = apodResponseDTO.explanation
        }
    }


}
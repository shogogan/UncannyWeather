package br.com.shogogan.uncannyweather.ui.features.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastIntent
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment: Fragment() {
    private val vm: WeatherForecastViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.dispatchIntent(WeatherForecastIntent.FetchIntent)
    }
}
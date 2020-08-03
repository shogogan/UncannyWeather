package br.com.shogogan.uncannyweather.ui.features.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.databinding.FragmentResultBinding
import br.com.shogogan.uncannyweather.ui.features.forecast.adapter.ForecastAdapter
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val vm: WeatherForecastViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private val adapter: ForecastAdapter by lazy {
        ForecastAdapter()
    }

    private lateinit var binding: FragmentResultBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_result,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        binding.vm = vm

        binding.rvForecast.adapter = adapter

        vm.state.observe(viewLifecycleOwner, Observer {
            if(it.locationModel == null){
                findNavController().navigateUp()
            }
        })
    }


}
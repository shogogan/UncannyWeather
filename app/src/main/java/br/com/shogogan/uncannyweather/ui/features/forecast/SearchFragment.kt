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
import br.com.shogogan.uncannyweather.databinding.FragmentSearchFiltersBinding
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastIntent
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val vm: WeatherForecastViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private lateinit var binding: FragmentSearchFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_filters,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm
        binding.btnSearch.setOnClickListener {
            vm.dispatchIntent(WeatherForecastIntent.FetchIntent)
        }

        vm.state.observe(viewLifecycleOwner, Observer {
            if (it.locationModel != null) {
                findNavController().navigate(R.id.action_searchFragment_to_resultFragment)
            }
        })
    }
}
package br.com.shogogan.uncannyweather.ui.features.forecast

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import br.com.shogogan.uncannyweather.R
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastIntent
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastState
import br.com.shogogan.uncannyweather.ui.features.forecast.viewmodel.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastActivity : AppCompatActivity() {
    private val vm: WeatherForecastViewModel by viewModels()
    private var canGoBack: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.state.observe(this, Observer {
            render(it)
        })
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    private fun render(it: WeatherForecastState) {
        canGoBack = it.locationModel == null
        if (it.error != null) {
            val message = if (it.error.messageId != null) {
                getString(it.error.messageId)
            } else {
                it.error.message ?: ""
            }
            AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    //ignore
                }
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (!canGoBack) {
            vm.dispatchIntent(WeatherForecastIntent.GoBackIntent)
            true
        } else {
            super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        if (!canGoBack) {
            vm.dispatchIntent(WeatherForecastIntent.GoBackIntent)
        } else {
            super.onBackPressed()
        }
    }
}
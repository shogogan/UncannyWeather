package br.com.shogogan.uncannyweather.ui.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("hideIfEmptyOrNull")
fun hideIfEmptyOrNull(view: View, value: String?) {
    view.visibility = if (value.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, value: Boolean) {
    view.visibility = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("adapterData")
fun <T> adapterData(rv: RecyclerView, value: List<T>){
    val adapter = rv.adapter
    if(adapter is UpdatableAdapter<*>){
        adapter as UpdatableAdapter<T>
        adapter.setData(value)
    }
}

package com.example.android.politicalpreparedness.ui.representative.adapter

import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.politicalpreparedness.R
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("android:text")
fun TextInputEditText.setInputText(text: String?) {
    setText(text)
}

@BindingAdapter("profileImage")
fun ImageView.fetchImage(src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        val requestOptions = RequestOptions()
            .circleCrop()
            .placeholder(R.drawable.ic_profile)
            .error(R.drawable.ic_profile)
        Glide.with(this.context)
            .load(uri)
            .apply(requestOptions)
            .into(this)
    }
}

fun Spinner.setNewValue(value: String?) {
    if (adapter != null) {
        val position = (adapter as ArrayAdapter<Any>).getPosition(value)
        setSelection(position, false)
        tag = position
    }
}

@BindingAdapter("entries", "stateValue")
fun Spinner.setEntries(entries: List<String>?, stateValue: String?) {
    entries?.let {
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, it)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
        setNewValue(stateValue)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T> {
    return adapter as ArrayAdapter<T>
}

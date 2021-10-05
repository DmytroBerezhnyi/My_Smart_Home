package com.example.mysmarthome.base.extension

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.Observable
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.util.concurrent.atomic.AtomicBoolean

fun View?.showKeyboard() {
    this?.let {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View?.hideKeyboard() {
    this?.let {
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun View?.gone() {
    this?.let { visibility = View.GONE }
}

fun View?.visible() {
    this?.let { visibility = View.VISIBLE }
}

fun View?.visibility(isVisible: Boolean) {
    this?.let { visibility = if (isVisible) View.VISIBLE else View.GONE }
}

fun View?.invisible() {
    this?.let { visibility = View.INVISIBLE }
}

fun View?.isVisible(): Boolean {
    if (this == null) return false
    return this.visibility == View.VISIBLE
}

fun ViewGroup.inflate(layoutRes: Int, attach: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attach)
        ?: throw IllegalArgumentException("ViewHolder not found, view = null")

fun View.setOnSingleClickListener(onClick: () -> Unit?) {
    this.setOnClickListener(
        object : View.OnClickListener {
            var canClick = AtomicBoolean(true)

            override fun onClick(v: View?) {
                if (canClick.getAndSet(false)) {
                    v?.run {
                        postDelayed({
                            canClick.set(true)
                        }, 1000L)
                        onClick.invoke()
                    }
                }
            }
        }
    )
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

inline fun <reified T : Observable> T.addOnPropertyChanged(crossinline callback: (T) -> Unit) =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also { addOnPropertyChangedCallback(it) }

fun MutableLiveData<Unit>.notifyLiveData() {
    this.value = null
}

fun FragmentManager.showDatePicker(
    onPositiveClickListener: MaterialPickerOnPositiveButtonClickListener<Long>,
    onCancelListener: DialogInterface.OnCancelListener
) {
    val picker = MaterialDatePicker.Builder
        .datePicker()
        .build()

    picker.addOnPositiveButtonClickListener(onPositiveClickListener)
    picker.addOnCancelListener(onCancelListener)
    picker.show(this, picker.toString())
}

fun <A, B> List<A>.toPairList(listTwo: List<B>): MutableList<Pair<A, B>> {
    if (size == listTwo.size) {
        return mutableListOf<Pair<A, B>>().also {
            forEachIndexed { index, a ->
                it.add(Pair(a, listTwo[index]))
            }
        }
    }
    return mutableListOf()
}
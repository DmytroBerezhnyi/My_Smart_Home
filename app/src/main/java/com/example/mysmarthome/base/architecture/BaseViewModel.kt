package com.example.mysmarthome.base.architecture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.extensions.fromJsonToMap
import com.example.mysmarthome.R
import com.example.mysmarthome.base.extension.notifyLiveData
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.base.model.SnackbarModel
import com.example.mysmarthome.base.model.ToastModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    val navEvent = MutableLiveData<NavigationModel>()
    val toastLiveEvent = MutableLiveData<ToastModel>()
    val snackLiveEvent = MutableLiveData<SnackbarModel>()
    val updateDataBinding = MutableLiveData<Unit>()
    val hideKeyboardEvent = MutableLiveData<Unit>()
    val showProgressLoadingEvent = MutableLiveData<Unit>()
    val hideProgressLoadingEvent = MutableLiveData<Unit>()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        when (throwable) {
            is HttpException -> {
                convertErrorBody(throwable)?.let {
                    snackLiveEvent.value = SnackbarModel(it)
                }
            }
            is UnknownHostException -> snackLiveEvent.value =
                SnackbarModel(idResMessage = R.string.error_internet)
            is SocketTimeoutException -> snackLiveEvent.value =
                SnackbarModel(idResMessage = R.string.error_connection_timeout)
        }
    }

    protected fun launchAsync(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((error: Exception?) -> Unit)? = null,
        onSuccess: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
    ) {
        val job = viewModelScope.launch(exceptionHandler) {
            block()
        }
        job.invokeOnCompletion {
            if (it != null) {
                if (it is java.lang.Exception) onError?.invoke(it)
                Timber.e(it)
            } else {
                onSuccess?.invoke()
            }
            onComplete?.invoke()
        }
    }

    protected fun launchAsyncWithLoadingDialog(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((error: Exception?) -> Unit)? = null,
        onSuccess: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
    ) {
        val job = viewModelScope.launch(exceptionHandler) {
            showProgressLoadingEvent.notifyLiveData()
            block()
        }
        job.invokeOnCompletion {
            hideProgressLoadingEvent.notifyLiveData()
            if (it != null) {
                if (it is java.lang.Exception) onError?.invoke(it)
                Timber.e(it)
            } else {
                onSuccess?.invoke()
            }
            onComplete?.invoke()
        }
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                val jObjError = JSONObject(it).getJSONObject("errorManager").getJSONObject("errors")
                val result = jObjError.toString().fromJsonToMap().values
                if (result.isNullOrEmpty()) {
                    null
                } else {
                    result.toTypedArray()[0].toString()
                }
            }
        } catch (exception: Exception) {
            null
        }
    }
}
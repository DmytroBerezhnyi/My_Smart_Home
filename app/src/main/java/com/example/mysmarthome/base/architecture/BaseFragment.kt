package com.example.mysmarthome.base.architecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mysmarthome.base.model.SnackbarModel
import com.google.android.material.snackbar.Snackbar
import com.example.mysmarthome.base.model.NavigationModel
import com.example.mysmarthome.base.model.ToastModel

abstract class BaseFragment<DataBinding : ViewDataBinding, VM : BaseViewModel>() : Fragment() {

    protected abstract val vm: VM

    @get:LayoutRes
    protected abstract val layoutId: Int

    protected lateinit var viewDataBinding: DataBinding

    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewDataBinding.initView()
        return viewDataBinding.root
    }

    protected open fun DataBinding.initView() {}

    protected open fun VM.setupViewObservers() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.setupViewObservers()
        navController = NavHostFragment.findNavController(this)

        vm.navEvent.observe(viewLifecycleOwner, { this.onNavigateTo(it) })
        vm.toastLiveEvent.observe(viewLifecycleOwner, { this.showToast(it) })
        vm.snackLiveEvent.observe(viewLifecycleOwner, { this.showSnackBar(it) })
        vm.updateDataBinding.observe(
            viewLifecycleOwner,
            { viewDataBinding.executePendingBindings() })
        vm.hideKeyboardEvent.observe(viewLifecycleOwner, { hideKeyboard() })
        vm.showProgressLoadingEvent.observe(viewLifecycleOwner, { showProgressLoading() })
        vm.hideProgressLoadingEvent.observe(viewLifecycleOwner, { hideProgressLoading() })
    }

    fun hideKeyboard() {
        (activity as? BaseActivity<*, *>)?.hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    protected fun onNavigateTo(navigationModel: NavigationModel?) {
        navigationModel?.let {
            when {
                it.popBack ->
                    navController.navigateUp()
                it.direction != null && it.extras != null ->
                    navController.navigate(
                        it.direction!!,
                        it.extras!!
                    )
                it.direction != null -> {
                    navController.navigate(it.direction!!)
                }
            }
            vm.navEvent.value = null
        }
    }

    protected fun showToast(toastModel: ToastModel) {
        val message = toastModel.idResMessage?.let { getString(it) } ?: toastModel.message
        if (!message.isNullOrEmpty()) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }
    }

    protected fun showSnackBar(snackbarModel: SnackbarModel) {
        val message =
            snackbarModel.idResMessage?.let { getString(it) } ?: snackbarModel.message ?: ""
        view?.let {
            val snackbar = Snackbar.make(it, message, snackbarModel.duration)
            snackbarModel.actionTitle?.let { res ->
                snackbar.setAction(getString(res)) {
                    snackbarModel.action?.invoke()
                }
            }
            snackbar.show()
        }
    }

    protected fun showProgressLoading() {
        (activity as? BaseActivity<*, *>)?.showProgressLoading()
    }

    protected fun hideProgressLoading() {
        (activity as? BaseActivity<*, *>)?.hideProgressLoading()
    }
}
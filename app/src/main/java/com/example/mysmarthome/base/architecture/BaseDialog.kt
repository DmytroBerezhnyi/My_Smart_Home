package com.example.mysmarthome.base.architecture

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<D : ViewDataBinding> :
    DialogFragment() {

    private var _viewDataBinding: D? = null

    protected val viewDataBinding get() = _viewDataBinding!!

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun bindDialogData()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _viewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, null, false)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(viewDataBinding.root)
        bindDialogData()
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewDataBinding = null
    }
}
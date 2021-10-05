package com.example.mysmarthome.base.architecture

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.mysmarthome.R

abstract class BaseActivity<DataBinding : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity() {

    protected lateinit var viewDataBinding: DataBinding

    protected abstract val vm: VM

    @get:LayoutRes
    abstract val layoutId: Int

    private var mProgressDialog: AlertDialog? = null

    fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showProgressLoading() {
        mProgressDialog = buildProgressDialog()
        mProgressDialog?.window?.let { window ->
            window.setGravity(Gravity.CENTER)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        mProgressDialog?.show()
    }

    fun hideProgressLoading() {
        mProgressDialog?.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
    }

    private fun buildProgressDialog(): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_progress, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        return dialogBuilder.create()
    }
}
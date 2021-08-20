package com.mobile.submissionperintis.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.mobile.submissionperintis.extension.instanceOf
import com.mobile.submissionperintis.widget.ProgressDialogFragment
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun contentView(): View
    abstract fun setupViews()
    abstract fun setupViewEvents()
    abstract fun bindViewModel()

    private var progressbarDialog: ProgressDialogFragment? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        setContentView(contentView())
        setupViews()
        setupViewEvents()
        bindViewModel()
    }

    @Synchronized
    fun showLoading(isLoading: Boolean) {
        if (!isLoading) {
            hideProgress()
            return
        }
        showDialogProgress()
    }

    @Synchronized
    private fun showDialogProgress() {
        if (progressbarDialog != null) return
        progressbarDialog = instanceOf(ProgressDialogFragment.withData(false))

        progressbarDialog?.show(supportFragmentManager, progressbarDialog?.tag)
    }

    @Synchronized
    private fun hideProgress() {
        progressbarDialog?.dismiss()
        progressbarDialog?.apply {
            supportFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
        progressbarDialog = null

    }

    protected fun showNetworkError(view: View?, exception: Exception?) {
        view?.let {
            var message = ""
            when (exception) {
                is SocketTimeoutException -> {
                    message = "Mohon cek sinyal internet atau koneksi wifi Anda"
                }
                is UnknownHostException -> {
                    message = "Sepertinya perangkat Anda tidak terhubung ke koneksi internet"
                }
            }
            val snackbar = Snackbar
                .make(it, message, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    protected fun showError(view: View?, message: String) {
        view?.let {
            val snackbar = Snackbar
                .make(it, message, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}
package com.mobile.submissionperintis.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseFragment : Fragment() {

    protected abstract fun contentView(inflater: LayoutInflater, container: ViewGroup?): View
    abstract fun setupViews()
    abstract fun setupViewEvents()
    abstract fun bindViewModel()
    abstract fun destroyView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return contentView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        setupViews()
        setupViewEvents()
        bindViewModel()
    }

    override fun onDestroy() {
        destroyView()
        super.onDestroy()
    }

    internal fun showLoading(isLoading: Boolean){
        (activity as? BaseActivity)?.showLoading(isLoading)
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
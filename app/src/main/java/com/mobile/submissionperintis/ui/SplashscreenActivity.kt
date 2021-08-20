package com.mobile.submissionperintis.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import com.mobile.submissionperintis.base.BaseActivity
import com.mobile.submissionperintis.databinding.ActivitySplashscreenBinding
import com.mobile.submissionperintis.ui.login.LoginActivity

class SplashscreenActivity : BaseActivity() {

    override fun contentView(): View {
        return ActivitySplashscreenBinding.inflate(layoutInflater).root
    }

    override fun setupViews() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this@SplashscreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }

    override fun setupViewEvents() {
    }

    override fun bindViewModel() {
    }
}
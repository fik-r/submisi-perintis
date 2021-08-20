package com.mobile.submissionperintis.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.mobile.submissionperintis.R
import com.mobile.submissionperintis.base.BaseActivity
import com.mobile.submissionperintis.databinding.ActivityMainBinding
import com.mobile.submissionperintis.ui.artisan.ListFragment

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    override fun contentView(): View {
        return ActivityMainBinding.inflate(layoutInflater).also { _binding = it }.root
    }

    override fun setupViews() {
        setCurrentFragment(ListFragment())
        _binding?.bottomNavigation?.selectedItemId = R.id.menu_home
    }

    override fun setupViewEvents() {
        _binding?.apply {
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> setCurrentFragment(ListFragment())
                    R.id.menu_profile -> setCurrentFragment(ProfileFragment())
                }
                true
            }
        }
    }

    override fun bindViewModel() {
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }

}
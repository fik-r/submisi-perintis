package com.mobile.submissionperintis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.submissionperintis.base.BaseFragment
import com.mobile.submissionperintis.databinding.FragmentProfileBinding
import com.mobile.submissionperintis.helper.LocalCache
import org.koin.android.ext.android.inject

class ProfileFragment : BaseFragment() {
    private var _binding: FragmentProfileBinding? = null
    private val localCache: LocalCache by inject()
    override fun contentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return FragmentProfileBinding.inflate(layoutInflater).also { _binding = it }.root
    }

    override fun setupViews() {
        _binding?.apply {
            textName.text = localCache.fullName
            textEmail.text = localCache.email
        }
    }

    override fun setupViewEvents() {
    }

    override fun bindViewModel() {
    }

    override fun destroyView() {
        _binding = null
    }
}
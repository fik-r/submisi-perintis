package com.mobile.submissionperintis.ui.artisan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.submissionperintis.base.BaseFragment
import com.mobile.submissionperintis.databinding.FragmentListBinding
import com.mobile.submissionperintis.extension.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment() {
    private var _job: Job? = null
    private var _binding: FragmentListBinding? = null
    private val _adapter: ListAdapter by lazy {
        ListAdapter {
            requireActivity().startActivity<DetailArtisanActivity>(
                *DetailArtisanActivity.withData(
                    it
                )
            )
        }
    }
    private val _viewModel: ListViewModel by viewModel()

    override fun contentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return FragmentListBinding.inflate(layoutInflater).also { _binding = it }.root
    }

    override fun setupViews() {
        _binding?.apply {
            listArtisan.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            listArtisan.setHasFixedSize(true)
            listArtisan.adapter = _adapter
        }
    }

    override fun setupViewEvents() {
        _binding?.apply {

        }
    }

    override fun bindViewModel() {
        _job = _viewModel.eventsFlow
            .onEach {
                when (it) {
                    is ListViewModel.Event.ShowData -> _adapter.artisanList = it.list
                    is ListViewModel.Event.ShowLoading -> showLoading(it.isLoading)
                    is ListViewModel.Event.ShowNetworkError -> showNetworkError(
                        _binding?.root,
                        it.exception
                    )
                    is ListViewModel.Event.ShowError -> showError(
                        _binding?.root,
                        it.message
                    )
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun destroyView() {
        _binding = null
        _job?.cancel()
    }
}
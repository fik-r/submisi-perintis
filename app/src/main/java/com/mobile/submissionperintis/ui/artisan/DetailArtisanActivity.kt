package com.mobile.submissionperintis.ui.artisan

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.submissionperintis.base.BaseActivity
import com.mobile.submissionperintis.databinding.ActivityDetailArtisanBinding
import com.mobile.submissionperintis.extension.loadImageFromUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailArtisanActivity : BaseActivity() {

    private val _id by lazy {
        intent.getStringExtra(
            ID,
        )
    }
    private var _job: Job? = null
    private var _binding: ActivityDetailArtisanBinding? = null
    private val _adapter: ServiceAdapter by lazy {
        ServiceAdapter()
    }
    private val _viewModel: DetailArtisanViewModel by viewModel()


    override fun contentView(): View {
        return ActivityDetailArtisanBinding.inflate(layoutInflater).also { _binding = it }.root
    }

    override fun setupViews() {
        _viewModel.loadDetail(_id ?: "")
        _binding?.apply {
            listService.layoutManager =
                LinearLayoutManager(this@DetailArtisanActivity, RecyclerView.HORIZONTAL, false)
            listService.setHasFixedSize(true)
            listService.adapter = _adapter
        }
    }

    override fun setupViewEvents() {
    }

    override fun bindViewModel() {
        _job = _viewModel.eventsFlow
            .onEach {
                when (it) {
                    is DetailArtisanViewModel.Event.ShowData -> {
                        _binding?.apply {
                            rating.rating = it.artisan.rating.toFloat()
                            textUserName.text = it.artisan.name
                            textDescription.text = it.artisan.description
                            img.loadImageFromUrl(it.artisan.image)
                        }
                        _adapter.serviceList = it.artisan.services
                    }
                    is DetailArtisanViewModel.Event.ShowLoading -> showLoading(it.isLoading)
                    is DetailArtisanViewModel.Event.ShowNetworkError -> showNetworkError(
                        _binding?.root,
                        it.exception
                    )
                    is DetailArtisanViewModel.Event.ShowError -> showError(
                        _binding?.root,
                        it.message
                    )
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Main))
    }

    companion object {

        private const val ID = ".id"

        fun withData(
            id: String,
        ): Array<Pair<String, *>> = arrayOf(
            ID to id,
        )
    }

}
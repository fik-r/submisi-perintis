package com.mobile.submissionperintis.ui.artisan

import androidx.lifecycle.viewModelScope
import com.mobile.submissionperintis.base.BaseViewModel
import com.mobile.submissionperintis.base.ResponseError
import com.mobile.submissionperintis.data.model.Artisan
import com.mobile.submissionperintis.domain.GetListArtisanUseCase
import com.mobile.submissionperintis.network.NetworkResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getListArtisanUseCase: GetListArtisanUseCase
) : BaseViewModel() {

    sealed class Event {
        data class ShowLoading(val isLoading: Boolean) : Event()
        data class ShowData(val list: List<Artisan>) : Event()
        data class ShowError(val code: Int, val message: String) : Event()
        data class ShowNetworkError(val exception: Exception) : Event()
    }

    private var _query: String? = null

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun setQuery(query: String) {
        _query = query
    }

    init {
        viewModelScope.launch {
            eventChannel.send(Event.ShowLoading(true))
            when (val response = getListArtisanUseCase.execute()) {
                is NetworkResponse.Success -> {
                    eventChannel.send(Event.ShowData(response.body))
                }
                else -> {
                    setError(response)
                }
            }
            eventChannel.send(Event.ShowLoading(false))
        }
    }

    private fun setError(networkResponse: NetworkResponse<Any, ResponseError>) =
        networkResponse.setErrorResponse(
            onServerError = { code, message ->
                viewModelScope.launch {
                    eventChannel.send(
                        Event.ShowError(
                            code,
                            message
                        )
                    )
                }
            },
            onNetworkError = { exception ->
                viewModelScope.launch {
                    eventChannel.send(
                        Event.ShowNetworkError(
                            exception
                        )
                    )
                }
            }
        )

}
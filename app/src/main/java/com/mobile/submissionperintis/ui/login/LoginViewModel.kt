package com.mobile.submissionperintis.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.submissionperintis.helper.LocalCache
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val localCache: LocalCache) : ViewModel() {

    sealed class Event {
        object NavigateToDashboard : Event()
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun loginButtonClicked(name: String?, email: String?, image: String?) = viewModelScope.launch {
        localCache.fullName = name
        localCache.email = email
        localCache.image = image
        eventChannel.send(Event.NavigateToDashboard)
    }
}
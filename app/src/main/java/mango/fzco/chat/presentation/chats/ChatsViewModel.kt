package mango.fzco.chat.presentation.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mango.fzco.chat.domain.model.ChatsModel
import mango.fzco.chat.domain.use_case.GetChatsUseCase
import javax.inject.Inject


@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatsUseCase: GetChatsUseCase
) : ViewModel() {

    private val _chats = MutableLiveData<List<ChatsModel>>()
    val chats: LiveData<List<ChatsModel>> get() = _chats

    fun getChats() {
        _chats.value = chatsUseCase.invoke()
    }
}
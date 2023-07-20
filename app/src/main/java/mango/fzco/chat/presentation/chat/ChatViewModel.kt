package mango.fzco.chat.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mango.fzco.chat.domain.model.ChatModel
import mango.fzco.chat.domain.use_case.GetChatUseCase
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    private val _chatData = MutableLiveData<List<ChatModel>>()
    val chatData: LiveData<List<ChatModel>> get() = _chatData

    init {
        _chatData.value = getChatUseCase.invoke()
    }

}
package mango.fzco.chat.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.databinding.FragmentChatBinding

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(layoutInflater, container, false)


        chatViewModel.chatData.observe(viewLifecycleOwner) {
            binding.rvChat.adapter = ChatAdapter(it)
        }

        return binding.root
    }
}
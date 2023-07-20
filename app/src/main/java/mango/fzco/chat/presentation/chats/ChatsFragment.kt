package mango.fzco.chat.presentation.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentChatsBinding

@AndroidEntryPoint
class ChatsFragment : Fragment() {

    private var _binding: FragmentChatsBinding? = null
    private val binding: FragmentChatsBinding get() = _binding!!
    private lateinit var menuHost: MenuHost


    private val chatsViewModel: ChatsViewModel by viewModels()
    private val chatListAdapter: ChatListAdapter = ChatListAdapter { chatId ->
        Toast.makeText(requireContext(), chatId.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatsBinding.inflate(layoutInflater, container, false)
        menuHost = requireActivity()
        binding.fabAddNewChat.setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.add_new_chat),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvChats.adapter = chatListAdapter
        chatsViewModel.getChats()

        setObserver()
        setMenuInToolbar()

        return binding.root
    }

    private fun setMenuInToolbar() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.chats_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_camera -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.camera),
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    R.id.menu_profile -> {
                        findNavController().navigate(R.id.action_chatsFragment_to_profileFragment)
                        true
                    }

                    R.id.menu_settings -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.settings),
                            Toast.LENGTH_SHORT
                        ).show()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setObserver() = with(chatsViewModel) {
        chats.observe(viewLifecycleOwner) {
            chatListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
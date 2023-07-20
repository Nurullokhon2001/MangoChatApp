package mango.fzco.chat.presentation.profile

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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentProfileBinding
import mango.fzco.chat.utils.ResultWrapper

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private val profileViwModel: ProfileViewModel by viewModels()
    private lateinit var menuHost: MenuHost
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        menuHost = requireActivity()
        setObserves()
        setMenuInToolbar()
        return binding.root
    }

    private fun setObserves() = with(binding) {
        profileViwModel.profileData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.NetworkError -> {
                    binding.pbProfile.isVisible = false
                    binding.llMain.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.GenericError -> {
                    if (result.code != null && result.code == 401) {
                        profileViwModel.getProfileData()
                    } else {
                        binding.pbProfile.isVisible = false
                        binding.llMain.isVisible = true
                        result.error?.let {
                            it.errorMessage?.let { errorMessage ->
                                Toast.makeText(
                                    requireContext(),
                                    errorMessage,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                is ResultWrapper.Success -> {
                    binding.pbProfile.isVisible = false
                    binding.llMain.isVisible = true
                    tiPhoneNumber.setText(result.value.phone)
                    tiUserName.setText(result.value.username)
                    tiDateOfBirthDay.setText(result.value.birthday)
                    tiName.setText(result.value.name)
                    tiCity.setText(result.value.city)
                    Glide.with(requireContext()).load("https://plannerok.ru/" + result.value.avatar)
                        .placeholder(R.drawable.ic_user)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(binding.ivChat)
                }

                is ResultWrapper.Loading -> {
                    binding.pbProfile.isVisible = true
                    binding.llMain.isVisible = false
                }
            }
        }
    }

    private fun setMenuInToolbar() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.profile_update_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_edit -> {
                        findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
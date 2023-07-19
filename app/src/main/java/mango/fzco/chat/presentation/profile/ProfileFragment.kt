package mango.fzco.chat.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentProfileBinding
import mango.fzco.chat.utils.ResultWrapper

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private val profileViwModel: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        setObserves()

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
                        Toast.makeText(
                            requireContext(),
                            result.error?.errorMessage.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is ResultWrapper.Success -> {
                    binding.pbProfile.isVisible = false
                    binding.llMain.isVisible = true
                    tiPhoneNumber.setText(result.value.phone)
                    tiUserName.setText(result.value.username)
                    tiDateOfBirthDay.setText(result.value.birthday)
                    tiName.setText(result.value.name)
                }

                is ResultWrapper.Loading -> {
                    binding.pbProfile.isVisible = true
                    binding.llMain.isVisible = false
                }
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
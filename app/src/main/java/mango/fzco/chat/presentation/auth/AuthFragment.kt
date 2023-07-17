package mango.fzco.chat.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.MainActivity
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentAuthBinding
import mango.fzco.chat.utils.ResultWrapper
import me.ibrahimsn.lib.PhoneNumberKit

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater, container, false)

        initViews()
        setObserver()

        return binding.root
    }

    private fun initViews() = with(binding) {

        val phoneNumberKit = PhoneNumberKit.Builder(requireContext()).setIconEnabled(true).build()
        phoneNumberKit.attachToInput(tiPhoneNumber, COUNTRY_ISO2_RU)
        phoneNumberKit.setupCountryPicker(
            activity = requireActivity() as MainActivity, searchEnabled = true
        )

        btnSend.setOnClickListener {
            authViewModel.sendAuthCode(etTiPhoneNumber.text.toString())
        }
    }

    private fun setObserver() = with(binding) {
        authViewModel.sendAuthCodeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.NetworkError -> {
                    pbAuth.isVisible = false
                    changeButtonText(pbAuth.isVisible)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.GenericError -> {
                    pbAuth.isVisible = false
                    changeButtonText(pbAuth.isVisible)
                    Toast.makeText(
                        requireContext(),
                        result.error?.errorMessage.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.Success -> {
                    pbAuth.isVisible = false
                    changeButtonText(pbAuth.isVisible)
                    if (result.value.isSuccess) {
                        val action = AuthFragmentDirections.actionAuthFragmentToOtpFragment(
                            binding.etTiPhoneNumber.text.toString()
                        )
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.wrong_try_again),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is ResultWrapper.Loading -> {
                    pbAuth.isVisible = true
                    changeButtonText(pbAuth.isVisible)
                }
            }
        }
    }

    private fun changeButtonText(isProgress: Boolean) {
        if (isProgress) {
            binding.btnSend.text = getString(R.string.empty_string)
        } else {
            binding.btnSend.text = getString(R.string.get_otp)
        }
        changedEnabledViews(isProgress)
    }

    private fun changedEnabledViews(isProgress: Boolean) = with(binding) {
        if (isProgress) {
            btnSend.isClickable = false
            etTiPhoneNumber.isFocusable = false
        } else {
            btnSend.isClickable = true
            etTiPhoneNumber.isFocusable = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val COUNTRY_ISO2_RU = "ru"
    }
}
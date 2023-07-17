package mango.fzco.chat.presentation.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentOtpBinding
import mango.fzco.chat.utils.ResultWrapper


@AndroidEntryPoint
class OtpFragment : Fragment() {

    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!

    private val otpViewModel: OtpViewModel by viewModels()

    private val args: OtpFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(layoutInflater, container, false)

        binding.btnVerify.setOnClickListener {
            if (REQUIRED_OTP_LENGTH == binding.otpView.otp?.length) {
                otpViewModel.checkAuthCode(args.phoneNumber, binding.otpView.otp.toString())
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.consists_otp_code), Toast.LENGTH_SHORT
                ).show()
            }
        }

        setObservers()

        return binding.root
    }

    private fun setObservers() {
        otpViewModel.checkAuthCodeResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    binding.pbOtp.isVisible = false
                    changeButtonText(binding.pbOtp.isVisible)
                    Toast.makeText(
                        requireContext(), result.value.userId.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.GenericError -> {
                    binding.pbOtp.isVisible = false
                    changeButtonText(binding.pbOtp.isVisible)
                    shaleOtpView()
                    Toast.makeText(
                        requireContext(), result.error?.errorMessage.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.NetworkError -> {
                    binding.pbOtp.isVisible = false
                    changeButtonText(binding.pbOtp.isVisible)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.Loading -> {
                    binding.pbOtp.isVisible = true
                    changeButtonText(binding.pbOtp.isVisible)
                }
            }
        }
    }

    private fun changeButtonText(isProgress: Boolean) {
        if (isProgress) {
            binding.btnVerify.text = getString(R.string.empty_string)
        } else {
            binding.btnVerify.text = getString(R.string.verify)
        }
        changedEnabledViews(isProgress)
    }

    private fun changedEnabledViews(isProgress: Boolean) = with(binding) {
        if (isProgress) {
            btnVerify.isClickable = false
            otpView.isFocusable = false
        } else {
            btnVerify.isClickable = true
            otpView.isFocusable = true
        }
    }

    private fun shaleOtpView() {
        binding.otpView.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(), R.anim.shake_animation
            )
        )
        binding.otpView.showError()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_OTP_LENGTH = 6
    }
}
package mango.fzco.chat.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentRegisterBinding
import mango.fzco.chat.utils.ResultWrapper


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!

    private val args: RegisterFragmentArgs by navArgs()

    private val registerViewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        binding.tiPhoneNumber.setText(args.phoneNumber)
        setObservers()

        binding.btnRegister.setOnClickListener {
            registerViewModel.registerUser(
                args.phoneNumber, binding.tiName.text.toString(), binding.tiUserName.text.toString()
            )
        }
        return binding.root
    }

    private fun setObservers() {
        registerViewModel.registerUserResult.observe(viewLifecycleOwner) { result ->
            binding.pbRegister.isVisible = result is ResultWrapper.Loading
            changeButtonTextAndEnabled(binding.pbRegister.isVisible)

            when (result) {
                is ResultWrapper.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_chatsFragment)
                }

                is ResultWrapper.GenericError -> {
                    Toast.makeText(
                        requireContext(),
                        result.error?.errorMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is ResultWrapper.NetworkError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_connection),
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> {}
            }
        }
    }

    private fun changeButtonTextAndEnabled(isProgress: Boolean) = with(binding) {
        if (isProgress) {
            btnRegister.text = getString(R.string.empty_string)
        } else {
            btnRegister.text = getString(R.string.register)
        }
        btnRegister.isClickable = !isProgress
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
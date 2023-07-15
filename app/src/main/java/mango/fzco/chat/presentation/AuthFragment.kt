package mango.fzco.chat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mango.fzco.chat.MainActivity
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentAuthBinding
import me.ibrahimsn.lib.PhoneNumberKit

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater, container, false)

        val phoneNumberKit = PhoneNumberKit.Builder(requireContext())
            .setIconEnabled(true)
            .build()

        phoneNumberKit.attachToInput(binding.tiPhoneNumber, COUNTRY_ISO2_RU)

        phoneNumberKit.setupCountryPicker(
            activity = requireActivity() as MainActivity,
            searchEnabled = true
        )

        binding.btnSend.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_otpFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val COUNTRY_ISO2_RU = "ru"
    }
}
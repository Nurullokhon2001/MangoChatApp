package mango.fzco.chat.presentation.update_profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import dagger.hilt.android.AndroidEntryPoint
import mango.fzco.chat.R
import mango.fzco.chat.databinding.FragmentUpdateProfileBinding
import mango.fzco.chat.utils.ResultWrapper
import java.io.ByteArrayOutputStream
import java.io.IOException


@AndroidEntryPoint
class UpdateProfileFragment : Fragment() {

    private var _binding: FragmentUpdateProfileBinding? = null
    private val binding: FragmentUpdateProfileBinding get() = _binding!!

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    data.data?.let {
                        binding.ivChat.setImageBitmap(getBitmapFromUri(it))
                    }
                }
            }
        }


    private val updateProfileViewModel: UpdateProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateProfileBinding.inflate(layoutInflater, container, false)
        setObserves()

        binding.btnSave.setOnClickListener {
            updateProfileViewModel.updateProfile(
                binding.tiUserName.text.toString(),
                binding.tiName.text.toString(),
                binding.tiCity.text.toString(),
                binding.tiDateOfBirthDay.text.toString(),
                encodeImage(getBitmapFromView(binding.ivChat))
            )
        }

        binding.ivChat.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            resultLauncher.launch(
                Intent.createChooser(
                    intent,
                    "Select Picture"
                )
            )
        }

        return binding.root
    }

    private fun setObserves() = with(binding) {
        updateProfileViewModel.profileData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.NetworkError -> {
                    pbBtnProfile.isVisible = false
                    changeButtonText(pbBtnProfile.isVisible)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ResultWrapper.GenericError -> {
                    if (result.code != null && result.code == 401) {
                        updateProfileViewModel.getProfileData()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            result.error?.errorMessage.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        pbBtnProfile.isVisible = false
                        changeButtonText(pbBtnProfile.isVisible)
                    }
                }

                is ResultWrapper.Success -> {
                    tiPhoneNumber.setText(result.value.phone)
                    tiUserName.setText(result.value.username)
                    tiDateOfBirthDay.setText(result.value.birthday)
                    tiCity.setText(result.value.city)
                    tiName.setText(result.value.name)
                    pbBtnProfile.isVisible = false
                    changeButtonText(pbBtnProfile.isVisible)
                    Glide.with(requireContext()).load("https://plannerok.ru/" + result.value.avatar)
                        .placeholder(R.drawable.ic_user)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(binding.ivChat)
                }

                is ResultWrapper.Loading -> {
                    pbBtnProfile.isVisible = true
                    changeButtonText(pbBtnProfile.isVisible)
                }
            }
        }
    }

    private fun changeButtonText(isProgress: Boolean) {
        if (isProgress) {
            binding.btnSave.text = getString(R.string.empty_string)
        } else {
            binding.btnSave.text = getString(R.string.save)
        }
        binding.btnSave.isClickable = !isProgress
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT) ?: ""
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            requireContext().contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()
        return image
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
package ru.myrosmol.conductor.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentProfileViewerBinding
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.ProfileResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ProfileViewerFragment : Fragment() {

    lateinit var binding: FragmentProfileViewerBinding
    val args: ProfileViewerFragmentArgs by navArgs()
    private val profileResponse = MutableLiveData<ProfileResponse>()

    @Inject
    lateinit var retrofitService: RetrofitService

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.startButton.setOnClickListener {
            findNavController().popBackStack()
        }

        profileResponse.observe(viewLifecycleOwner) {
            binding.fullname.text = it.fullname
            binding.position.text = it.position
            binding.division.text = it.divisionTitle

            val f = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            binding.birthDate.text = DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru"))
                .format(f.parse("${it.birthDate}")!!)

            binding.vk.text = it.vk
            binding.whatsapp.text = it.whatsapp
            binding.telegram.text = it.telegram
            binding.email.text = it.email

            hideLoading()
        }

        binding.vkCard.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(binding.vk.text.toString())
            startActivity(i)
        }

        binding.whatsapp.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(binding.whatsapp.text.toString())
            startActivity(i)
        }

        binding.telegram.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(binding.telegram.text.toString())
            startActivity(i)
        }

        binding.email.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:${binding.email.text}")
            startActivity(emailIntent)
        }

        loadInfoAboutUser()
    }

    fun loadInfoAboutUser() {
        showLoading()
        retrofitService.getUser(preferenceRepository.token, args.userId) { response, code ->
            if (code == 200)
                profileResponse.postValue(response!!)
            else {
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

}
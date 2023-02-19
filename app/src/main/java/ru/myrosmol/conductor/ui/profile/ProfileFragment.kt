package ru.myrosmol.conductor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentProfileBinding
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.ProfileResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    @Inject
    lateinit var retrofitService: RetrofitService

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    private val profileResponse = MutableLiveData<ProfileResponse>()
    var currentDivisionId = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileResponse.observe(viewLifecycleOwner) {
            binding.fullname.text = it.fullname
            binding.position.text = it.position
            binding.coins.text = "${it.coins}"
            binding.division.text = it.divisionTitle

            val f = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            binding.birthDate.text = DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru"))
                .format(f.parse("${it.birthDate}")!!)

            binding.vk.editText?.setText(it.vk)
            binding.whatsapp.editText?.setText(it.whatsapp)
            binding.telegram.editText?.setText(it.telegram)

            hideLoading()
        }

        binding.confirmChanges.setOnClickListener {
            updateProfile(
                "${binding.telegram.editText?.text}",
                "${binding.whatsapp.editText?.text}",
                "${binding.vk.editText?.text}"
            )
        }

        loadProfile()
    }

    fun loadProfile() {
        showLoading()
        retrofitService.myProfile(preferenceRepository.token) { response, code ->
            if (code == 200)
                profileResponse.postValue(response!!)
            else
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    fun updateProfile(telegram: String, whatsApp: String, vk: String) {
        showLoading()
        retrofitService.changeUser(
            preferenceRepository.token,
            telegram,
            whatsApp,
            vk
        ) { response, code ->
            if (code == 200) {
                if (response!!.isDone == true) {
                    loadProfile()
                    Toast.makeText(
                        context,
                        "Данные успешно изменены.",
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    hideLoading()
                    Toast.makeText(
                        context,
                        "Введённые данные некорректны.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                hideLoading()
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
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
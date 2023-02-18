package ru.myrosmol.conductor.ui.network

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentNetworkBinding
import ru.myrosmol.conductor.epoxy.person
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.ProfileResponse
import ru.myrosmol.conductor.network.response.RoadmapResponse
import javax.inject.Inject

@AndroidEntryPoint
class NetworkFragment : Fragment() {

    private lateinit var binding: FragmentNetworkBinding
    private val profilesList = MutableLiveData<List<ProfileResponse>>()

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    private var onPersonClickedListener = object : OnPersonClickedListener {
        override fun onPersonClicked(id: Int) {
            findNavController().navigate(
                NetworkFragmentDirections.actionNetworkFragmentToProfileViewerFragment(
                    id
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNetworkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.usersRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        profilesList.observe(viewLifecycleOwner) {
            retrofitService.getDivisions(preferenceRepository.token) { response, code ->
                binding.usersRecycler.withModels {
                    it.forEach {
                        person {
                            id(it.id)
                            userId(it.id!!)
                            fullname(it.fullname)
                            division(response!!.find { i -> i.id!! == it.divisionId }!!.title)
                            position(it.position)
                            onPersonClickedListener(onPersonClickedListener)
                        }
                    }
                }
            }
            hideLoading()
        }

        loadProfiles()
    }

    fun loadProfiles() {
        showLoading()
        retrofitService.myProfile(preferenceRepository.token) { divisionResponse, divisionCode ->
            if (divisionCode == 200)
                retrofitService.getUsers(
                    preferenceRepository.token,
                    divisionResponse?.divisionId!!
                ) { response, code ->
                    if (code == 200)
                        profilesList.postValue(response!!)
                    else
                        Toast.makeText(context, "Ошибка при загрузке профилей.", Toast.LENGTH_SHORT)
                            .show()
                }
            else
                Toast.makeText(
                    context,
                    "Ошибка при загрузке основного профиля.",
                    Toast.LENGTH_SHORT
                )
                    .show()
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }
}
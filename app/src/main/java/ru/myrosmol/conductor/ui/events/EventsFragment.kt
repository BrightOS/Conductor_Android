package ru.myrosmol.conductor.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentEventsBinding
import ru.myrosmol.conductor.epoxy.event
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.EventResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    val eventResponse = MutableLiveData<List<EventResponse>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.eventsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        eventResponse.observe(viewLifecycleOwner) {
            binding.eventsRecycler.withModels {
                it.forEach {
                    event {
                        id(it.id)
                        title(it.title)
                        description(it.desc)
                        val f = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
                        dateTime(DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("ru"))
                            .format(f.parse("${it.dateTime}")!!))
                    }
                }
            }

            hideLoading()
        }

        loadMyEvents()
    }

    fun loadMyEvents() {
        showLoading()
        retrofitService.myEvents(preferenceRepository.token) { response, code ->
            if (code == 200)
                eventResponse.postValue(response)
            else
                Toast.makeText(
                    context,
                    "$code Произошла непредвиденная ошибка.",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

}
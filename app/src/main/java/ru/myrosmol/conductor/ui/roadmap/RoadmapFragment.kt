package ru.myrosmol.conductor.ui.roadmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentRoadmapBinding
import ru.myrosmol.conductor.epoxy.day
import ru.myrosmol.conductor.epoxy.task
import ru.myrosmol.conductor.epoxy.week
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.DivisionResponse
import ru.myrosmol.conductor.network.response.RoadmapResponse
import ru.myrosmol.conductor.utils.TaskType
import javax.inject.Inject

@AndroidEntryPoint
class RoadmapFragment : Fragment() {

    private lateinit var binding: FragmentRoadmapBinding
    private val roadmapResponse = MutableLiveData<RoadmapResponse>()

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    private val taskClickedListener = object : OnTaskClickedListener {
        override fun onTaskClicked(taskId: Int) {
//            roadmapResponse.value!!.weeksRoadmap.weeks.forEach { it.days. }
            findNavController().navigate(RoadmapFragmentDirections.actionRoadmapFragmentToTaskFragment(taskId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoadmapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.roadmapRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        roadmapResponse.observe(viewLifecycleOwner) {
            var i = 0
            binding.roadmapRecycler.withModels {
                if (it != null)
                    it.weeksRoadmap?.weeks?.forEach {
                        week {
                            id(i++)
                            week(it.week.toString())
                        }
                        it.days?.forEach {
                            it.tasks!!
                            day {
                                id(i++)
                                day(it.day.toString())
                            }
                            it.tasks.forEachIndexed { index, taskResponse ->
                                task {
                                    id(i++)
                                    passed(taskResponse.completed!!)
                                    coins(taskResponse.coins!!)
                                    title(taskResponse.title)
                                    type(
                                        when (taskResponse.type) {
                                            "auto_test" -> TaskType.AUTO
                                            "hr_confirmation" -> TaskType.HR
                                            else -> TaskType.POLL
                                        }
                                    )

                                    taskId(taskResponse.id!!)
                                    onTaskClickedListener(taskClickedListener)
                                    if (index == it.tasks.size - 1)
                                        last(true)
                                }
                            }
                        }
                    }
                hideLoading()
            }
        }

        updateRoadmap()
    }

    fun updateRoadmap() {
        showLoading()
        retrofitService.myRoadmap(preferenceRepository.token) { response, code ->
            roadmapResponse.postValue(response)
        }
    }

    fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

}
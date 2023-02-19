package ru.myrosmol.conductor.ui.task

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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.myrosmol.conductor.data.repository.PreferenceRepository
import ru.myrosmol.conductor.databinding.FragmentTaskBinding
import ru.myrosmol.conductor.epoxy.attachment
import ru.myrosmol.conductor.epoxy.question
import ru.myrosmol.conductor.network.RetrofitService
import ru.myrosmol.conductor.network.response.TaskResponse
import ru.myrosmol.conductor.utils.TaskType
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment : Fragment() {

    lateinit var binding: FragmentTaskBinding
    val args: TaskFragmentArgs by navArgs()
    val taskResponse = MutableLiveData<TaskResponse>()

    val onAttachmentClickedListener = object : OnAttachmentClickedListener {
        override fun onAttachmentClicked(url: String) {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    val currentAnswers = arrayListOf<String>()

    val onQuestionTextChangedListener = object : OnQuestionTextChangedListener {
        override fun onQuestionTextChanged(position: Int, text: String) {
            currentAnswers[position] = text
        }
    }

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    @Inject
    lateinit var retrofitService: RetrofitService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("Task ${args.index}")

        binding.attachmentsRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.poll.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        taskResponse.observe(viewLifecycleOwner) {

            binding.money.text = "+${it.coins}"
            binding.moneyInHint.text = "+${it.coins}"

            if (it.attachments.isNullOrEmpty()) {
                binding.attachmentsCard.visibility = View.GONE
            } else {
                binding.attachmentsCard.visibility = View.VISIBLE
                it.attachments.forEach {
                    var i = 0
                    binding.attachmentsRecycler.withModels {
                        attachment {
                            id(i++)
                            title(it.title)
                            url(it.url)
                            onAttachmentClicked(onAttachmentClickedListener)
                        }
                    }
                }
            }

            var description = it.text!!
            while (description.contains("  "))
                description = description.replace("  ", " ")
            while (description.contains("\n\n\n"))
                description = description.replace("\n\n\n", "\n\n")

            binding.title.text = it.title
            binding.description.text = description

            when (it.type) {
                "auto_test" -> {
                    if (it.completed!!) {
                        binding.hintCard.visibility = View.VISIBLE
                        binding.hint.text =
                            "Тестирование завершено успешно. Начислено баллов:"
                        binding.pollTitle.visibility = View.GONE
                        binding.poll.visibility = View.GONE
                        binding.sendAnswers.visibility = View.GONE
                    } else {
                        binding.hintCard.visibility = View.GONE
                        binding.pollTitle.text = "Тестирование"
                        binding.pollTitle.visibility = View.VISIBLE
                        binding.poll.visibility = View.VISIBLE
                        binding.sendAnswers.visibility = View.VISIBLE
                    }
                    binding.moneyCard.visibility = if (it.completed) View.GONE else View.VISIBLE
                    binding.moneyCardInHint.visibility =
                        if (it.completed) View.VISIBLE else View.GONE
                }

                "hr_confirmation" -> {
                    binding.hintCard.visibility = View.VISIBLE
                    binding.pollTitle.visibility = View.GONE
                    binding.poll.visibility = View.GONE
                    binding.sendAnswers.visibility = View.GONE

                    binding.hint.text = if (it.completed!!)
                        "Задание выполнено и проверено HR'ом. Начислено баллов:"
                    else
                        "Ожидание подтверждения выполнения задачи HR'ом."

                    binding.moneyCard.visibility = if (it.completed) View.GONE else View.VISIBLE
                    binding.moneyCardInHint.visibility =
                        if (it.completed) View.VISIBLE else View.GONE
                }

                else -> {
                    if (it.completed!!) {
                        binding.hintCard.visibility = View.VISIBLE
                        binding.hint.text =
                            "Опрос завершён успешно. Начислено баллов:"
                        binding.pollTitle.visibility = View.GONE
                        binding.poll.visibility = View.GONE
                        binding.sendAnswers.visibility = View.GONE
                    } else {
                        binding.hintCard.visibility = View.GONE
                        binding.pollTitle.visibility = View.VISIBLE
                        binding.pollTitle.text = "Опрос"
                        binding.poll.visibility = View.VISIBLE
                        binding.sendAnswers.visibility = View.VISIBLE
                    }
                    binding.moneyCard.visibility = if (it.completed) View.GONE else View.VISIBLE
                    binding.moneyCardInHint.visibility =
                        if (it.completed) View.VISIBLE else View.GONE
                }
            }

            binding.poll.withModels {
                var i = 0
                currentAnswers.clear()
                it.quizzes?.forEach {
                    if (it == null)
                        return@forEach

                    question {
                        id(i)
                        title(it.question)
                        number(i++)
                        onQuestionTextChangedListener(onQuestionTextChangedListener)
                    }
                    currentAnswers.add("")
                }
            }

            binding.loading.visibility = View.GONE
        }

        binding.sendAnswers.setOnClickListener {
            if (currentAnswers.count { it.isEmpty() } == currentAnswers.size)
                return@setOnClickListener

            sendQuizz()
        }

        updateTaskResponse()
    }

    fun updateTaskResponse() {
        binding.loading.visibility = View.VISIBLE
        retrofitService.getTask(preferenceRepository.token, args.index) { response, code ->
            if (code == 200)
                taskResponse.postValue(response)
            else {
                Toast.makeText(context, "$code Попробуйте ещё раз чуть позже.", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    fun sendQuizz() {
        binding.loading.visibility = View.VISIBLE
        retrofitService.sendQuizz(
            preferenceRepository.token,
            args.index,
            currentAnswers
        ) { response, code ->
            if (code == 200) {
                Toast.makeText(context, "Ответы были отправлены.", Toast.LENGTH_SHORT).show()
                updateTaskResponse()
            } else {
                Toast.makeText(context, "$code", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
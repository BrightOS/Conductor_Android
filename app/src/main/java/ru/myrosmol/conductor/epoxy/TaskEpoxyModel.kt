package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.roadmap.OnTaskClickedListener
import ru.myrosmol.conductor.utils.KotlinHolder
import ru.myrosmol.conductor.utils.TaskType

@EpoxyModelClass
abstract class TaskEpoxyModel : EpoxyModelWithHolder<TaskEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_task

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var onTaskClickedListener: OnTaskClickedListener

    @EpoxyAttribute
    var coins = 0

    @EpoxyAttribute
    var taskId = 0

    @EpoxyAttribute
    lateinit var type: TaskType

    @EpoxyAttribute
    var passed = false

    @EpoxyAttribute
    var last = false

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.coins.setText("+$coins")
        holder.title.setText(title)
        holder.passed.setText(
            when (type) {
                TaskType.AUTO -> if (passed) "Тестирование пройдено" else "Есть непройденное тестирование"
                TaskType.HR -> if (passed) "Выполнено" else "Не выполнено"
                TaskType.POLL -> if (passed) "Опрос пройден" else "Опрос не пройден"
            }
        )

        holder.passed.isChecked = passed

        holder.card.setOnClickListener {
            onTaskClickedListener.onTaskClicked(taskId)
        }

        holder.root.background =
            ContextCompat.getDrawable(
                holder.root.context,
                if (last)
                    R.drawable.background_last_task
                else
                    R.drawable.background_task
            )
    }

    inner class Holder : KotlinHolder() {
        val root: ConstraintLayout by bind(R.id.task_root)
        val card: MaterialCardView by bind(R.id.task_card)
        val title: TextView by bind(R.id.title)
        val coins: TextView by bind(R.id.coins)
        val passed: CheckedTextView by bind(R.id.passed)
    }
}
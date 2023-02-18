package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.textfield.TextInputLayout
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.task.OnAttachmentClickedListener
import ru.myrosmol.conductor.ui.task.OnQuestionTextChangedListener
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class QuestionEpoxyModel : EpoxyModelWithHolder<QuestionEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_question

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    var number = 0

    @EpoxyAttribute
    lateinit var onQuestionTextChangedListener: OnQuestionTextChangedListener

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.title.text = title
        holder.number.text = "${number + 1}"
        holder.answer.editText?.doAfterTextChanged {
            onQuestionTextChangedListener.onQuestionTextChanged(
                number,
                it.toString()
            )
        }
    }

    inner class Holder : KotlinHolder() {
        val number: TextView by bind(R.id.question_number)
        val title: TextView by bind(R.id.title)
        val answer: TextInputLayout by bind(R.id.answer)
    }
}
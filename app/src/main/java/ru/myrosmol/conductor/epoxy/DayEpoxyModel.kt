package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.CheckedTextView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.card.MaterialCardView
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.roadmap.OnTaskClickedListener
import ru.myrosmol.conductor.utils.KotlinHolder
import ru.myrosmol.conductor.utils.TaskType

@EpoxyModelClass
abstract class DayEpoxyModel : EpoxyModelWithHolder<DayEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_day

    @EpoxyAttribute
    lateinit var day: String

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.day.setText("День $day")
    }

    inner class Holder : KotlinHolder() {
        val day: TextView by bind(R.id.day_number)
    }
}
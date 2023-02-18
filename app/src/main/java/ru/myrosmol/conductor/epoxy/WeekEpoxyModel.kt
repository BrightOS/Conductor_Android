package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class WeekEpoxyModel : EpoxyModelWithHolder<WeekEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_week

    @EpoxyAttribute
    lateinit var week: String

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.week.setText("Неделя $week")
    }

    inner class Holder : KotlinHolder() {
        val week: TextView by bind(R.id.week_number)
    }
}
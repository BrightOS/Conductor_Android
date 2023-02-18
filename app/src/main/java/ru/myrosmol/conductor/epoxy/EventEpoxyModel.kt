package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class EventEpoxyModel : EpoxyModelWithHolder<EventEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_event

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    lateinit var dateTime: String

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.title.setText(title)
        holder.description.setText(description)
        holder.dateTime.setText(dateTime)
    }

    inner class Holder : KotlinHolder() {
        val title: TextView by bind(R.id.title)
        val description: TextView by bind(R.id.description)
        val dateTime: TextView by bind(R.id.date_time)
    }
}
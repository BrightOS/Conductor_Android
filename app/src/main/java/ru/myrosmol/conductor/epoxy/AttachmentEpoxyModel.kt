package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.task.OnAttachmentClickedListener
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class AttachmentEpoxyModel : EpoxyModelWithHolder<AttachmentEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_attachment

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var url: String

    @EpoxyAttribute
    lateinit var onAttachmentClicked: OnAttachmentClickedListener

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.butt.text = title
        holder.butt.setOnClickListener {
            onAttachmentClicked.onAttachmentClicked(url)
        }
    }

    inner class Holder : KotlinHolder() {
        val butt: TextView by bind(R.id.attachment_butt)
    }
}
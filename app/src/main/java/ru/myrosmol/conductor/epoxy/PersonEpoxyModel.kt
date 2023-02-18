package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.google.android.material.card.MaterialCardView
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.network.OnPersonClickedListener
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class PersonEpoxyModel : EpoxyModelWithHolder<PersonEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_person

    @EpoxyAttribute
    lateinit var position: String

    @EpoxyAttribute
    lateinit var division: String

    @EpoxyAttribute
    lateinit var fullname: String

    @EpoxyAttribute
    var userId = 0

    @EpoxyAttribute
    lateinit var onPersonClickedListener: OnPersonClickedListener

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.position.text = position
        holder.division.text = division
        holder.fullname.text = fullname

        holder.personCard.setOnClickListener {
            onPersonClickedListener.onPersonClicked(userId)
        }
    }

    inner class Holder : KotlinHolder() {
        val personCard: MaterialCardView by bind(R.id.person_card)
        val position: TextView by bind(R.id.position)
        val division: TextView by bind(R.id.division)
        val fullname: TextView by bind(R.id.fullname)
    }
}
package ru.myrosmol.conductor.epoxy

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import ru.myrosmol.conductor.R
import ru.myrosmol.conductor.ui.shop.OnProductBoughtListener
import ru.myrosmol.conductor.ui.task.OnQuestionTextChangedListener
import ru.myrosmol.conductor.utils.KotlinHolder

@EpoxyModelClass
abstract class ProductEpoxyModel : EpoxyModelWithHolder<ProductEpoxyModel.Holder>() {

    override fun getDefaultLayout() = R.layout.item_product

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var description: String

    @EpoxyAttribute
    var alreadyBought = false

    @EpoxyAttribute
    var cost = 0

    @EpoxyAttribute
    var productId = 0

    @EpoxyAttribute
    lateinit var onProductBoughtListener: OnProductBoughtListener

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        holder.title.setText(title)
        holder.description.setText(description)
        holder.cost.setText("$cost")

        if (!alreadyBought) {
            holder.alreadyBought.visibility = View.GONE
            holder.costCard.alpha = 1f

            holder.costCard.setOnClickListener {
                onProductBoughtListener.onProductBought(productId)
            }
        } else {
            holder.alreadyBought.visibility = View.VISIBLE
            holder.costCard.alpha = 0.5f
        }

        Glide.with(holder.picture)
            .load("https://divarteam.ru/static/img/shopper.jpg")
            .into(holder.picture)
    }

    inner class Holder : KotlinHolder() {
        val alreadyBought: TextView by bind(R.id.already_bought)
        val cost: TextView by bind(R.id.cost)
        val costCard: MaterialCardView by bind(R.id.cost_card)
        val title: TextView by bind(R.id.title)
        val description: TextView by bind(R.id.description)
        val picture: ImageView by bind(R.id.picture)
    }
}
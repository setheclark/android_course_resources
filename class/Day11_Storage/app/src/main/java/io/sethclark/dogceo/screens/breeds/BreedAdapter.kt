package io.sethclark.dogceo.screens.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sethclark.dogceo.R
import io.sethclark.dogceo.model.Breed

private const val BREED = 0
private const val SUB_BREED = 1

class BreedAdapter(private val itemClickListener: (text: Breed) -> Unit) :
    RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    var breeds: List<Breed> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class BreedViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(text: String) {
            textView.text = text.capitalize()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        return BreedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    BREED -> R.layout.item_breed
                    SUB_BREED -> R.layout.item_sub_breed
                    else -> throw IllegalArgumentException("Unknown view type")
                }, parent, false
            ) as TextView
        )
    }

    override fun getItemViewType(position: Int) = if (breeds[position].isSubBreed()) SUB_BREED else BREED

    override fun getItemCount(): Int = breeds.size

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val item = breeds[position]
        holder.bind(if (item.isSubBreed()) item.subBreed!! else item.name)
        holder.itemView.setOnClickListener { itemClickListener.invoke(item) }
    }
}
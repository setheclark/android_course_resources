package io.sethclark.dogceo.screens.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sethclark.dogceo.R
import io.sethclark.dogceo.model.Breed
import io.sethclark.dogceo.model.SubBreed

private const val BREED = 0
private const val SUB_BREED = 1

class BreedAdapter(private val data: List<Breed>, private val itemClickListener: (text: Breed) -> Unit) :
    RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

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

    override fun getItemViewType(position: Int) = if (data[position] is SubBreed) SUB_BREED else BREED

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item.name)
        holder.itemView.setOnClickListener { itemClickListener.invoke(item) }
    }
}
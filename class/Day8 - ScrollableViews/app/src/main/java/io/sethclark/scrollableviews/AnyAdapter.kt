package io.sethclark.scrollableviews

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private const val INT_TYPE = 0
private const val STRING_TYPE = 1

class AnyAdapter(private val data: List<Any>, val itemClickListener: (text: Any) -> Unit) :
    RecyclerView.Adapter<AnyAdapter.AnyViewHolder>() {

    class AnyViewHolder(val view: TextView) : RecyclerView.ViewHolder(view) {

        fun onBind(data: Any) {
            view.text = data.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.e("ADAPTER", "getItemViewType - $position")
        val item = data[position]
        return when (item) {
            is Int -> INT_TYPE
            is String -> STRING_TYPE
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnyAdapter.AnyViewHolder {
        Log.e("ADAPTER", "onCreateViewHolder - $viewType")
        val tv = LayoutInflater
            .from(parent.context)
            .inflate(
                when (viewType) {
                    INT_TYPE -> R.layout.int_view
                    STRING_TYPE -> R.layout.string_view
                    else -> throw IllegalArgumentException("Unknown type")
                }, parent, false
            ) as TextView

        return AnyViewHolder(tv)
    }

    override fun getItemCount(): Int {
        Log.e("ADAPTER", "getItemCount")
        return data.size
    }
    
    override fun onBindViewHolder(holder: AnyAdapter.AnyViewHolder, position: Int) {
        Log.e("ADAPTER", "onBindViewHolder - $position")
        holder.onBind(data[position])
        holder.itemView.setOnClickListener { itemClickListener.invoke(data[position]) }

    }
}
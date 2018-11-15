package io.sethclark.fizzbuzzassigment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private const val NUM_TYPE = 0
private const val FIZZ_TYPE = 1
private const val BUZZ_TYPE = 2
private const val FIZZBUZZ_TYPE = 3

class FizzBuzzAdapter(val data: List<Int>) : RecyclerView.Adapter<FizzBuzzAdapter.BaseViewHolder>() {

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        return when {
            item % 15 == 0 -> FIZZBUZZ_TYPE
            item % 5 == 0 -> BUZZ_TYPE
            item % 3 == 0 -> FIZZ_TYPE
            else -> NUM_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            NUM_TYPE -> NumberViewHolder(parent)
            FIZZ_TYPE -> FizzViewHolder(parent)
            BUZZ_TYPE -> BuzzViewHolder(parent)
            FIZZBUZZ_TYPE -> FizzBuzzViewHolder(parent)
            else -> throw IllegalArgumentException("Unknown type $viewType")
        }

    abstract class BaseViewHolder(parent: ViewGroup, textViewLayoutId: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(textViewLayoutId, parent, false)) {
        abstract fun boundText(number: Int): String

        fun onBind(number: Int) {
            (itemView as TextView).text = boundText(number)
        }
    }

    class NumberViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.number_item) {
        override fun boundText(number: Int) = number.toString()
    }

    class FizzViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.fizz_item) {
        override fun boundText(number: Int) = "Fizz"
    }

    class BuzzViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.buzz_item) {
        override fun boundText(number: Int) = "Buzz"
    }

    class FizzBuzzViewHolder(parent: ViewGroup) : BaseViewHolder(parent, R.layout.fizzbuzz_item) {
        override fun boundText(number: Int) = "FizzBuzz"
    }
}

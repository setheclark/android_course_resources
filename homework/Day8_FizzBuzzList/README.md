# Homework

## FizzBuzz List

Write a program that displays the output of the FizzBuzz game (recall the challenge from day 2) in a list.

Each unique item type (number, 'Fizz', 'Buzz' and 'FizzBuzz') should have a layout that is visually distinct from the other types.

Feel free to get creating with the 4 different view types.

I have no real requirements around how that look other than that they are each different.

Here is a bit of code to help get you started:
```kotlin
private const val NUM_TYPE = 0
private const val FIZZ_TYPE = 1
private const val BUZZ_TYPE = 2
private const val FIZZBUZZ_TYPE = 3

class FizzBuzzAdapter(val data: List<Int>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}
```

[Example App Video](./day_8_homework.webm)
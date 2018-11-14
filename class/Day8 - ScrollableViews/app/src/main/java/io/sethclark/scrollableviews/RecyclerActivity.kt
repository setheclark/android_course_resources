package io.sethclark.scrollableviews

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        recyclerView = findViewById(R.id.recyclerView)

        val dataList = (0..200).map {
            if (it % 2 == 0) it.toString()
            else it
        }

        recyclerView.adapter = AnyAdapter(dataList) { item ->
            Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}

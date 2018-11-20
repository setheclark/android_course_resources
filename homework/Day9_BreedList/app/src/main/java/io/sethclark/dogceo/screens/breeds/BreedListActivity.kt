package io.sethclark.dogceo.screens.breeds

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R
import io.sethclark.dogceo.model.SubBreed
import io.sethclark.dogceo.screens.image.EXTRA_KEY_BREED
import io.sethclark.dogceo.screens.image.EXTRA_KEY_SUB_BREED
import io.sethclark.dogceo.screens.image.RandomDogImageActivity

class BreedListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: View
    private lateinit var viewModel: BreedListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breed_list)
        val viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BreedListViewModel::class.java)

        loadingBar = findViewById(R.id.loading_bar)

        recyclerView = findViewById(R.id.breed_list_view)
        recyclerView.layoutManager = LinearLayoutManager(this).apply { orientation = RecyclerView.VERTICAL }

        viewModel.screenState.observe(this, Observer { screenState ->
            screenState?.let {
                when (it) {
                    is BreedListViewModel.ScreenState.LoadingBreedList -> loadingBar.visibility = View.VISIBLE
                    is BreedListViewModel.ScreenState.ErrorMessage -> {
                        loadingBar.visibility = View.GONE
                        Toast.makeText(this, "Failed to retrieve new image url", Toast.LENGTH_LONG).show()
                    }
                    is BreedListViewModel.ScreenState.DisplayBreedList -> {
                        loadingBar.visibility = View.GONE
                        recyclerView.adapter = BreedAdapter(it.response) { breed ->
                            startActivity(Intent(this, RandomDogImageActivity::class.java).apply {
                                when (breed) {
                                    is SubBreed -> {
                                        putExtra(EXTRA_KEY_BREED, breed.parent)
                                        putExtra(EXTRA_KEY_SUB_BREED, breed.name)
                                    }
                                    else -> putExtra(EXTRA_KEY_BREED, breed.name)
                                }
                            })
                        }
                    }
                }
            }
        })
    }
}

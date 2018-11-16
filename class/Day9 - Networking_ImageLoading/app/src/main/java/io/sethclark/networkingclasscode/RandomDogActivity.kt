package io.sethclark.networkingclasscode

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RandomDogActivity : AppCompatActivity() {

    lateinit var dogImageView: ImageView
    lateinit var progressBar: ProgressBar
    lateinit var viewModel: DogImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog)

        val viewModelFactory = Injection.provideViewModelFactory()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DogImageViewModel::class.java)

        dogImageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)

        viewModel.screenState.observe(this, Observer { screenState ->
            when (screenState) {
                is DogImageViewModel.ScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is DogImageViewModel.ScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, screenState.errorText, Toast.LENGTH_LONG).show()
                }
                is DogImageViewModel.ScreenState.LoadImage -> {
                    loadImageUrl(screenState.url)
                }
            }

        })
    }

    private fun loadImageUrl(url: String) {
        Picasso.get().load(url).into(dogImageView, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }

        })
    }
}

package io.sethclark.dogceo.screens.image

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R

const val EXTRA_KEY_BREED = "breed"
const val EXTRA_KEY_SUB_BREED = "subBreed"

class RandomDogImageActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var loadingBar: View

    private lateinit var viewModel: RandomDogImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_dog_image)

        val viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RandomDogImageViewModel::class.java)

        loadingBar = findViewById(R.id.loading_bar)
        imageView = findViewById(R.id.image_view)

        viewModel.screenState.observe(this, Observer { screenState ->
            screenState?.let {
                when (it) {
                    is RandomDogImageViewModel.ScreenState.LoadingDogUrl -> loadingBar.visibility = View.VISIBLE
                    is RandomDogImageViewModel.ScreenState.ErrorMessage -> {
                        showErrorMessage("Failed to retrieve new image url")
                    }
                    is RandomDogImageViewModel.ScreenState.LoadDogImage -> {
                        Picasso.get()
                            .load(it.response)
                            .into(imageView, object : Callback {
                                override fun onSuccess() {
                                    hideLoading()
                                }

                                override fun onError(e: Exception?) {
                                    showErrorMessage(e?.message ?: "Failed to load image")
                                }

                            })
                    }
                }
            }
        })

        if (savedInstanceState == null) {
            viewModel.setBreed(intent.getStringExtra(EXTRA_KEY_BREED), intent.getStringExtra(EXTRA_KEY_SUB_BREED))
        }
    }

    fun showErrorMessage(msg: String) {
        hideLoading()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun hideLoading() {
        loadingBar.visibility = View.GONE
    }
}

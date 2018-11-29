package io.sethclark.dogceo.screens.image


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R
import io.sethclark.dogceo.model.Breed

class DogImageFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var loadingBar: View
    private lateinit var favoriteButton: Button

    private val args by lazy { DogImageFragmentArgs.fromBundle(arguments) }

    private lateinit var viewModel: RandomDogImageViewModel

    var breed: Breed? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = Injection.provideViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RandomDogImageViewModel::class.java)

        viewModel.getBreedFromId(args.breedId).observe(this, Observer { breed ->
            if (breed == null) {
                viewModel.setBreed(null, null)
                favoriteButton.visibility = View.GONE
                this.breed = null
            } else {
                if (this.breed?.isSameBreed(breed) != true) {
                    //New breed was returned from the live data
                    viewModel.setBreed(breed.name, breed.subBreed)
                }
                this.breed = breed
                favoriteButton.visibility = View.VISIBLE
                favoriteButton.setText(if (breed.favorite) R.string.unmark_as_favorite else R.string.mark_as_favorite)
                favoriteButton.setOnClickListener {
                    if (breed.favorite) {
                        viewModel.removeBreedFromFavorites(breed)
                    } else {
                        viewModel.setBreedAsFavorite(breed)
                    }
                }
            }
        })

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
    }

    fun Breed.isSameBreed(breed: Breed) = name == breed.name && subBreed == breed.subBreed


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dog_image, container, false)

        loadingBar = view.findViewById(R.id.loading_bar)
        imageView = view.findViewById(R.id.image_view)
        favoriteButton = view.findViewById(R.id.button_favorite)

        return view
    }

    fun showErrorMessage(msg: String) {
        hideLoading()
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun hideLoading() {
        loadingBar.visibility = View.GONE
    }
}

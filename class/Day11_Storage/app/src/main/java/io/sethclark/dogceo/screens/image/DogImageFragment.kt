package io.sethclark.dogceo.screens.image


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R
import kotlinx.android.synthetic.main.fragment_dog_image.*

class DogImageFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var loadingBar: View
    private val args by lazy { DogImageFragmentArgs.fromBundle(arguments) }

    private lateinit var viewModel: RandomDogImageViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = Injection.provideViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RandomDogImageViewModel::class.java)

        val sp = Injection.provideAppSharedPreferences(context!!)

        var breed: String? = args.breed
        var subBreed: String? = args.subBreed

        if (breed == null) {
            button_favorite.visibility = View.GONE
            breed = sp.getString("breed", null)
            subBreed = sp.getString("subBreed", null)
        } else {
            button_favorite.setOnClickListener {
                sp.edit()
                    .putString("breed", args.breed)
                    .putString("subBreed", args.subBreed)
                    .apply()
            }
        }

        viewModel.setBreed(breed, subBreed)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dog_image, container, false)

        loadingBar = view.findViewById(R.id.loading_bar)
        imageView = view.findViewById(R.id.image_view)

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

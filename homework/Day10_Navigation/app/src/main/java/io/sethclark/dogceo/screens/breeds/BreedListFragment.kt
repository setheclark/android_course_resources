package io.sethclark.dogceo.screens.breeds


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R
import io.sethclark.dogceo.model.SubBreed

class BreedListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: View
    private lateinit var viewModel: BreedListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = Injection.provideViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BreedListViewModel::class.java)

        viewModel.screenState.observe(this, Observer { screenState ->
            screenState?.let {
                when (it) {
                    is BreedListViewModel.ScreenState.LoadingBreedList -> loadingBar.visibility = View.VISIBLE
                    is BreedListViewModel.ScreenState.ErrorMessage -> {
                        loadingBar.visibility = View.GONE
                        Toast.makeText(context, "Failed to retrieve new image url", Toast.LENGTH_LONG).show()
                    }
                    is BreedListViewModel.ScreenState.DisplayBreedList -> {
                        loadingBar.visibility = View.GONE
                        recyclerView.adapter = BreedAdapter(it.response) { breed ->
                            findNavController().navigate(
                                when (breed) {
                                    is SubBreed -> BreedListFragmentDirections.actionShowBreedImage(
                                        breed.parent, breed.name
                                    )
                                    else -> BreedListFragmentDirections.actionShowBreedImage(breed.name, null)
                                }
                            )
                        }
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breed_list, container, false)

        loadingBar = view.findViewById(R.id.loading_bar)

        recyclerView = view.findViewById(R.id.breed_list_view)
        recyclerView.layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.VERTICAL }

        return view
    }
}

package io.sethclark.dogceo.screens.breeds


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.sethclark.dogceo.Injection
import io.sethclark.dogceo.R

class BreedListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingBar: View
    private lateinit var viewModel: BreedListViewModel
    private lateinit var adapter: BreedAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = Injection.provideViewModelFactory(context!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BreedListViewModel::class.java)

        adapter = BreedAdapter { breed ->
            findNavController().navigate(
                if (breed.isSubBreed()) {
                    BreedListFragmentDirections.actionShowBreedImage(
                        breed.name, breed.subBreed
                    )
                } else {
                    BreedListFragmentDirections.actionShowBreedImage(breed.name, null)
                }
            )
        }
        recyclerView.adapter = adapter


        viewModel.breeds.observe(this, Observer { breeds ->
            if (breeds.isEmpty()) {
                loadingBar.visibility = View.VISIBLE
            } else {
                loadingBar.visibility = View.GONE
                adapter.breeds = breeds
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

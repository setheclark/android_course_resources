package io.sethclark.dogceo.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.sethclark.dogceo.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_breedList.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionShowBreedList())
        }

        button_randomImage.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionRandomImage(null, null))
        }
    }
}

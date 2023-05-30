package com.ikbalabki.keysearch.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ikbalabki.keysearch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityOnMapFragment : Fragment() {
    private val viewModel: CitiesViewModel by activityViewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        viewModel.selectedCity.observe(viewLifecycleOwner) {
            val coords = LatLng(it.coord.lat, it.coord.lon)
            googleMap.addMarker(MarkerOptions().position(coords).title(it.name))

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 10f) )
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_on_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}
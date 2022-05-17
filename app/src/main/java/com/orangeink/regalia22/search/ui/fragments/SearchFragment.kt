package com.orangeink.regalia22.search.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.orangeink.regalia22.databinding.FragmentSearchBinding
import com.orangeink.regalia22.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun search(query: String) {
        hideKeyboard(requireActivity())/*
        binding.progressSearch.visibility = View.VISIBLE
        binding.tvTrending.visibility = View.GONE
        binding.rvSearch.visibility = View.GONE*/
    }

}
package com.example.mentalarithmetic.ui.games.stolbci

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mentalarithmetic.R

class StolbciFragment : Fragment() {

    companion object {
        fun newInstance() = StolbciFragment()
    }

    private lateinit var viewModel: StolbciViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stolbci, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StolbciViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
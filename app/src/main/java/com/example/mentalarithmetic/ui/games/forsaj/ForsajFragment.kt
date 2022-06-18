package com.example.mentalarithmetic.ui.games.forsaj

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mentalarithmetic.R

class ForsajFragment : Fragment() {

    companion object {
        fun newInstance() = ForsajFragment()
    }

    private lateinit var viewModel: ForsajViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forsaj, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForsajViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
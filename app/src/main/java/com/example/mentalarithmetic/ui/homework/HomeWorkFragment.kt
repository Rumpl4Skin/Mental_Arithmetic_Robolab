package com.example.mentalarithmetic.ui.homework

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mentalarithmetic.databinding.FragmentHomeWorkBinding
import com.example.mentalarithmetic.ui.main.MainViewModel

class HomeWorkFragment : Fragment() {

    companion object {
        fun newInstance() = HomeWorkFragment()
    }

    private lateinit var viewModel: HomeWorkViewModel
    private var _binding: FragmentHomeWorkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeWorkBinding.inflate(inflater, container, false)
        val root: View = binding.root

//activity Model
        val model = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }

        binding.txtHomework.text="Домашнее задание: "+model?.myHomework.toString()
        binding.txtGroupNameHomework.text="Группа: "+model?.user?.groupName.toString()

        activity?.let {
            model?.getHomework()?.observe(it, Observer {
                it?.let {
                    binding.txtHomework.text=it
                }
            })
}
            return root
        }

                    override fun onStart() {
            super.onStart()

        }

                    override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            viewModel = ViewModelProvider(this).get(HomeWorkViewModel::class.java)
            // TODO: Use the ViewModel
        }
        }


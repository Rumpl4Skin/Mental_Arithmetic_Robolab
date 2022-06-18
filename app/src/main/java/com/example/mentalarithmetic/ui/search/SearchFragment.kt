package com.example.mentalarithmetic.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalarithmetic.SearchRecyclerAdapter
import com.example.mentalarithmetic.TreningRecyclerAdapter
import com.example.mentalarithmetic.databinding.FragmentSearchBinding
import com.example.mentalarithmetic.ui.main.MainViewModel

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //activity Model
        var model = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }

        val recyclerView: RecyclerView = binding.searchList
        recyclerView.layoutManager = LinearLayoutManager(context)

        binding.btnSearch?.setOnClickListener {
            val manager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)

            recyclerView.setLayoutManager(manager)
            if (model?.searchUser(binding.edtSearch.text.toString())!=null)
                if(model?.searchUser(binding.edtSearch.text.toString())!!.mail!=""){
                    recyclerView.adapter = SearchRecyclerAdapter(model?.searchUser(binding.edtSearch.text.toString())!!)
                    recyclerView.visibility=View.VISIBLE
                }
                else {recyclerView.visibility=View.GONE
                    Toast.makeText(context,"Пользователь с таким именем не найден", Toast.LENGTH_LONG).show()
                }
            else Toast.makeText(context,"", Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

}
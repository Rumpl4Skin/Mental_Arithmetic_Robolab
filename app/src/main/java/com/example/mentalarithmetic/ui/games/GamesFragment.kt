package com.example.mentalarithmetic.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalarithmetic.R
import com.example.mentalarithmetic.TreningRecyclerAdapter
import com.example.mentalarithmetic.databinding.FragmentGamesBinding


class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gamesViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(GamesViewModel::class.java)

        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        gamesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val recyclerView: RecyclerView = binding.treningItemsRV
        recyclerView.layoutManager = LinearLayoutManager(context)

        val stateClickListener: TreningRecyclerAdapter.OnStateClickListener = object : TreningRecyclerAdapter.OnStateClickListener {
            override fun onStateClick(game: String?, position: Int) {
                val navController = activity?.let { findNavController(it,R.id.nav_host_fragment_activity_main) }
                when(game){
                    "Флэш"->  navController?.navigate(R.id.fleshFragment)
                    "Столбцы"-> navController?.navigate(R.id.gameFlashFragment)
                    "Форсаж"-> navController?.navigate(R.id.gameFlashFragment)
                }

            }
        }
        recyclerView.adapter = TreningRecyclerAdapter(getGamesList(),stateClickListener)

        return root
    }
    private fun getGamesList(): List<String> {
        return this.resources.getStringArray(R.array.games_names).toList()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
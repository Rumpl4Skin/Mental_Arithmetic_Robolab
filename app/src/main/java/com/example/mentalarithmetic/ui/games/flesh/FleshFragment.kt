package com.example.mentalarithmetic.ui.games.flesh

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.example.mentalarithmetic.R
import com.example.mentalarithmetic.databinding.FragmentFleshBinding
import com.example.mentalarithmetic.databinding.FragmentGamesBinding

class FleshFragment : Fragment() {

    private var _binding: FragmentFleshBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FleshFragment()
    }

    private lateinit var viewModel: FleshViewModel

    lateinit var chisla:MutableList<Int>
    var cifr:Int=1
    var interval:Int=1
    var count:Int=0
    var inGame:Boolean=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFleshBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cardGame.visibility=View.GONE
        binding.cardSettingsGame.visibility=View.VISIBLE


        chisla= mutableListOf()

        binding.chip1.setOnClickListener {
            if(binding.chip1.isChecked) chisla.add(1) else chisla.remove(1)
        }
        binding.chip2.setOnClickListener {
            if(binding.chip2.isChecked) chisla.add(2) else chisla.remove(2)
        }
        binding.chip3.setOnClickListener {
            if(binding.chip3.isChecked) chisla.add(3) else chisla.remove(3)
        }
        binding.chip4.setOnClickListener {
            if(binding.chip4.isChecked) chisla.add(4) else chisla.remove(4)
        }
        binding.chip5.setOnClickListener {
            if(binding.chip5.isChecked) chisla.add(5) else chisla.remove(5)
        }
        binding.chip6.setOnClickListener {
            if(binding.chip6.isChecked) chisla.add(6) else chisla.remove(6)
        }
        binding.chip7.setOnClickListener {
            if(binding.chip7.isChecked) chisla.add(7) else chisla.remove(7)
        }
        binding.chip8.setOnClickListener {
            if(binding.chip8.isChecked) chisla.add(8) else chisla.remove(8)
        }
        binding.chip9.setOnClickListener {
            if(binding.chip9.isChecked) chisla.add(9) else chisla.remove(9)
        }
        binding.chipAll.setOnClickListener {
            if(binding.chipAll.isChecked) {
                chisla.clear()
                chisla.add(1)
                chisla.add(2)
                chisla.add(3)
                chisla.add(4)
                chisla.add(5)
                chisla.add(6)
                chisla.add(7)
                chisla.add(8)
                chisla.add(9)
                binding.chip1.isChecked=true
                binding.chip2.isChecked=true
                binding.chip3.isChecked=true
                binding.chip4.isChecked=true
                binding.chip5.isChecked=true
                binding.chip6.isChecked=true
                binding.chip7.isChecked=true
                binding.chip8.isChecked=true
                binding.chip9.isChecked=true
            }
            else{
                chisla.clear()
                binding.chip1.isChecked=false
                binding.chip2.isChecked=false
                binding.chip3.isChecked=false
                binding.chip4.isChecked=false
                binding.chip5.isChecked=false
                binding.chip6.isChecked=false
                binding.chip7.isChecked=false
                binding.chip8.isChecked=false
                binding.chip9.isChecked=false
            }
        }

        binding.edtCifr.doAfterTextChanged {
            cifr=binding.edtCifr.text.toString().toInt()
        }
        binding.edtInterval.doAfterTextChanged {
            interval=binding.edtInterval.text.toString().toInt()
        }

        binding.btnStart.setOnClickListener {

            if(chisla.size==0){
                Toast.makeText(context, "Выберите диапазон", Toast.LENGTH_LONG).show()
            }
            else {
               // binding.imgCount.setImageResource(getResources().getIdentifier("s_"+2+".png" , "drawable", activity?.applicationContext?.packageName))
                binding.imgCount.setImageDrawable(Drawable.createFromPath(activity?.applicationContext?.packageName.toString()+"res/drawable-v24/s_"+8+".PNG"));
                //startGame(chisla,cifr,interval)
                binding.cardGame.visibility=View.VISIBLE
                binding.cardSettingsGame.visibility=View.GONE
            }
        }
        return root
    }

    fun startGame(chisla:MutableList<Int>,cifr:Int,interval:Int) {
        inGame=true
        while(inGame) {
            binding.edtAnswer.visibility=View.GONE
            binding.btnAnswer.visibility=View.GONE
            binding.imgCount.visibility=View.VISIBLE

            val rndImg = (chisla.min().toInt() ..chisla.max().toInt()).random()
            binding.imgCount.setImageResource(getResources().getIdentifier("s_"+rndImg+".png" , "drawable", activity?.applicationContext?.packageName))
            Thread.sleep((interval*1000).toLong())

            binding.imgCount.visibility=View.GONE
            binding.edtAnswer.visibility=View.VISIBLE
            binding.btnAnswer.visibility=View.VISIBLE
            binding.imgCount.visibility=View.GONE
            inGame=false


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FleshViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
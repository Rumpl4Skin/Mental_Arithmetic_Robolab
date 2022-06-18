package com.example.mentalarithmetic.ui.reiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mentalarithmetic.databinding.FragmentReitingBinding
import com.example.mentalarithmetic.ui.main.MainViewModel
import github.com.st235.lib_chartio.ChartioAdaper


class ReitingFragment : Fragment() {

    private var _binding: FragmentReitingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var userRaiting:Int=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(ReitingViewModel::class.java)

        _binding = FragmentReitingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //activity Model
        val model = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }

        userRaiting=if(model?.user?.raiting!=-1) model?.user?.raiting!! else 100
        if(model?.user?.raiting!=-1){
            binding.chart.adapter = RandomChartAdapter("баллов в рейтинге за этот месяц", userRaiting)
        }
        else {
            binding.chart.adapter = RandomChartAdapter("баллов в рейтинге за этот месяц", userRaiting)
            Toast.makeText(context,"У вашей учетной не предусмотрен рейтинг. На графике отображается общая динамика изменения рейтинга в школе", Toast.LENGTH_LONG).show()
        }
            binding.chart.addOnPointSelectedObserver { data ->
                binding.valueTextView.text = (data as String)
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    class RandomChartAdapter(
        private val currency: String,
        private var raiting:Int
    ) : ChartioAdaper() {

        private var size  = 10

        private var values = FloatArray(size)

        init {
            initValues()
        }

        private fun initValues() {
            values = FloatArray(size)
            values[0]= 0.0F
            values[1]= raiting.toFloat()
            for (i in 2 until size) {
                values[i] = (Math.random() * raiting + 20).toFloat()
            }
        }

        override fun getSize(): Int = size

        override fun getY(index: Int): Float = values[index]

        override fun getData(index: Int): Any = String.format("%.0f $currency", values[index])

        fun update() {
            size  = (Math.random() * 50 + 10).toInt()
            initValues()
            notifyDataSetChanged()
        }
    }

}
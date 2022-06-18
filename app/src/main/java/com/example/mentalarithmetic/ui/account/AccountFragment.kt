package com.example.mentalarithmetic.ui.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.mentalarithmetic.databinding.FragmentAccountBinding
import com.example.mentalarithmetic.ui.main.MainViewModel

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                AccountViewModel::class.java
            )

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        var model = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }
        binding.txtName.text=model?.user?.name
        binding.txtEmail.text=model?.user?.mail
        binding.txtPass.text=model?.user?.password
        binding.txtStatus.text=model?.user?.status

        if (model?.sPref?.getString("SAVED_EMAIL", "")!="" && model?.sPref?.getString("SAVED_PASS", "")!="")
            binding.checkSaveData.isChecked=true

        binding.checkSaveData?.setOnClickListener {
            val ed: SharedPreferences.Editor? = model?.sPref?.edit()
            if (binding.checkSaveData.isChecked){
                ed?.putString("SAVED_EMAIL", model?.user?.mail.toString())
                ed?.putString("SAVED_PASS", model?.user?.password.toString())
                ed?.commit()
            }
            else{
                ed?.putString("SAVED_EMAIL", "")
                ed?.putString("SAVED_PASS", "")
                ed?.commit()
            }
        }

        binding.btbExit?.setOnClickListener{
            model?.auth?.signOut()
            activity?.finishAffinity()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
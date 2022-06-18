package com.example.mentalarithmetic.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mentalarithmetic.R
import com.example.mentalarithmetic.databinding.ActivityMainBinding
import com.example.mentalarithmetic.ui.BottomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private val ADMIN_ROLE="ADMIN"
    private val CHILD_ROLE="CHILD"
    private val TEACH_ROLE="TEACH"

    private lateinit var binding: ActivityMainBinding

    lateinit var sPref: SharedPreferences
    lateinit var model: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set to viewModel
        model = ViewModelProviders.of(this).get(MainViewModel::class.java)
        model.context = applicationContext
        model.auth = FirebaseAuth.getInstance(FirebaseApp.getInstance())
        model.UID = model.auth.uid.toString()
        model.sPref = getSharedPreferences("MENTAL", MODE_PRIVATE)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        var appBarConfiguration = AppBarConfiguration(
            setOf(
                //общие пункты
                R.id.accountFragment, R.id.gamesFragment,
                R.id.reitingFragment, R.id.forsajFragment,
                R.id.stolbciFragment, R.id.fleshFragment,
                R.id.gameFlashFragment,
                //админ
                R.id.searchFragment,
                //ученик
                R.id.homeWorkFragment,
                //учитель

            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var intent:Intent=getIntent()


        if (intent != null) {
           // Toast.makeText(applicationContext, intent.getStringExtra("USER_ROLE"), Toast.LENGTH_LONG).show()

            when (intent.getStringExtra("USER_ROLE")) {
                ADMIN_ROLE -> {
                    binding.expandableFabLayoutAdmin.visibility= View.VISIBLE
                    binding.expandableFabLayoutTeach.visibility= View.GONE
                    binding.expandableFabLayoutChild.visibility= View.GONE


                    binding.searchAdmin.setOnClickListener {
                        navController.navigate(R.id.searchFragment)
                    }
                    binding.edtGroup.setOnClickListener {

                    }
                    binding.edtTeach.setOnClickListener {

                    }
                    binding.edtChild.setOnClickListener {

                    }

                }

                CHILD_ROLE -> {
                    binding.expandableFabLayoutChild.visibility= View.VISIBLE
                    binding.expandableFabLayoutTeach.visibility= View.GONE
                    binding.expandableFabLayoutAdmin.visibility= View.GONE


                    binding.viewHomework.setOnClickListener {
                        model.getHomework(model.user.groupName)
                        navController.navigate(R.id.homeWorkFragment)
                    }
                    binding.searchChild.setOnClickListener {
                        navController.navigate(R.id.searchFragment)
                    }
                }
                TEACH_ROLE -> {
                    binding.expandableFabLayoutTeach.visibility= View.VISIBLE
                    binding.expandableFabLayoutAdmin.visibility= View.GONE
                    binding.expandableFabLayoutChild.visibility= View.GONE

                    binding.searchTeach.setOnClickListener {
                        navController.navigate(R.id.searchFragment)
                    }

                    binding.addHomework.setOnClickListener {
                        model.getHomework(model.user.groupName)
                        navController.navigate(R.id.homeWorkFragment)
                    }
                }
            }
        }
        //получение текущего пользователя из бд
        model.getUser(intent.getStringExtra("USER_EMAIL").toString(),intent.getStringExtra("USER_PASS").toString())
    }
}
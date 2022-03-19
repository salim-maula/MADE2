package com.example.capstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.capstone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    private lateinit var barConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        navigationController = findNavController(R.id.main_nav)
        barConfiguration = AppBarConfiguration(navigationController.graph)

        setupActionBarWithNavController(navigationController, barConfiguration)
        supportActionBar?.title = resources.getString(R.string.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        navigationController.navigateUp(barConfiguration)
        return super.onSupportNavigateUp()
    }
}
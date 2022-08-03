package com.murat.dailysmoking

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.murat.dailysmoking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var appBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_graph, R.id.navigation_profile
            )
        )
        val noBottomNavigationIds = listOf(R.id.navigation_splash, R.id.navigation_onboarding)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (noBottomNavigationIds.contains(destination.id)) {
                supportActionBar?.hide()
                navView.isVisible = false
            } else {
                supportActionBar?.show()
                navView.isVisible = true
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration!!)
        navView.setupWithNavController(navController)
    }

}
package com.example.restaurantapplicationgraduationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.restaurantapplicationgraduationproject.ui.restaurantList.RestaurantListFragmentDirections
import com.example.restaurantapplicationgraduationproject.utils.hide
import com.example.restaurantapplicationgraduationproject.utils.show
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> bottomNav.hide()
                R.id.restaurantDetailFragment->bottomNav.hide()
                R.id.mealDetailFragment->bottomNav.hide()
                R.id.onBoardingFragment->bottomNav.hide()
                R.id.loginFragment->bottomNav.hide()
                R.id.registerFragment->bottomNav.hide()

                else -> bottomNav.show()
            }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) || super.onOptionsItemSelected(item)
    }
}
package com.mobileapp.deafassist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mobileapp.deafassist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val viewModel: DeafAssistViewModel by viewModels()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.fab.setOnClickListener {
            navController.navigate(R.id.homeFragment) // Take user back to home (if away)
            navController.navigate(R.id.favoriteListDialogFragment) // Display the Favorites List
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}

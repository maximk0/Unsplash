package com.example.unsplash

//import com.google.android.material.appbar.MaterialToolbar
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.unsplash.databinding.ActivityMainBinding
import com.example.unsplash.ui.viewmodels.AuthViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<MaterialToolbar>(R.id.toolbar)?.let { toolbar ->
            setSupportActionBar(toolbar)
        }

        val navController = addNavigation()
        /*hide bottomNavBar if need authorized*/
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.title = destination.label
            if (destination.id == R.id.navigation_onboarding || destination.id == R.id.navigation_auth) {
                binding.navView.visibility = ViewGroup.GONE
                binding.toolbar.visibility = ViewGroup.GONE
            } else {
                binding.navView.visibility = ViewGroup.VISIBLE
                binding.toolbar.visibility = ViewGroup.VISIBLE
            }
        }

    }

    /* create navController, open onboarding if user not authorized */
    private fun addNavigation(): NavController {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        if (!authViewModel.isAuthorized.value) {
            navController.popBackStack()
            navController.navigate(R.id.navigation_onboarding)
        }

        navView.setupWithNavController(navController)
        return navController
    }

}
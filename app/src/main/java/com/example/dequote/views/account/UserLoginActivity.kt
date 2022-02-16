package com.example.dequote.views.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dequote.R
import com.example.dequote.databinding.ActivityMainBinding
import com.example.dequote.utils.DeQuoteDataStore
import com.example.dequote.views.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class UserLoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var deQuoteDataStore: DeQuoteDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            if (deQuoteDataStore.isLoggedIn.first()) {
                startActivity(
                    Intent(
                        this@UserLoginActivity,
                        HomeActivity::class.java
                    )
                )
                finish()
            } else {
                setupNavController()
                supportActionBar?.hide()
            }
        }


    }

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
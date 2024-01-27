package com.ozanarik.mvvmmovieapp.ui.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMainBinding.inflate(layoutInflater)

        handleBottomNav()

        setContentView(binding.root)
    }

    private fun handleBottomNav(){
        val bottomNavBar = binding.bottomNavBar
        val navHostFragment:NavHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment


        NavigationUI.setupWithNavController(bottomNavBar,navHostFragment.navController)

    }
}
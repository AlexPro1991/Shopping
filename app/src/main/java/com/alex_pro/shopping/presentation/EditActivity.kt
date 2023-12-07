package com.alex_pro.shopping.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.ActivityEditBinding


class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
private lateinit var navController:NavController
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         navController = findNavController(R.id.fragmentContainerView)
       // val appBarConfig = AppBarConfiguration(setOf(
          //  R.id.homeFragmentTest,
          //  R.id.addFragmentTest))
        //setupActionBarWithNavController(navController,appBarConfig)
    }
}
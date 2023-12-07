package com.alex_pro.shopping.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alex_pro.shopping.R
import com.alex_pro.shopping.databinding.ActivityMainBinding
import com.alex_pro.shopping.presentation.fragments.AccountFragment
import com.alex_pro.shopping.presentation.fragments.CartFragment
import com.alex_pro.shopping.presentation.fragments.CategoriesFragment
import com.alex_pro.shopping.presentation.fragments.FavoritesFragment
import com.alex_pro.shopping.presentation.fragments.HomeFragment
import com.alex_pro.shopping.utils.openFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        openFragment(HomeFragment.newInstance())
        onBottomNavClicks()

    }

    private fun onBottomNavClicks() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> openFragment(HomeFragment.newInstance())
                R.id.categories -> openFragment(CategoriesFragment.newInstance())
                R.id.cart -> openFragment(CartFragment.newInstance())
                R.id.favorite -> openFragment(FavoritesFragment.newInstance())
                R.id.account -> openFragment(AccountFragment.newInstance())


            }
            true
        }
    }
}

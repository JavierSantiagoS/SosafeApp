package com.javierestudio.appsosafe.homeModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.javierestudio.appsosafe.R
import com.javierestudio.appsosafe.common.interfacesAux.HomeAux
import com.javierestudio.appsosafe.databinding.ActivityHomeBinding
import com.javierestudio.appsosafe.favoriteModule.FavoriteFragment
import com.javierestudio.appsosafe.mapModule.MapFragment
import com.javierestudio.appsosafe.optionsModule.OptionsFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityHomeBinding

    private lateinit var mActiveFragment: Fragment
    private lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //hideSystemUI()
        setupBottomNav()
    }

    // TODO: 26/01/2022 SI USO EL TECLADO CUANDO ESTOY EN FULLSCREENS SE AUMENTA SU HEIGTH con el método hideSystemUi para hacer fullscreen
    // TODO: 26/01/2022 SI PONGO EL SetOnApllyWIndow se arregla lo anterior  pero cuando saco el teclado se borra el bottom navbar
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, mBinding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun setupBottomNav() {
        //mBinding.bottomNav.setOnApplyWindowInsetsListener(null);
        mFragmentManager = supportFragmentManager

        val mapFragment = MapFragment()
        val favoriteFragment = FavoriteFragment()
        val optionsFragment = OptionsFragment()

        mActiveFragment = mapFragment

        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, optionsFragment, OptionsFragment::class.java.name)
            .hide(optionsFragment).commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, favoriteFragment, FavoriteFragment::class.java.name)
            .hide(favoriteFragment).commit()
        mFragmentManager.beginTransaction()
            .add(R.id.hostFragment, mapFragment, MapFragment::class.java.name).commit()

        mBinding.bottomNav.setOnItemSelectedListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.action_map -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(mapFragment)
                        .commit()
                    mActiveFragment = mapFragment
                    true
                }
                R.id.action_favorite -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(favoriteFragment)
                        .commit()
                    mActiveFragment = favoriteFragment
                    true
                }
                R.id.action_options -> {
                    mFragmentManager.beginTransaction().hide(mActiveFragment).show(optionsFragment)
                        .commit()
                    mActiveFragment = optionsFragment
                    true
                }
                else -> false
            }
        }

        mBinding.bottomNav.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.action_favorite -> (favoriteFragment as HomeAux).goToTop()
            }
        }
    }


}
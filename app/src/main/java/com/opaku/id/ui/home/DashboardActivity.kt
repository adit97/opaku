package com.opaku.id.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.opaku.id.R
import com.opaku.id.databinding.ActivityDashboardBinding
import com.opaku.id.ui.home.fragment.account.AccountFragment
import com.opaku.id.ui.home.fragment.cart.CartFragment
import com.opaku.id.ui.home.fragment.explore.ExploreFragment
import com.opaku.id.ui.home.fragment.home.HomeFragment
import com.opaku.id.ui.home.fragment.offer.OfferFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboardBinding

    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            return@OnNavigationItemSelectedListener when (it.itemId) {
                R.id.m_home -> {
                    openFragment(HomeFragment.newInstance())
                    true
                }
                R.id.m_explore -> {
                    openFragment(ExploreFragment.newInstance())
                    true
                }
                R.id.m_cart -> {
                    openFragment(CartFragment.newInstance())
                    true
                }
                R.id.m_offer -> {
                    openFragment(OfferFragment.newInstance())
                    true
                }
                R.id.m_account -> {
                    openFragment(AccountFragment.newInstance())
                    true
                }
                else -> false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupView()
    }

    private fun setupView() {
        setupBottomNav()
    }

    private fun setupBottomNav() {
        openFragment(HomeFragment.newInstance())
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
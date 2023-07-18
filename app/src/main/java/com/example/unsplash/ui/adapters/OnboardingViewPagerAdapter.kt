package com.example.unsplash.ui.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unsplash.ui.fragments.onboarding.FragmentOne
import com.example.unsplash.ui.fragments.onboarding.FragmentThree
import com.example.unsplash.ui.fragments.onboarding.FragmentTwo


class OnboardingViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Log.d("TAG", "create fragment1")
                FragmentOne()
            }
            1 ->{
                Log.d("TAG", "create fragment2")
                FragmentTwo()
            }
            else -> {
                Log.d("TAG", "create fragment3")
                FragmentThree()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
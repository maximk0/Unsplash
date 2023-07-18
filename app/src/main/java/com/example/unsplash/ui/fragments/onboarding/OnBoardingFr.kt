package com.example.unsplash.ui.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.unsplash.R
import com.example.unsplash.databinding.ViewpagerTablayoutBinding
import com.example.unsplash.ui.adapters.OnboardingViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingFr : Fragment() {

    private var _binding: ViewpagerTablayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = ViewpagerTablayoutBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = OnboardingViewPagerAdapter(requireActivity())
        TabLayoutMediator(binding.pageIndicator, binding.viewPager) { _, _ -> }.attach()
        binding.viewPager.offscreenPageLimit = 1

        /* show/hide button next/prev during slide between fragments */
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.next.isVisible = position != 2
                binding.previous.isVisible = position != 0
            }
        })

        /* next fragment if press button */
        binding.next.setOnClickListener {
            if (getItem() <= binding.viewPager.childCount) {
                binding.viewPager.setCurrentItem(getItem() + 1, true)
            }
        }

        /* prev fragment if press button */
        binding.previous.setOnClickListener {
            if (getItem() != 0) {
                binding.viewPager.setCurrentItem(getItem() - 1, true)
            }
        }

        /* skip onboarding, open authorized fragment */
        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }

    private fun getItem(): Int {
        return binding.viewPager.currentItem
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
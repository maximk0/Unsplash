package com.example.unsplash.ui.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.unsplash.R
import com.example.unsplash.databinding.OnboardingBinding


class FragmentTwo : Fragment() {

    private var _binding: OnboardingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = OnboardingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imageEllipse.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.image_ellipse2, requireContext().theme))
            textOnboardingDescription.text =
                getString(R.string.text_for_onboardin2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.unsplash.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unsplash.R
import com.example.unsplash.databinding.FragmentAuthBinding
import com.example.unsplash.ui.viewmodels.AuthViewModel
import com.example.unsplash.utils.launchAndCollectIn
import com.example.unsplash.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindViewModel() {
        binding.btnSignIn.setOnClickListener { authViewModel.openSignInPage() }

        authViewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }

        authViewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        authViewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_authFragment_to_homeFragment)
        }
    }

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    /* handle authorization result */
    private fun handleAuthResponseIntent(intent: Intent) {
        // try get exception from response (null is success)
        val exception = AuthorizationException.fromIntent(intent)

        // try get exchange request token (null is exception)
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()

        when {
            // auth failed
            exception != null -> authViewModel.onAuthCodeFailed(exception)
            // auth success, exchange token
            tokenExchangeRequest != null -> authViewModel.onAuthCodeSuccess(tokenExchangeRequest)
        }
    }
}
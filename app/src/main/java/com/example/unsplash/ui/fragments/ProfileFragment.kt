package com.example.unsplash.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.unsplash.BUNDLE_ID_KEY
import com.example.unsplash.R
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.databinding.FragmentProfileBinding
import com.example.unsplash.ui.adapters.LikedPhotosAdapter
import com.example.unsplash.ui.viewmodels.ProfileViewModel
import com.example.unsplash.utils.launchAndCollectIn
import com.example.unsplash.utils.resetNavGraph
import com.example.unsplash.utils.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

    private val likedPhotosAdapter = LikedPhotosAdapter(
        onClickPhoto = { searchResponse -> onItemClicked(searchResponse) }
    )

    private val logoutResponse = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        profileViewModel.webLogoutComplete()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileViewModel.loadUserInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add logout button to appBar
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar_menu, menu)
                menu.findItem(R.id.action_share).isVisible = false
                menu.findItem(R.id.action_search).isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_logout -> {
                        profileViewModel.logout()
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.userInfoFlow.collect { user ->

                    user?.username?.let {
                        profileViewModel.submitUsername(it)
                        Log.d("ProfileFragment", "submitUsername: $it")
                    }

                    with(binding) {
                        Picasso.get().load(user?.profileImage?.large).into(avatar)
                        username.text = "${user?.firstName} ${user?.lastName}"
                        nickname.text = user?.username
                        bio.text = user?.bio
                        textLocation.text = user?.location ?: "N/A"
                        textEmail.text = user?.email
                        textDownloads.text = user?.downloads.toString()
                        textFavorite.text = getString(R.string.you_liked, user?.totalLikes)
                        recyclerview.adapter = likedPhotosAdapter
                    }

                    profileViewModel.likedPhotos.onEach {
                        likedPhotosAdapter.setData(it)
                        Log.d("ProfileFragment", "adapter: $it")
                    }.launchIn(viewLifecycleOwner.lifecycleScope)
                }

            }
        }

        profileViewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }

        profileViewModel.logoutPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            logoutResponse.launch(it)
        }

        profileViewModel.logoutCompletedFlow.launchAndCollectIn(viewLifecycleOwner) {
            findNavController().resetNavGraph(R.navigation.mobile_navigation)
        }
    }

    private fun onItemClicked(searchResponse: UnsplashPhotoDto) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_ID_KEY, searchResponse.id)
        findNavController().navigate(R.id.navigation_details, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
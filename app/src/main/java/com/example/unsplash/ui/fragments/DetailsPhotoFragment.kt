package com.example.unsplash.ui.fragments

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.unsplash.BUNDLE_ID_KEY
import com.example.unsplash.PARCELABLE_KEY
import com.example.unsplash.R
import com.example.unsplash.State
import com.example.unsplash.data.network.models.DetailPhotoDto
import com.example.unsplash.data.network.models.Tag
import com.example.unsplash.data.room.UnsplashPhotosEntity
import com.example.unsplash.databinding.FragmentDetailsPhotoBinding
import com.example.unsplash.ui.viewmodels.DetailsPhotoViewModel
import com.example.unsplash.utils.toast
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.*
import java.util.*


@AndroidEntryPoint
class DetailsPhotoFragment : Fragment() {

    private var _binding: FragmentDetailsPhotoBinding? = null
    private val binding get() = _binding!!
    private val photoViewModel: DetailsPhotoViewModel by viewModels()

    private var photoEntity: UnsplashPhotosEntity? = null
    private var photoDto: DetailPhotoDto? = null
    private var deepLinkId: String? = null
    private var searchId: String? = null

    private var target: com.squareup.picasso.Target? = null

    private var isClickLike = photoDto?.likedByUser ?: photoEntity?.likedByUser ?: false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            photoEntity = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                bundle.getParcelable(PARCELABLE_KEY, UnsplashPhotosEntity::class.java)
            else bundle.getParcelable(PARCELABLE_KEY)
            Log.d("DetailsPhotoFragment", "PhotoDetail: ${photoEntity.toString()}")

            val deepLinkArgs: DetailsPhotoFragmentArgs by navArgs()
            deepLinkId = deepLinkArgs.id
            Log.d("DetailsPhotoFragment", "DeepLinkId: $deepLinkId")

            searchId = bundle.getString(BUNDLE_ID_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsPhotoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add share button to appBar
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar_menu, menu)
                menu.findItem(R.id.action_search).isVisible = false
                menu.findItem(R.id.action_logout).isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_share -> {
                        shareContent()
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        getPhotoDetails()

        binding.swipeRefresh.setOnRefreshListener { getPhotoDetails() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isClickLike = false
    }

    // Gets photo details from API, handles state (loading, error, success) and set data to layout
    private fun getPhotoDetails() {
        lifecycleScope.launch {
            photoViewModel.getPhotoDetailsFromApi(photoEntity?.id ?: searchId ?: deepLinkId ?: "")
            Log.d(
                "DetailsPhotoFragment",
                "idPhoto : ${photoEntity?.id ?: searchId ?: deepLinkId ?: ""}"
            )

            repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoViewModel.state.collect { state ->
                    when (state) {
                        State.Loading -> {
                            binding.swipeRefresh.isRefreshing = true
                            Log.d("DetailsPhotoFragment", "Loading")
                        }
                        State.Success -> {
                            Log.d(
                                "DetailsPhotoFragment",
                                "${photoDto.toString()}, idPhotoDetail: ${photoEntity?.id}"
                            )

                            binding.swipeRefresh.isRefreshing = false
                            photoDto = photoViewModel.detailPhoto.value

                            if (photoDto != null) {
                                target = object : com.squareup.picasso.Target {
                                    override fun onBitmapLoaded(
                                        bitmap: Bitmap?,
                                        from: Picasso.LoadedFrom?
                                    ) {

                                        val contentResolver = requireContext().contentResolver

                                        val images: Uri =
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                                            } else {
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                            }

                                        val contentValues = ContentValues()
                                        contentValues.put(
                                            MediaStore.Images.Media.DISPLAY_NAME,
                                            "${photoDto?.id}.jpg"
                                        )
                                        contentValues.put(
                                            MediaStore.Images.Media.MIME_TYPE,
                                            "images/*"
                                        )
                                        val uri = contentResolver.insert(images, contentValues)

                                        val bitmapDrawable = BitmapDrawable(resources, bitmap)
                                        val bitmapForDownload = bitmapDrawable.bitmap
                                        val outputStream = Objects.requireNonNull(uri)
                                            ?.let { contentResolver.openOutputStream(it) }
                                        bitmapForDownload.compress(
                                            Bitmap.CompressFormat.JPEG,
                                            100,
                                            outputStream
                                        )
                                        Objects.requireNonNull(outputStream)
                                        val snackbar = Snackbar.make(
                                            binding.root,
                                            getString(R.string.image_saved),
                                            Snackbar.LENGTH_LONG
                                        )
                                        snackbar.setAction(R.string.open_picture) {
                                            if (uri != null) {
                                                openDownloadedPhoto(uri)
                                            }
                                        }
                                        snackbar.show()
                                        Log.d("TAG", "openDownloadedPhoto: Success")
                                    }

                                    override fun onBitmapFailed(
                                        e: Exception?,
                                        errorDrawable: Drawable?
                                    ) {
                                        toast(R.string.image_not_saved)
                                        Log.d("TAG", "openDownloadedPhoto e: $e")
                                    }

                                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                                        toast(R.string.start_to_save_image)
                                        Log.d(
                                            "TAG",
                                            "openDownloadedPhoto placeholder: $placeHolderDrawable"
                                        )
                                    }
                                }
                                Log.d("TAG", "openDownloadedPhoto target: $target")
                            }

                            setDataToLayout()
                        }
                        State.Error -> {
                            binding.swipeRefresh.isRefreshing = false
                            setDataToLayout()
                            Log.d("DetailsPhotoFragment", "Error")
                        }
                    }
                }
            }
        }
    }

    // Set data to layout
    private fun setDataToLayout() {
        Log.d("DetailsPhotoFragment", "PhotoDetail setDataToLayout: ${photoEntity.toString()}")
        // block of "like"
        binding.likesCountTextView.text = "${photoDto?.likes ?: photoEntity?.likes}"
        // change color of like button
        if (photoDto?.likedByUser == true || photoEntity?.likedByUser == true)
            changeIconLike(R.drawable.ic_like)
        else
            changeIconLike(R.drawable.ic_unlike)
        // like button click
        binding.likeImageView.setOnClickListener {
            isClickLike = !isClickLike
            // if user clicked "like", change drawable and like count
            if (isClickLike) {
                changeIconLike(R.drawable.ic_like)
                binding.likesCountTextView.text =
                    (photoDto?.likes?.plus(1) ?: photoEntity?.likes?.plus(1)).toString()
            } else {
                changeIconLike(R.drawable.ic_unlike)
                binding.likesCountTextView.text =
                    (photoDto?.likes ?: photoEntity?.likes).toString()
            }
            // if user not liked early and clicked like now
            if (photoDto?.likedByUser == false && isClickLike)
                (photoDto?.id)?.let { id ->
                    photoViewModel.onLikeClicked(id = id)
                }
            //if user liked early and clicked dislike now
            if (photoDto?.likedByUser == true && !isClickLike)
                (photoDto?.id)?.let {
                    photoViewModel.onDislikeClicked(id = it)
                }
        }

        // block of photo`s author
        binding.username.text = photoDto?.user?.name ?: photoEntity?.userName
        binding.nickname.text = photoDto?.user?.username ?: photoEntity?.userNickname

        // block of image for the main photo and user's avatar
        (photoDto?.urlPhoto ?: photoEntity?.urlPhoto)?.let {
            Picasso.get()
                .load(photoDto?.urlPhoto?.regular ?: photoEntity?.urlPhoto)
                .placeholder(R.drawable.image_placeholder_loading)
                .error(R.drawable.image_placeholder_error)
                .into(binding.photo)
            Picasso.get()
                .load(photoDto?.user?.profileImage?.medium ?: photoEntity?.userPhoto)
                .placeholder(R.drawable.image_placeholder_avatar)
                .into(binding.avatar)
        }

        // block of location
        binding.locationIcon.setOnClickListener {
            openGoogleMaps()
        }
        binding.locationTextView.text =
            "${photoDto?.location?.country ?: ""} ${photoDto?.location?.city ?: ""}"
        binding.locationTextView.setOnClickListener {
            openGoogleMaps()
        }

        // block of tags
        binding.tags.text = getTags(photoDto?.tags)

        // block of info about exif`s data
        binding.madeWith.text = getString(R.string.made_with, photoDto?.exif?.make ?: "")
        binding.model.text = getString(R.string.model, photoDto?.exif?.model ?: "")
        binding.exposure.text = getString(R.string.exposure, photoDto?.exif?.exposureTime ?: "")
        binding.aperture.text = getString(R.string.aperture, photoDto?.exif?.aperture ?: "")
        binding.focalLength.text = getString(
            R.string.focal_length,
            photoDto?.exif?.focalLength ?: ""
        )
        binding.iso.text = getString(R.string.iso, photoDto?.exif?.iso.toString())

        // block of info about photo`s author
        binding.about.text = getString(
            R.string.about,
            photoDto?.user?.username ?: photoEntity?.userName ?: "",
            photoDto?.user?.bio ?: ""
        )
        binding.nickname.text = photoDto?.user?.username ?: photoEntity?.userNickname

        // block of download
        binding.downloadsCount.text = getString(R.string.downloads, photoDto?.downloads ?: 0L)
        if (target != null) {
            binding.downloadBtn.setOnClickListener {
                Log.d("TAG", "openDownloadedPhoto btn clicked dto: $photoDto")
                // Take to permission from user
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    saveImage()
                } else {
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                }
            }
        } else {
            binding.downloadBtn.visibility = View.GONE
        }
    }

    // Change drawable of like button (like/unlike)
    private fun changeIconLike(idLikeImage: Int) {
        binding.likeImageView.setImageResource(idLikeImage)
    }

    // Add '#' before every tag
    private fun getTags(listTags: List<Tag?>?): String {
        val tags = StringBuilder()
        listTags?.forEach { tag ->
            tags.append("#").append(tag?.title.toString()).append(" ")
        }
        return tags.toString()
    }

    // Intent requests for Google Maps
    private fun openGoogleMaps() {
        val latitude = photoDto?.location?.position?.latitude ?: 0.0//?: 37.7749
        val longitude = photoDto?.location?.position?.longitude ?: 0.0//?: -122.4194
        val label =
            photoDto?.location?.city ?: photoDto?.location?.country ?: photoDto?.location?.position
            ?: "Earth"

        val gmmIntentUri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri))
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(mapIntent)
        } else {
            val webUri =
                Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    // Save image to gallery
    private fun saveImage() {
        Picasso.get().load(photoDto?.urlPhoto?.full).into(target!!)
    }

    // Open downloaded photo
    private fun openDownloadedPhoto(uri: Uri) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
            Log.d("TAG", "openDownloadedPhoto: Success")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "openDownloadedPhoto: $e")
        }
    }

    // Permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage()
            } else {
                toast(R.string.permission_not_granted)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun shareContent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        // Text to share
        shareIntent.putExtra(Intent.EXTRA_TEXT, photoDto?.links?.html ?: photoEntity?.shareLink)
        // Opens chooser for sharing the photo
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }


    private companion object {
        const val REQUEST_CODE = 1
    }

}
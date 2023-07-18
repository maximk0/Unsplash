package com.example.unsplash.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.unsplash.R
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.databinding.PhotosListItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class SearchPhotosAdapter(
    private val onClickPhoto: (UnsplashPhotoDto) -> Unit,
    private val onLikeClicked: (String) -> Unit,
    private val onDislikeClicked: (String) -> Unit
) : PagingDataAdapter<UnsplashPhotoDto, PhotosViewHolder>(SearchPhotosDiffUtilCallback()) {

    init {
        Log.d(TAG_SEARCH, "SearchPhotosAdapter init")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        Log.d(TAG_SEARCH, "SearchPhotosAdapter onCreateViewHolder")
        return PhotosViewHolder(
            PhotosListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    /* sets data`s into view */
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = getItem(position)
        Log.d(TAG_SEARCH, "SearchPhotosAdapter resultID: ${item?.id}")

        var isClickLike = item?.likedByUser ?:false

        // change icon like/unlike
        val changeLikeIcon: (Int) -> Unit = { idLikeImage ->
            holder.binding.likeImageView.setImageResource(idLikeImage)
        }

        if (item?.likedByUser == true) changeLikeIcon(R.drawable.ic_like)
        else changeLikeIcon(R.drawable.ic_unlike)

        with(holder.binding) {
            progressBar.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            errorImage.visibility = View.GONE
            loadingImage.visibility = View.VISIBLE

            likeImageView.setOnClickListener {
                isClickLike = !isClickLike

                // if user clicked icon like change drawable and count of likes
                if (isClickLike) {
                    changeLikeIcon(R.drawable.ic_like)
                    holder.binding.likesCountTextView.text = (item?.likes?.plus(1) ?: 0).toString()
                } else {
                    changeLikeIcon(R.drawable.ic_unlike)
                    holder.binding.likesCountTextView.text = (item?.likes ?: 0).toString()
                }

                item?.id?.let { id ->
                    // if user not liked early and clicked like now
                    if(item.likedByUser == false && isClickLike) onLikeClicked(id)
                    //if user liked early and clicked dislike now
                    if(item.likedByUser == true && !isClickLike) onDislikeClicked(id)
                }
            }

            username.text = item?.user?.name ?: "null"
            nickname.text = item?.user?.username ?: "null"
            likesCountTextView.text = item?.likes.toString()

            Picasso.get().load(item?.urls?.small).into(photo, object : Callback {
                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                    loadingImage.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    errorImage.visibility = View.VISIBLE
                    errorText.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            })
            Picasso.get().load(item?.user?.profileImage?.medium).into(avatar)

            root.setOnClickListener {
                if (item != null) {
                    onClickPhoto(item)
                }
            }
        }
    }
}

class SearchPhotosDiffUtilCallback : DiffUtil.ItemCallback<UnsplashPhotoDto>() {

    override fun areItemsTheSame(
        oldItem: UnsplashPhotoDto, newItem: UnsplashPhotoDto
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UnsplashPhotoDto, newItem: UnsplashPhotoDto
    ): Boolean = oldItem == newItem

}
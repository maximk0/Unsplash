package com.example.unsplash.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.data.room.UnsplashPhotosEntity
import com.example.unsplash.databinding.PhotosListItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PhotosAdapter(
    private val onClickPhoto: (UnsplashPhotosEntity) -> Unit,
    private val onLikeClicked: (String) -> Unit,
    private val onDislikeClicked: (String) -> Unit
) : PagingDataAdapter<UnsplashPhotosEntity, PhotosViewHolder>(UnsplashPhotosDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder =
        PhotosViewHolder(
            PhotosListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )

    /* sets data into view */
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = getItem(position)
        Log.d(TAG_SEARCH, "PhotosAdapter resultID: ${item?.id}")

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

                // if user not liked early and clicked like now
                if(item?.likedByUser == false && isClickLike) onLikeClicked(item.id)
                //if user liked early and clicked dislike now
                if(item?.likedByUser == true && !isClickLike) onDislikeClicked(item.id)
            }

            username.text = item?.userName ?: "null"
            nickname.text = item?.userNickname ?: "null"
            likesCountTextView.text = item?.likes.toString()

            Picasso.get().load(item?.urlPhoto).into(photo, object : Callback {
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
            Picasso.get().load(item?.userPhoto).into(avatar)

            root.setOnClickListener {
                if (item != null) {
                    onClickPhoto(item)
                }
            }
        }
    }
}

class PhotosViewHolder(val binding: PhotosListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
        Log.d(TAG_SEARCH, "PhotosViewHolder init")
    }
}

class UnsplashPhotosDiffUtilCallback : DiffUtil.ItemCallback<UnsplashPhotosEntity>() {

    override fun areItemsTheSame(
        oldItem: UnsplashPhotosEntity, newItem: UnsplashPhotosEntity
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UnsplashPhotosEntity, newItem: UnsplashPhotosEntity
    ): Boolean = oldItem == newItem

}
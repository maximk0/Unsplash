package com.example.unsplash.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.data.network.models.CollectionPhotosDto
import com.example.unsplash.databinding.ItemCollectionBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CollectionPhotosAdapter :
    PagingDataAdapter<CollectionPhotosDto, CollectionPhotosViewHolder>(
        CollectionPhotosDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionPhotosViewHolder =
        CollectionPhotosViewHolder(
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CollectionPhotosViewHolder, position: Int) {
        val item = getItem(position)

        Log.d("CollectionPhotosFragment", "Adapter onBindViewHolder: $item")

        with(holder.binding) {
            progressBar.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            errorImage.visibility = View.GONE
            loadingImage.visibility = View.VISIBLE
            countOfPhotos.visibility = View.GONE
            nameCollection.visibility = View.GONE

            username.text = item?.user?.username ?: ""
            nickname.text = item?.user?.name ?: ""

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
        }
    }

}

class CollectionPhotosViewHolder(val binding: ItemCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class CollectionPhotosDiffUtilCallback : DiffUtil.ItemCallback<CollectionPhotosDto>() {

    override fun areItemsTheSame(
        oldItem: CollectionPhotosDto,
        newItem: CollectionPhotosDto
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CollectionPhotosDto,
        newItem: CollectionPhotosDto
    ): Boolean = oldItem == newItem
}
package com.example.unsplash.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.R
import com.example.unsplash.data.network.models.CollectionsDto
import com.example.unsplash.databinding.ItemCollectionBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CollectionsAdapter(
    private val onClickPhoto: (CollectionsDto) -> Unit,
) : PagingDataAdapter<CollectionsDto, CollectionsViewHolder>(CollectionsDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder =
        CollectionsViewHolder(
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            progressBar.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            errorImage.visibility = View.GONE
            loadingImage.visibility = View.VISIBLE

            countOfPhotos.text =
                holder.itemView.context.getString(R.string.count_of_photos, item?.totalPhotos ?: 0)
            nameCollection.text = item?.title ?: ""
            username.text = item?.user?.username ?: ""
            nickname.text = item?.user?.name ?: ""

            Picasso.get().load(item?.coverPhoto?.urls?.small).into(photo, object : Callback {
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

class CollectionsViewHolder(val binding: ItemCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)

class CollectionsDiffUtilCallback : DiffUtil.ItemCallback<CollectionsDto>() {

    override fun areItemsTheSame(oldItem: CollectionsDto, newItem: CollectionsDto): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CollectionsDto, newItem: CollectionsDto): Boolean =
        oldItem == newItem

}
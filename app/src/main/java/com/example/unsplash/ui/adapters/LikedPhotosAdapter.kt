package com.example.unsplash.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.databinding.ItemCollectionBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class LikedPhotosAdapter(
    private val onClickPhoto: (UnsplashPhotoDto) -> Unit
) : RecyclerView.Adapter<LikedPhotosViewHolder>() {

    private var data: List<UnsplashPhotoDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedPhotosViewHolder {
        return LikedPhotosViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LikedPhotosViewHolder, position: Int) {
        val item = data[position]

        Log.d("ProfileFragment", "Adapter onBindViewHolder: $item")

        with(holder.binding) {
            progressBar.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            errorImage.visibility = View.GONE
            loadingImage.visibility = View.VISIBLE
            countOfPhotos.visibility = View.GONE
            nameCollection.visibility = View.GONE

            username.text = item?.user?.username ?: ""
            nickname.text = item?.user?.name ?: ""

            Picasso.get().load(item?.urls?.small).into(photo, object :
                Callback {
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

    fun setData(data: List<UnsplashPhotoDto>) {
        this.data = data
        notifyDataSetChanged()
    }
}

class LikedPhotosViewHolder(val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root)
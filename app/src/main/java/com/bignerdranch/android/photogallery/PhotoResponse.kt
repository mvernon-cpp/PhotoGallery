package com.bignerdranch.android.photogallery

import androidx.paging.PagedList
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}

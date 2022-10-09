package com.bignerdranch.android.photogallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {

    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView

    private lateinit var photoAdapter: PhotoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoGalleryViewModel =
            ViewModelProviders.of(this).get(PhotoGalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoAdapter = PhotoAdapter()

        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            Observer {
                photoAdapter?.submitList(it as PagedList<GalleryItem>?)
                photoRecyclerView.adapter = photoAdapter
//                    galleryItems -> photoRecyclerView.adapter = PhotoAdapter(galleryItems)
            })
    }

    private class PhotoHolder(itemTextView: TextView)
        : RecyclerView.ViewHolder(itemTextView) {
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

//    private class PhotoAdapter(private val galleryItems: List<GalleryItem>)
//        : RecyclerView.Adapter<PhotoHolder>() {

    private class PhotoAdapter :
        PagedListAdapter<GalleryItem, PhotoHolder>(object : DiffUtil.ItemCallback<GalleryItem>() {

            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean =
                oldItem == newItem
        }
            ){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
                    val textView = TextView(parent.context)
                    return PhotoHolder(textView)
                }

                override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
                    val galleryItem = getItem(position)
                    galleryItem?.title?.let { holder.bindTitle(it) }
                }

//            override fun onCreateViewHolder(
//                parent: ViewGroup,
//                viewType: Int
//            ): PhotoHolder {
//                val textView = TextView(parent.context)
//                return PhotoHolder(textView)
//            }

    //        override fun getItemCount(): Int = galleryItems.size
//            override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
//                val galleryItem = galleryItems[position]
//                holder.bindTitle(galleryItem.title)
            }
        }

    private class GalleryItemDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }


    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }


    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}
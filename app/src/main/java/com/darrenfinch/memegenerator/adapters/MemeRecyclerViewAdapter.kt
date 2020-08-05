package com.darrenfinch.memegenerator.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.memegenerator.R
import com.darrenfinch.memegenerator.models.MemePicture

class MemeRecyclerViewAdapter(private val memePictures: MutableList<MemePicture>, private val itemListener: MemeViewHolder.Listener) : RecyclerView.Adapter<MemeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        return MemeViewHolder(itemListener, LayoutInflater.from(parent.context).inflate(R.layout.meme_item, parent, false))
    }

    override fun getItemCount(): Int {
        return memePictures.size
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.bind(memePictures[position])
    }

    fun updateMemes(newMemePictures: List<MemePicture>) {
        memePictures.clear()
        memePictures.addAll(newMemePictures)
        notifyDataSetChanged()
    }
}
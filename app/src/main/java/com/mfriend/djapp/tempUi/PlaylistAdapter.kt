/*
 * Copyright (c) 2020 Lutron. All rights reserved.
 */
package com.mfriend.djapp.tempUi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfriend.djapp.R
import com.mfriend.djapp.spotifyapi.models.Playlist

/**
 * Created by mfriend on 2020-01-05.
 * TODO mfriend WRITE CLASS HEADER
 */
class PlaylistAdapter(var items: List<Playlist>) :
    RecyclerView.Adapter<PlaylistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlaylistViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bindPlaylist(items[position])
    }
}

class PlaylistViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bindPlaylist(playlist: Playlist) {
        view.findViewById<TextView>(R.id.PlaylistName).text = playlist.name
        view.findViewById<TextView>(R.id.tv_description).text = playlist.description
    }

}
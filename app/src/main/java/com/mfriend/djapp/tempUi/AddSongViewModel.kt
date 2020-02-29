package com.mfriend.djapp.tempUi

import android.util.Log
import androidx.lifecycle.*
import com.mfriend.djapp.db.daos.TrackDao
import com.mfriend.djapp.db.entities.Track
import com.mfriend.djapp.spotifyapi.SpotifyService
import com.mfriend.djapp.spotifyapi.models.PlaylistDto
import kotlinx.coroutines.launch

/**
 * ViewModel for the screen to add a song to a slected playlist
 *
 * @param playlistDto to add songs to
 *
 * Created by mfriend on 2020-02-16.
 */

class AddSongViewModel(
    private val spotifyService: SpotifyService,
    private val trackDao: TrackDao,
    private val playlistDto: PlaylistDto
) :
    ViewModel() {


    /**
     *
     */
    val songs: LiveData<List<Track>> = liveData {
        emit(emptyList())
        emitSource(trackDao.getAll().asLiveData())
    }

    /**
     *  LiveData for result of request
     */
    val requestResult: LiveData<String>
        get() = _requestResult

    private val _requestResult = MutableLiveData<String>()

    private val songUriRegex: Regex = """https://open.spotify.com/track/(.*)\?.*""".toRegex()

    fun fillRequestsList() {
        viewModelScope.launch {
            val tracks = songs.value ?: emptyList()
            if (tracks.isEmpty()) {
                trackDao.insert(
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album"),
                    Track("Song", "lil b", "the album")
                )
            } else {
                trackDao.delete(tracks.random())
            }
        }
    }

    /**
     * Add a song to the selected playlist
     *
     * @param songLink the share link of the song to add to the playlist
     */
    fun addSong(songLink: String) {
        val uri = songUriRegex.matchEntire(songLink)?.groupValues?.get(1) ?: run {
            Log.e("MRF", "didnt match $songLink")
            return
        }
        Log.d("MRF", "found $uri")
        viewModelScope.launch {
            try {
                spotifyService.addSong(playlistDto.id, "spotify:track:$uri")
                _requestResult.value = "Added the song"
            } catch (e: Exception) {
                _requestResult.value = e.message

                Log.e("MRF", "failed to add $uri", e)
            }

        }
    }
}
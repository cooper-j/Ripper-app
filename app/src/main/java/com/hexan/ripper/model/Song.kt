package com.hexan.ripper.model

/**
 * Created by James Cooper on 11/04/2018.
 */
class Song(val id: Long,
           val name: String,
           val mediaUrl: String,
           val album: Album,
           val genres: ArrayList<String>)
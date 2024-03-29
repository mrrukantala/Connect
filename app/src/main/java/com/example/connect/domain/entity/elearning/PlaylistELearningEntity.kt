package com.example.connect.domain.entity.elearning

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PlaylistELearningEntity(
    val idPlaylist: Int,
    val namaPlaylist: String,
    val dibuatPada: String,
    val diupdatePada: String
) : Parcelable

@Keep
@Parcelize
data class PlaylistELearningByIdEntity(
    val idPlaylist: Int,
    val namaPlaylist: String,
    val dibuatPada: String,
    val diupdatePada: String,
    val videos: List<VideoELearningByIdEntity>
) : Parcelable

@Keep
@Parcelize
data class VideoELearningByIdEntity(
    val idVideo: Int,
    val idPlaylist: Int,
    val judul: String,
    val linkVideo: String
): Parcelable

@Keep
@Parcelize
data class VideoELearningEntity(
    val idVideo: Int,
    val idPlaylist: Int,
    val judulVideo: String,
    val linkVideo: String,
    val dibuatPada: String,
    val diupdatePada: String,
    val playlist: PlaylistELearningEntity
): Parcelable

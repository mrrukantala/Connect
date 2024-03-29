package com.example.connect.domain.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class BasicEntity(
    val status: Boolean?,
    val message: String?
) : Parcelable

package com.rifqipadisiliwangi.aplikasigithubuser.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @SerializedName("id")
    val id: Int? ,
    @PrimaryKey
    @SerializedName("login")
    val login: String,
    @SerializedName("type")
    val type: String?  ,
    @SerializedName("avatar_url")
    val avatarUrl: String?
) : Parcelable
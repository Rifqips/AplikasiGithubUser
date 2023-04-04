package com.rifqipadisiliwangi.aplikasigithubuser.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class DataGithubUser (
    @PrimaryKey
    @SerializedName("login")
    val login: String,
    @ColumnInfo(name = "type")
    val type: String?,
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "company")
    val company: String?,
    @ColumnInfo(name = "location")
    val location: String?,
    @ColumnInfo(name = "publicRepos")
    val publicRepos: String? = null
): Parcelable


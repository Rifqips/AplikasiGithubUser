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
    val login: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,
    @ColumnInfo(name = "followers")
    val followers: Int,
    @ColumnInfo(name = "following")
    val following: Int,
    @ColumnInfo(name = "company")
    val company: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "publicRepos")
    val publicRepos: String
): Parcelable


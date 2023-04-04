package com.rifqipadisiliwangi.aplikasigithubuser.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDetail(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    @SerializedName("followers")
    val followers: Int? = null,
    @SerializedName("following")
    val following: Int? = null,
    @SerializedName("company")
    val company: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("public_repos")
    val publicRepos: String? = null
): Serializable
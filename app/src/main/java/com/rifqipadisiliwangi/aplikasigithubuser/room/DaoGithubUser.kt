package com.rifqipadisiliwangi.aplikasigithubuser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoGithubUser {

    @Insert
    fun addFavorite(DataGithubUser: DataGithubUser):Long

    @Query("SELECT * FROM DataGithubUser")
    fun getFavorite() : List<DataGithubUser>

    @Query("SELECT count(*) FROM DataGithubUser WHERE DataGithubUser.login = :login")
    fun checkFavorite(login: Int) : Int

    @Delete
    fun deleteFavorite(DataGithubUser: DataGithubUser) : Int
}
package com.rifqipadisiliwangi.aplikasigithubuser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rifqipadisiliwangi.aplikasigithubuser.model.User

@Dao
interface DaoGithubUser {

    @Insert
    fun addFavorite(DataGithubUser: User):Long

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getFavorite() : List<User>

    @Query("SELECT count(*) FROM User WHERE User.login = :login")
    fun checkFavorite(login: Int) : Int

    @Delete
    fun deleteFavorite(DataGithubUser: User) : Int
}
package com.rifqipadisiliwangi.aplikasigithubuser.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration


@Database(entities = [DataGithubUser::class], version = 2)
abstract class DatabaseGithubUser: RoomDatabase() {

    abstract fun FavoritGithubDao() : DaoGithubUser


    companion object{
        private var INSTANCE : DatabaseGithubUser? = null

        fun getInstance(context : Context): DatabaseGithubUser? {
            if (INSTANCE == null){
                synchronized(DatabaseGithubUser::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseGithubUser::class.java,"github-user.db")
                        .build()

                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
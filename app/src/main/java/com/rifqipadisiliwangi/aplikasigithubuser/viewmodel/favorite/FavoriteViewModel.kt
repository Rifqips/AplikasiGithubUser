package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteViewModel(app: Application) : AndroidViewModel(app)  {

    lateinit var allFavorite : MutableLiveData<List<User>>

    init {
        allFavorite = MutableLiveData()
        getAllHistory()
    }

    fun getAllHistoryObserve(): MutableLiveData<List<User>> {
        return allFavorite
    }

    fun getAllHistory(){
        GlobalScope.launch {
            var favDao = DatabaseGithubUser.getInstance(getApplication())!!.FavoritGithubDao()
            var listFav = favDao.getFavorite()
            allFavorite.postValue(listFav)
        }
    }
}
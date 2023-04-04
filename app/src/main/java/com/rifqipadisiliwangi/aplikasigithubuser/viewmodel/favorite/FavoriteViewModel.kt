package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rifqipadisiliwangi.aplikasigithubuser.room.DataGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteViewModel(app: Application) : AndroidViewModel(app)  {

    lateinit var allHistory : MutableLiveData<List<DataGithubUser>>

    init {
        allHistory = MutableLiveData()
        getAllHistory()
    }

    fun getAllHistoryObserve(): MutableLiveData<List<DataGithubUser>> {
        return allHistory
    }

    fun getAllHistory(){
        GlobalScope.launch {
            var historyDao = DatabaseGithubUser.getInstance(getApplication())!!.FavoritGithubDao()
            var listHistory = historyDao.getFavorite()
            allHistory.postValue(listHistory)
        }
    }
}
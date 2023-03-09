package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.model.SearchResponse
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>()
    val users: LiveData<ArrayList<User>> = _users

    fun searchUsers(query: String) {
        val client = ApiClient.apiInstance.getSearchUsers(query)

        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure searchUsers ${t.message}")
            }
        })
    }
}
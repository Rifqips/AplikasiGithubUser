package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _follow = MutableLiveData<ArrayList<User>>()
    val follow: LiveData<ArrayList<User>> = _follow

    fun setFollowers(username: String) {
        val client = ApiClient.apiInstance.getFollowersUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }

    fun setFollowing(username: String) {
        val client = ApiClient.apiInstance.getFollowingUsers(username)

        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    _follow.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowViewModel", "onFailure setFollowers ${t.message}")
            }
        })
    }
}
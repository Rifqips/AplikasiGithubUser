package com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.model.UserDetail
import com.rifqipadisiliwangi.aplikasigithubuser.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    fun setUserDetail(username: String) {
        val client = ApiClient.apiInstance.getDetailUsers(username)

        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(
                call: Call<UserDetail>,
                response: Response<UserDetail>
            ) {
                if(response.isSuccessful){
                    _userDetail.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure setUserDetail ${t.message}")
            }
        })
    }
}
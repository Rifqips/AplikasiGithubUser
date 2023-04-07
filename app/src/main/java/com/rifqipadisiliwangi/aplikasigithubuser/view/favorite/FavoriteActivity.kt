package com.rifqipadisiliwangi.aplikasigithubuser.view.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.preferences.DataStorePreferences
import com.rifqipadisiliwangi.aplikasigithubuser.view.adapter.FavoriteAdapter
import com.rifqipadisiliwangi.aplikasigithubuser.view.detail.UserDetailActivity
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.favorite.FavoriteViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ThemeViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.UserCallback {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!


    private var favoriteDB : DatabaseGithubUser? = null
    private lateinit var viewModel: FavoriteViewModel
    var adapterFavorite = FavoriteAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_favorite)
        supportActionBar?.elevation = 0f
        favoriteDB = DatabaseGithubUser.getInstance(this)

        val pref = DataStorePreferences.getInstance(dataStore)
        val prefsViewMdel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ThemeViewModel::class.java
        )
        prefsViewMdel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getAllHistoryObserve().observe(this) {
            if(it != null){
                adapterFavorite.setFavorite(it as ArrayList<User>)
            }
        }
        historyVm()

    }

    fun historyVm(){
        adapterFavorite = FavoriteAdapter(this)
        binding.rvFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvFavorite.adapter = adapterFavorite
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUserClick(user: User) {
        val userDetailIntent = Intent(this, UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(userDetailIntent)
    }
    fun getAllFavorite(){
        var data = favoriteDB?.FavoritGithubDao()?.deleteFavorite(User(0,"","",""))
        run {
                adapterFavorite = FavoriteAdapter(this@FavoriteActivity)
                binding.rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.rvFavorite.adapter = adapterFavorite
        }
    }

}
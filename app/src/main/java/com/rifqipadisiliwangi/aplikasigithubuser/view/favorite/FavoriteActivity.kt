package com.rifqipadisiliwangi.aplikasigithubuser.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityFavoriteBinding
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.view.adapter.FavoriteAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!


    private var databaseFavorite : DatabaseGithubUser? = null
    private val adapter : FavoriteAdapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_favorite)
        supportActionBar?.elevation = 0f


        databaseFavorite = DatabaseGithubUser.getInstance(this)
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        getFavorite()

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

    private fun getFavorite(){
        GlobalScope.launch {
            val listFavorite = databaseFavorite?.FavoritGithubDao()?.getFavorite()
            runOnUiThread {
                listFavorite.let {
                    adapter.setFavorite(it!!)
                }
            }
        }

    }
}
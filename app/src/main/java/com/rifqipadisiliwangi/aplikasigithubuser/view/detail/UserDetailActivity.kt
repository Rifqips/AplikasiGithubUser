package com.rifqipadisiliwangi.aplikasigithubuser.view.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.model.UserDetail
import com.rifqipadisiliwangi.aplikasigithubuser.room.DaoGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DataGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.loadImage
import com.rifqipadisiliwangi.aplikasigithubuser.view.adapter.FollowPagerAdapter
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.DetailViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.ByteArrayOutputStream

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel

    private var githubDao : DaoGithubUser? =null
    private var databaseGithubUser : DatabaseGithubUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_detail)
        supportActionBar?.elevation = 0f
        databaseGithubUser = DatabaseGithubUser.getInstance(this)
        githubDao = databaseGithubUser?.FavoritGithubDao()

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        user.login?.let { setupViewModel(it) }
        setupViewPager()
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

    private fun populateDetail(user: UserDetail) {
        with(binding) {
            imgDetailAvatar.loadImage(user.avatarUrl)
            tvDetailName.text = user.name ?: "-"
            tvDetailUsername.text = user.login ?: "-"
            tvDetailFollower.text = getString(R.string.detail_followers, user.followers)
            tvDetailFollowing.text = getString(R.string.detail_following, user.following)
            tvDetailCompany.text = user.company ?: "-"
            tvDetailLocation.text = user.location ?: "-"
            tvDetailRepository.text = user.publicRepos ?: "-"
        }

        binding.btnFavorite.setOnClickListener {
            GlobalScope.async {
                val imgUser = user.avatarUrl.toString()
                val detailName = user.login.toString()
                val userName = user.name.toString()
                val typeUser = user.type.toString()
                val detailFollowers = user.followers.toString().toInt()
                val detailFollowing = user.following.toString().toInt()
                val detailCompany = user.company.toString()
                val detailLokasi = user.location.toString()
                val detailRepo = user.publicRepos.toString()
                val userFavorite = databaseGithubUser?.FavoritGithubDao()?.addFavorite(
                    DataGithubUser(detailName,userName,typeUser,avatarUrl = imgUser,detailFollowers,detailFollowing,detailCompany,detailLokasi,detailRepo)
                )
                runOnUiThread {
                    if (userFavorite !=  0.toLong()){
                        Toast.makeText(this@UserDetailActivity,"Berhasil Menambahkan Favorite", Toast.LENGTH_SHORT).show()
                        Log.e("$userFavorite","berhasil tambah ke favorite")
                        var _isChecked = false
                        _isChecked = !_isChecked
                        binding.btnFavorite.isClickable = _isChecked
                    }else{
                        Toast.makeText(this@UserDetailActivity, "Gagal Menambah Favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.setUserDetail(username)

        detailViewModel.userDetail.observe(this) {
            populateDetail(it)
            showLoading(false)
        }
    }

    private fun setupViewPager() {
        val pagerAdapter = FollowPagerAdapter(this)
        val viewPager = binding.viewPagerDetail
        viewPager.adapter = pagerAdapter
        val tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.app_follower,
            R.string.app_following
        )
    }
}
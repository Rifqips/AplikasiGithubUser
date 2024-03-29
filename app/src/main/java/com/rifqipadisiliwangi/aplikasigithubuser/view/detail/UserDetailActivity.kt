package com.rifqipadisiliwangi.aplikasigithubuser.view.detail

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityUserDetailBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.model.UserDetail
import com.rifqipadisiliwangi.aplikasigithubuser.room.DaoGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.loadImage
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.preferences.DataStorePreferences
import com.rifqipadisiliwangi.aplikasigithubuser.view.adapter.FollowPagerAdapter
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.detail.DetailViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ThemeViewModel
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.home.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel

    private var githubDao : DaoGithubUser? = null
    private var databaseGithubUser : DatabaseGithubUser? = null
    lateinit var sUrl : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.user_detail)
        supportActionBar?.elevation = 0f
        databaseGithubUser = DatabaseGithubUser.getInstance(this)
        githubDao = databaseGithubUser?.FavoritGithubDao()

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

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        user.login?.let { setupViewModel(it) }

        setupViewPager()
        favoriteSetUp()


    }

    private fun favoriteSetUp(){
        with(binding){
            btnFavorite.setOnClickListener {
                GlobalScope.async {
                    val userName = tvDetailUsername.text.toString()
                    val typeUser = tvDetailName.text.toString()

                    val dataFav = databaseGithubUser!!.FavoritGithubDao().addFavorite(User(0,userName,typeUser,sUrl))
                    runOnUiThread {
                        if (dataFav != 0.toLong()){
                            Toast.makeText(this@UserDetailActivity, "Berhasil Menambah Ke Favorite", Toast.LENGTH_SHORT).show()
                            var _isChecked = false
                            _isChecked = !_isChecked
                            binding.btnFavorite.isInvisible = true
                            binding.btnFavoriteInvis.isVisible = true

                        }else{
                            Toast.makeText(this@UserDetailActivity, "Gagal Menambah Ke Favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }

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
        sUrl = user.avatarUrl.toString()

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
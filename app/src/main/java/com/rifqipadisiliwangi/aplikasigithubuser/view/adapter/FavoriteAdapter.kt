package com.rifqipadisiliwangi.aplikasigithubuser.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ItemFavoriteBinding
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ItemUserBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.room.DaoGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DataGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.loadImage
import com.rifqipadisiliwangi.aplikasigithubuser.view.favorite.FavoriteActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FavoriteAdapter(private val callback: UserCallback)
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val mData = ArrayList<DataGithubUser>()

    private lateinit var context : Context
    var githubDatabase: DatabaseGithubUser? = null
    private lateinit var daoGithub : DaoGithubUser
    private var githubData : List<DataGithubUser> = emptyList()
    private var database: DatabaseGithubUser? = null

    fun setFavorite(list: List<DataGithubUser>){
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    interface UserCallback {
        fun onUserClick(user: DataGithubUser)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataGithubUser) {
            with(binding) {
                tvName.text = user.login
                tvUsername.text = user.type
                tvCompany.text = user.company
                tvLocation.text = user.location
                ivUser.loadImage(user.avatarUrl)
                root.setOnClickListener { callback.onUserClick(user) }
                delete.setOnClickListener {
                    githubDatabase = DatabaseGithubUser.getInstance(it.context)
                    GlobalScope.async {
                        githubDatabase?.FavoritGithubDao()?.deleteFavorite(githubData[position])
                        (itemView.context as FavoriteActivity).run {
                            Toast.makeText(it.context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT)
                                .show()
                            (itemView.context as FavoriteActivity).apply {
                                githubDatabase
                            }
                        }
                    }
                    Toast.makeText(it.context, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val view =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        database = DatabaseGithubUser.getInstance(parent.context)
        if (database != null){
            daoGithub = database!!.FavoritGithubDao()
        }
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}
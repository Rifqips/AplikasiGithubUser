package com.rifqipadisiliwangi.aplikasigithubuser.view.adapter

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ItemFavoriteBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.Constant
import com.rifqipadisiliwangi.aplikasigithubuser.room.DaoGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DataGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.loadImage
import com.squareup.picasso.Picasso

class FavoriteAdapter():RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var context : Context
    var githubDatabase: DatabaseGithubUser? = null
    private lateinit var daoGithub : DaoGithubUser
    private var githubData : List<DataGithubUser> = emptyList()
    private var database: DatabaseGithubUser? = null

    class ViewHolder(val binding : ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        val view = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        database = DatabaseGithubUser.getInstance(parent.context)
        if (database != null){
            daoGithub = database!!.FavoritGithubDao()
        }
        return ViewHolder(view)
    }

    fun setFavorite(list: List<DataGithubUser>){
        this.githubData = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        holder.binding.tvUsername.text = githubData[position].login
        holder.binding.tvName.text = githubData[position].name

        val pict = githubData[position]
        Picasso.get()
            .load(Constant.POSTER_PATH + pict.avatarUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .fit().centerCrop()
            .into(holder.view.ivUser)
//        holder.binding.tvFollowing.text = githubData[position].following
//        holder.binding.tvFollower.text = githubData[position].followers
        holder.binding.tvCompany.text = githubData[position].company
        holder.binding.tvLocation.text = githubData[position].location
    }

    override fun getItemCount(): Int {
        return githubData.size
    }
}
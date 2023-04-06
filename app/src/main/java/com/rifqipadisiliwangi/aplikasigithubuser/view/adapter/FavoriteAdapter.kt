package com.rifqipadisiliwangi.aplikasigithubuser.view.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rifqipadisiliwangi.aplikasigithubuser.R
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ItemFavoriteBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.room.DaoGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.room.DatabaseGithubUser
import com.rifqipadisiliwangi.aplikasigithubuser.uitls.loadImage
import com.rifqipadisiliwangi.aplikasigithubuser.view.favorite.FavoriteActivity
import com.rifqipadisiliwangi.aplikasigithubuser.view.home.HomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class FavoriteAdapter(private val callback: UserCallback)
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val mData = ArrayList<User>()

    private lateinit var context : Context
    var githubDatabase: DatabaseGithubUser? = null
    private lateinit var daoGithub : DaoGithubUser
    private var githubData : List<User> = emptyList()
    private var database: DatabaseGithubUser? = null


    fun setFavorite(list: List<User>){
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }

    interface UserCallback {
        fun onUserClick(user: User){}

    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                tvName.text = user.login
                tvUsername.text = user.type
                ivUser.loadImage(user.avatarUrl)
                root.setOnClickListener {
                    callback.onUserClick(user)}

                delete.setOnClickListener {
                    githubDatabase = DatabaseGithubUser.getInstance(it.context)
                    val builder = AlertDialog.Builder(it.context)
                    builder.setTitle(R.string.title)
                    builder.setMessage(R.string.dialogMsg)
                    builder.setNegativeButton("Cancel"){dialogInterface, which ->
                        Toast.makeText(context,"Cancel", Toast.LENGTH_LONG).show()
                    }
                    builder.setPositiveButton("Yes"){dialogInterface, which->
                        run {
                            GlobalScope.async {
                                val del = githubDatabase?.FavoritGithubDao()?.deleteFavorite(user)
                                (itemView.context as FavoriteActivity).run{
                                    (itemView.context as FavoriteActivity).getAllFavorite()
                                }
                            }
                        }
                        Toast.makeText(it.context,"Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        val intent = Intent(it.context, HomeActivity::class.java)
                        it.context.startActivity(intent)
                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
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
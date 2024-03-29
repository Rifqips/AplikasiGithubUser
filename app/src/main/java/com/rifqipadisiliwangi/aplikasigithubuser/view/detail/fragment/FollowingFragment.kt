package com.rifqipadisiliwangi.aplikasigithubuser.view.detail.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.FragmentFollowersBinding
import com.rifqipadisiliwangi.aplikasigithubuser.model.User
import com.rifqipadisiliwangi.aplikasigithubuser.view.adapter.UserAdapter
import com.rifqipadisiliwangi.aplikasigithubuser.view.detail.UserDetailActivity
import com.rifqipadisiliwangi.aplikasigithubuser.viewmodel.detail.FollowViewModel


class FollowingFragment : Fragment(),
    UserAdapter.UserCallback {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val userAdapter = UserAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val extraUser =
            activity?.intent?.getParcelableExtra<User>(UserDetailActivity.EXTRA_USER) as User

        extraUser.login?.let { setupViewModel(it) }

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel(username: String) {
        showLoading(true)
        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowViewModel::class.java)

        followViewModel.setFollowing(username)


        followViewModel.follow.observe(viewLifecycleOwner) {
            if (it != null) {
                userAdapter.setData(it)
                showEmpty(false)
            }
            if (it.isEmpty()) {
                showEmpty(true)
            }
            showLoading(false)
        }
    }

    private fun showEmpty(state: Boolean) {
        binding.tvEmptyFollow.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFollow.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(requireContext())
            rvFollow.adapter = userAdapter
        }
    }

    override fun onUserClick(user: User) {
        val userDetailIntent = Intent(requireActivity(), UserDetailActivity::class.java)
        userDetailIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(userDetailIntent)
    }
}
package com.rifqipadisiliwangi.aplikasigithubuser.view.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivityHomeBinding
import com.rifqipadisiliwangi.aplikasigithubuser.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
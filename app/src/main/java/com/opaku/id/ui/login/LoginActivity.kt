package com.opaku.id.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.session.ISessionManager
import com.opaku.id.core.utils.ButtonClickListener
import com.opaku.id.core.utils.toast
import com.opaku.id.core.utils.writeFileToInternalStorage
import com.opaku.id.databinding.ActivityLoginBinding
import com.opaku.id.ui.dashboard.DashboardActivity
import com.opaku.id.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var sessionManager: ISessionManager

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupFile()
        setupBinding()
    }

    private fun setupFile() {
        val mFile = File(filesDir, "user.json")
        if (!mFile.exists()) {
            writeFileToInternalStorage(
                "[{}]",
                "user.json"
            )
        }
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@LoginActivity
            vm = viewModel
            onLogin = onLogin()
            onRegister = onRegister()
        }
    }

    private fun onLogin() = ButtonClickListener {
        viewModel.login().observe(this, {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if (it.data == 0L) {
                        toast(getString(R.string.login_error_message))
                    } else {
                        sessionManager.isLogin = true
                        startActivity(Intent(this, DashboardActivity::class.java))
                    }
                }
                is Resource.Error -> {
                }
            }
        })
    }

    private fun onRegister() = ButtonClickListener {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
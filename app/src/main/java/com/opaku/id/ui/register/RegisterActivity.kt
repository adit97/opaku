package com.opaku.id.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.opaku.id.R
import com.opaku.id.core.data.Resource
import com.opaku.id.core.data.source.local.session.ISessionManager
import com.opaku.id.core.utils.AnalyticCustomParam
import com.opaku.id.core.utils.ButtonClickListener
import com.opaku.id.core.utils.toast
import com.opaku.id.databinding.ActivityRegisterBinding
import com.opaku.id.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    @Inject
    lateinit var sessionManager: ISessionManager

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private var userId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupBinding()
        setupAnalytic()
        setupViewModel()
    }

    private fun setupViewModel() {
        userId = System.currentTimeMillis()
        viewModel.userModel.value?.id = userId
    }

    private fun setupAnalytic() {
        firebaseAnalytics = Firebase.analytics
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = this@RegisterActivity
            vm = viewModel
            onRegister = onRegister()
            onLogin = onLogin()
        }
    }

    private fun onRegister() = ButtonClickListener {
        viewModel.userModel.value?.apply {
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty() && password == passwordAgain) {
                viewModel.register().observe(this@RegisterActivity, {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            if (it.data == true) {
                                sessionManager.isLogin = true
                                sessionManager.userId = userId
                                firebaseAnalytics.setUserProperty(
                                    AnalyticCustomParam.SuccessfulRegistration,
                                    viewModel.userModel.value?.email
                                )
                                startActivity(
                                    Intent(
                                        this@RegisterActivity,
                                        DashboardActivity::class.java
                                    )
                                )
                            } else {
                                toast(getString(R.string.general_check_your_data))
                            }
                        }
                        is Resource.Error -> {
                        }
                    }
                })
            } else {
                toast(getString(R.string.general_check_your_data))
            }
        }
    }

    private fun onLogin() = ButtonClickListener {
        finish()
    }
}
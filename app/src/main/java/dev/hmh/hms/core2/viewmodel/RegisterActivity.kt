package dev.hmh.hms.core2.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hmh.hms.R
import dev.hmh.hms.databinding.ActivityRegisterBinding
import kotlinx.coroutines.flow.collect
import java.security.MessageDigest

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var str_userName: String
    private lateinit var str_Password: String

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            str_userName = binding.etLoginuserName.text.toString().trim()
            str_Password = binding.etLoginUserPassword.text.toString().trim()
            viewModel.userLogin(str_userName, str_Password)

        }
        backgroundUserLogin()
    }

    private fun backgroundUserLogin() {

        lifecycleScope.launchWhenCreated {
            viewModel.sharedFlow.collect {
                when (it) {
                    is LoginViewModel.CurrentEvent.Failure -> {
                        Toast.makeText(this@RegisterActivity, "" + it.error, Toast.LENGTH_SHORT)
                            .show()
                    }

                    LoginViewModel.CurrentEvent.Loading -> {
                        Toast.makeText(this@RegisterActivity, "Loading", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is LoginViewModel.CurrentEvent.Success -> {
                        Toast.makeText(
                            this@RegisterActivity,
                            "S " + it.resultText,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else -> Unit
                }
            }
        }

    }


}
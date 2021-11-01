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
            str_userName = binding.etLoginuserName.text.toString()
            str_Password = binding.etLoginUserPassword.text.toString()
            backgroundUserLogin()

        }
    }

    private fun backgroundUserLogin() {
        viewModel.userLogin("00000-0000000-0",md5("123"))
        lifecycleScope.launchWhenCreated {
            viewModel.response.collect {
                when (it) {
                    is LoginViewModel.CurrentEvent.Failure -> {
                        Toast.makeText(this@RegisterActivity, "F " + it.error, Toast.LENGTH_SHORT)
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

    fun md5(toEncrypt: String): String {
        return try {
            val digest = MessageDigest.getInstance("md5")
            digest.update(toEncrypt.toByteArray())
            val bytes = digest.digest()
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(String.format("%02X", bytes[i]))
            }
            sb.toString().toLowerCase()
        } catch (exc: Exception) {
            "" // Impossibru!
        }

    }
}
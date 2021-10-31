package dev.hmh.hms.core1.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hmh.hms.core1.data.network.api_state.ApiState
import dev.hmh.hms.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collect
import java.security.MessageDigest

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: UserLoginViewModel by viewModels()
    private lateinit var str_cnic: String
    private lateinit var str_password: String

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {

            btnLogin.setOnClickListener {


                str_cnic = etLoginUserName.text.toString()
                str_password = etLoginUserPassword.text.toString()
                when {
                    str_cnic.isEmpty() -> {
                        Toast.makeText(this@LoginActivity, "CNIC is required", Toast.LENGTH_SHORT)
                            .show()
                    }
                    str_password.isEmpty() -> {
                        Toast.makeText(
                            this@LoginActivity,
                            "Password is required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    else -> {
                        backgroundLoginTask()
                    }
                }
            }
        }
    }

    private fun backgroundLoginTask() {
        lifecycleScope.launchWhenCreated {
            viewModel.apply {
                userLogin(str_cnic, md5(str_password))
                binding.apply {
                    btnLogin.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                response.collect {
                    when (it) {
                        is ApiState.Error -> {
                            binding.apply {
                                binding.apply {
                                    btnLogin.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                            }
                        }
                        is ApiState.Loading -> {
                            binding.apply {
                                btnLogin.visibility = View.GONE
                                progressBar.visibility = View.VISIBLE
                            }
                        }
                        is ApiState.Success -> {
                            binding.apply {
                                btnLogin.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                            }
                            it.data.let { it1 ->
                                Toast.makeText(
                                    this@LoginActivity,
                                    "" + it1?.CITY,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }
            }
            /*        response.observe(this@LoginActivity) {
                        when (it) {
                            is ApiState.Error -> {
                                binding.apply {
                                    binding.apply {
                                        btnLogin.visibility = View.VISIBLE
                                        progressBar.visibility = View.GONE
                                    }
                                }
                            }
                            is ApiState.Loading -> {
                                binding.apply {
                                    btnLogin.visibility = View.GONE
                                    progressBar.visibility = View.VISIBLE
                                }
                            }
                            is ApiState.Success -> {
                                binding.apply {
                                    btnLogin.visibility = View.VISIBLE
                                    progressBar.visibility = View.GONE
                                }
                                it.data.let { it1 ->
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "" + it1?.CITY,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                    }*/

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
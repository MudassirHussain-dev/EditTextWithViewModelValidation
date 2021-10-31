package dev.hmh.hms.core1.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.hmh.hms.databinding.ActivitySharedFlowBinding
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SharedFlowActivity : AppCompatActivity() {

    private val viewModel: ViewModelValidate by viewModels()

    private val binding by lazy {
        ActivitySharedFlowBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

/*        binding.btnLivedata.setOnClickListener {
            viewModel.triggerLiveData()

        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.txtFlow.text = it.toString()
                }
            }
        }*/
        //viewModelClickAction()

        binding.btnOk.setOnClickListener {
            viewModel.convert(binding.etUserName.text.toString(),binding.etPassword.text.toString())

        }

        lifecycleScope.launchWhenCreated {
            viewModel.conversion.collect { event ->
                when (event) {
                    is ViewModelValidate.Event.Success -> {
                        Toast.makeText(
                            this@SharedFlowActivity,
                            "" + event.resultText,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ViewModelValidate.Event.Failure -> {
                        Toast.makeText(
                            this@SharedFlowActivity,
                            "" + event.errorText,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ViewModelValidate.Event.Loading -> {
                        Toast.makeText(this@SharedFlowActivity, "Loading", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> Unit
                }

            }
        }
    }

    /* private fun viewModelClickAction() {
         viewModel.liveData.observe(this@SharedFlowActivity) {
             binding.txtLivedata.text = it
         }
         lifecycleScope.launchWhenCreated {
             viewModel.stateFlow.collectLatest {
                 binding.txtStateflow.text = it
                 Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
             }
         }

     }*/
}
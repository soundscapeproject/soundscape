package com.example.dinhh.soundscape.presentation.screens.main

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogout.setOnClickListener {
            mainViewModel.logout()
        }

        mainViewModel.viewState.observe(this, Observer {
            it?.run(this@MainActivity::handleView)
        })
    }

    fun handleView(viewState: MainViewState) {
        when (viewState) {
            MainViewState.Loading -> {
            }

            is MainViewState.Success -> {
                gotoLoginActivity()
            }

            is MainViewState.Failure -> {
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun gotoLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

package com.example.dinhh.soundscape.presentation.screens.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import com.example.dinhh.soundscape.presentation.screens.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.viewState.observe(this, Observer {
            it?.run(this@SplashActivity::handleView)
        })

        splashViewModel.getLoginState()

    }

    fun handleView(viewState: SplashViewState) {
        when(viewState) {
            SplashViewState.Loading -> {
                //Show loading
            }

            is SplashViewState.Success -> {
                if(viewState.token.isEmpty()) {
                    gotoLoginActivity()
                } else {
                    gotoMainActivity()
                }
            }

            is SplashViewState.Failure -> {
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun gotoMainActivity() {
        val background = object : Thread(){
            override fun run() {
                Thread.sleep(3000)
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        background.start()

    }

    fun gotoLoginActivity() {
        val background = object : Thread(){
            override fun run() {
                Thread.sleep(3000)
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        background.start()
    }
}

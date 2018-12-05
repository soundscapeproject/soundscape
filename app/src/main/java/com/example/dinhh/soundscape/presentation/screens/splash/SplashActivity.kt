package com.example.dinhh.soundscape.presentation.screens.splash

import android.animation.ValueAnimator
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.*
import com.example.dinhh.soundscape.presentation.screens.login.LoginActivity
import com.example.dinhh.soundscape.presentation.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
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

        fullscreen_content.translateX(getHeight().toFloat(),500)
        popupAnimator.apply {
            addUpdateListener {
                fullscreen_content.scaleX = it.animatedValue as Float
                fullscreen_content.scaleY = it.animatedValue as Float
            }
            start()
        }
        txt_application_name.translateY(getHeight().toFloat(),500)
        txt_application_slogan.translateY(getHeight().toFloat(),500)

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

    private val popupAnimator = ValueAnimator.ofFloat(1f, 0.9f)
        .also {
            it.duration = 500
            it.repeatMode = ValueAnimator.REVERSE
            it.repeatCount = ValueAnimator.INFINITE
        }


}

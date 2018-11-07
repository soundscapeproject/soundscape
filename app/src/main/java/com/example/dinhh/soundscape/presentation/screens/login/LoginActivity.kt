package com.example.dinhh.soundscape.presentation.screens.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dinhh.soundscape.R
import com.example.dinhh.soundscape.common.*
import com.example.dinhh.soundscape.presentation.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            handleBtnLoginClicked()
        }

        loginViewModel.viewState.observe(this, Observer {
            it?.run(this@LoginActivity::handleView)
        })

        txt_appName.translateX(getWidth().toFloat(), 500)
        txt_hello.translateX(getWidth().toFloat(), 500)
        txt_welcome.translateX(getWidth().toFloat(), 800)
        usernameEditText.translateX(getWidth().toFloat(), 800)
        passwordEditText.translateY(getHeight().toFloat(), 500)
        btnLogin.translateY(getHeight().toFloat(), 800)
    }

    fun handleBtnLoginClicked() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username or password should not be empty", Toast.LENGTH_SHORT).show()
        } else {
            loginViewModel.login(username, password)
        }
    }

    fun handleView(viewState: LoginViewState) {
        when(viewState) {
            LoginViewState.Loading -> {
                progressBar.show()
            }

            is LoginViewState.Success -> {
                progressBar.gone()
                gotoMainActivity()
            }

            is LoginViewState.Failure -> {
                progressBar.gone()
                Toast.makeText(this, "Error ${viewState.throwable.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}

package com.example.dinhh.soundscape.presentation.screens

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.Token
import com.example.dinhh.soundscape.domain.LoginUseCase
import io.reactivex.disposables.CompositeDisposable

//private val loginUseCase: LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState : LiveData<LoginViewState> = _viewState

    fun login(username: String, password: String) {
        disposibles.add(
            loginUseCase.execute(username, password)
                .doOnSubscribe {
                    _viewState.value = LoginViewState.Loading
                }
                .subscribe({
                    _viewState.value = LoginViewState.Success(it)
                },{
                    _viewState.value = LoginViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class LoginViewState {
    object Loading : LoginViewState()
    data class Success(val token: Token) : LoginViewState()
    data class Failure(val throwable: Throwable) : LoginViewState()
}

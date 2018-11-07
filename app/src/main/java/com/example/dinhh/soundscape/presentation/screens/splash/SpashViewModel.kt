package com.example.dinhh.soundscape.presentation.screens.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.domain.user.GetTokenUseCase
import io.reactivex.disposables.CompositeDisposable

class SplashViewModel(
    private val getLoginStateUseCase: GetTokenUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<SplashViewState>()
    val viewState : LiveData<SplashViewState> = _viewState

    fun getLoginState() {
        disposibles.add(
            getLoginStateUseCase.execute()
                .doOnSubscribe {
                    _viewState.value = SplashViewState.Loading
                }
                .subscribe({
                    _viewState.value =
                            SplashViewState.Success(it)
                },{
                    _viewState.value =
                            SplashViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class SplashViewState {
    object Loading : SplashViewState()
    data class Success(val token: String) : SplashViewState()
    data class Failure(val throwable: Throwable) : SplashViewState()
}

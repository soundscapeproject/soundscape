package com.example.dinhh.soundscape.presentation.screens.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.domain.user.LogoutUseCase
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<MainViewState>()
    val viewState : LiveData<MainViewState> = _viewState

    fun logout() {
        disposibles.add(
            logoutUseCase.execute()
                .doOnSubscribe {
                    _viewState.value = MainViewState.Loading
                }
                .subscribe({
                    _viewState.value =
                            MainViewState.Success
                },{
                    _viewState.value =
                            MainViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class MainViewState {
    object Loading : MainViewState()
    object Success : MainViewState()
    data class Failure(val throwable: Throwable) : MainViewState()
}

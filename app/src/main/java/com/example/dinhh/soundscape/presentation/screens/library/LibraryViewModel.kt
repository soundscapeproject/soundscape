package com.example.dinhh.soundscape.presentation.screens.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.domain.user.LogoutUseCase
import io.reactivex.disposables.CompositeDisposable

class LibraryViewModel (
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<LibraryViewState>()
    val viewState : LiveData<LibraryViewState> = _viewState

    fun logout() {
        disposibles.add(
            logoutUseCase.execute()
                .doOnSubscribe {
                    _viewState.value = LibraryViewState.Loading
                }
                .subscribe({
                    _viewState.value =
                            LibraryViewState.LogoutSuccess
                },{
                    _viewState.value =
                            LibraryViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class LibraryViewState {
    object Loading : LibraryViewState()
    object LogoutSuccess : LibraryViewState()
    data class Failure(val throwable: Throwable) : LibraryViewState()
}

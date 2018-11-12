package com.example.dinhh.soundscape.presentation.screens.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import io.reactivex.disposables.CompositeDisposable

class LibraryViewModel(
    private val beginSearchUseCase: BeginSearchUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<LibraryViewState>()
    val viewState : LiveData<LibraryViewState> = _viewState

    fun beginSearch(selectedCategory: String) {
        disposibles.add(
            beginSearchUseCase.execute(selectedCategory)
                .subscribe({
                    _viewState.value = LibraryViewState.Success
                }, {
                    _viewState.value = LibraryViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }
}
sealed class LibraryViewState {

    object Success : LibraryViewState()
    data class Failure(val throwable: Throwable) : LibraryViewState()
}
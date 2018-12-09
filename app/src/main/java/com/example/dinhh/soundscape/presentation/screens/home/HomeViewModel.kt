package com.example.dinhh.soundscape.presentation.screens.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.domain.soundscape.GetLocalSoundscapesUseCase
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel (
    private val getLocalSoundscapesUseCase: GetLocalSoundscapesUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState : LiveData<HomeViewState> = _viewState

    fun getLocalSoundscapes() {
        disposibles.add(
            getLocalSoundscapesUseCase.execute()
                .doOnSubscribe {
                    _viewState.value = HomeViewState.Loading
                }
                .subscribe({
                    _viewState.value =
                            HomeViewState.GetSoundScapeSuccess(it)
                },{
                    _viewState.value =
                            HomeViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class GetSoundScapeSuccess(val list: List<LocalSoundscape>) : HomeViewState()
    data class Failure(val throwable: Throwable) : HomeViewState()
}

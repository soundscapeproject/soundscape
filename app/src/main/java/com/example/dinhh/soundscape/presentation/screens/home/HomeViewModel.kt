package com.example.dinhh.soundscape.presentation.screens.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.common.logD
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.domain.soundscape.GetLocalSoundscapesUseCase
import com.example.dinhh.soundscape.domain.soundscape.UploadSoundscapeUseCase
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel (
    private val getLocalSoundscapesUseCase: GetLocalSoundscapesUseCase,
    private val uploadSoundscapeUseCase: UploadSoundscapeUseCase
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

    fun uploadSoundScape(localSoundscape: LocalSoundscape) {
        disposibles.add(
            uploadSoundscapeUseCase.execute(localSoundscape)
                .doOnSubscribe {
                    _viewState.value = HomeViewState.Loading
                }
                .subscribe({
                    _viewState.value =
                            HomeViewState.UploadSuccess
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
    data class Failure(val throwable: Throwable) : HomeViewState()

    object UploadSuccess : HomeViewState()
    data class GetSoundScapeSuccess(val list: List<LocalSoundscape>) : HomeViewState()
}

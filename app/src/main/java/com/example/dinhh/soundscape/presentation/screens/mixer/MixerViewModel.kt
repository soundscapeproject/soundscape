package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.domain.soundscape.*
import io.reactivex.disposables.CompositeDisposable

class MixerViewModel(
    private val playSoundScapesUseCase: PlaySoundScapesUseCase,
    private val stopSoundsScapeUseCase: StopSoundsScapeUseCase,
    private val playSingleSoundScapeUseCase: PlaySingleSoundScapeUseCase,
    private val stopSingleSoundScapeUseCase: StopSingleSoundScapeUseCase,
    private val getSoundScapeUseCase: GetSoundScapeUseCase
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData<MixerViewState>()
    val viewState : LiveData<MixerViewState> = _viewState

    fun playSoundScapes() {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            playSoundScapesUseCase.execute()
                .subscribe({
                    _viewState.value =
                            MixerViewState.Success
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun stopSoundScapes() {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            stopSoundsScapeUseCase.execute()
                .subscribe({
                    _viewState.value =
                            MixerViewState.Success
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun getSoundScapes() {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            getSoundScapeUseCase.execute()
                .subscribe({
                    _viewState.value =
                            MixerViewState.GetSoundScapesSuccess(it)
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun playSingleSoundScape(index: Int) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            playSingleSoundScapeUseCase.execute(index)
                .subscribe({
                }, {
                })
        )
    }

    fun stopSingleSoundScape(index: Int) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
           stopSingleSoundScapeUseCase.execute(index)
                .subscribe({
                }, {
                })
        )
    }

    fun removeSingleSoundScape(index: Int) {
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
sealed class MixerViewState {
    object Loading: MixerViewState()
    object Success : MixerViewState()
    data class Failure(val throwable: Throwable) : MixerViewState()

    data class GetSoundScapesSuccess(val soundScapeItems: MutableList<SoundscapeItem>): MixerViewState()
}
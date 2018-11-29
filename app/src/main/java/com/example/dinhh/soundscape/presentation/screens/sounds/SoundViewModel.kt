package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import io.reactivex.disposables.CompositeDisposable

class SoundViewModel(
    private val beginSearchUseCase: BeginSearchUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val stopSoundUseCase: StopSoundUseCase
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData<SoundViewState>()
    val viewState : LiveData<SoundViewState> = _viewState

    fun beginSearch(selectedCategory: String) {
        _viewState.value =
                SoundViewState.Loading
        disposables.add(
            beginSearchUseCase.execute(selectedCategory)
                .subscribe({
                    _viewState.value =
                            SoundViewState.Success(it)
                }, {
                    _viewState.value =
                            SoundViewState.Failure(it)
                })
        )
    }

    fun playSound(selectedSound: String) {
        _viewState.value =
                SoundViewState.PlayLoading
        disposables.add(
           playSoundUseCase.execute(selectedSound)
                .subscribe({
                    _viewState.value =
                            SoundViewState.PlaySuccess
                }, {
                    _viewState.value =
                            SoundViewState.Failure(it)
                })
        )
    }

    fun stopSound() {
        disposables.add(
            stopSoundUseCase.execute()
                .subscribe({
                    _viewState.value = SoundViewState.StopSuccess
                }, {
                    _viewState.value = SoundViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
sealed class SoundViewState {

    object Loading: SoundViewState()
    object PlayLoading: SoundViewState()
    object PlaySuccess: SoundViewState()
    object StopSuccess: SoundViewState()
    data class Success(val listSound: List<List<Sound>>) : SoundViewState()
    data class Failure(val throwable: Throwable) : SoundViewState()
}
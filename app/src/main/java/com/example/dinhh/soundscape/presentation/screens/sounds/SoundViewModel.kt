package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.Sound
import com.example.dinhh.soundscape.domain.library.AddSelectedSoundUseCase
import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import io.reactivex.disposables.CompositeDisposable

class SoundViewModel(
    private val beginSearchUseCase: BeginSearchUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val stopSoundUseCase: StopSoundUseCase,
    private val addSelectedSoundUseCase: AddSelectedSoundUseCase
): ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData<SoundViewState>()
    val viewState : LiveData<SoundViewState> = _viewState

    var playingIndex = -1

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
                            SoundViewState.PlayFinish
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
                }, {
                    _viewState.value = SoundViewState.Failure(it)
                })
        )
    }

    fun addSelectedSound(selectedSound: String, title: String, length: Int, category: String, volume: Int) {
        _viewState.value =
                SoundViewState.AddSelectedLoading
        disposables.add(
            addSelectedSoundUseCase.execute(selectedSound, title, length, category, volume)
                .subscribe({
                    _viewState.value =
                            SoundViewState.AddSelectedSoundSuccess
                }, {
                    _viewState.value =
                            SoundViewState.Failure(it)
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
    object PlayFinish: SoundViewState()
    object PlayLoading: SoundViewState()
    object AddSelectedLoading: SoundViewState()
    object AddSelectedSoundSuccess: SoundViewState()
    data class Success(val listSound: List<List<Sound>>) : SoundViewState()
    data class Failure(val throwable: Throwable) : SoundViewState()
}
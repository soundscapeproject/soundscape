package com.example.dinhh.soundscape.presentation.screens.savedrecord

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import com.example.dinhh.soundscape.domain.record.GetRecordsUseCase
import com.example.dinhh.soundscape.presentation.screens.sounds.SoundViewState
import io.reactivex.disposables.CompositeDisposable

class SavedRecordViewModel(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val stopSoundUseCase: StopSoundUseCase
): ViewModel() {

    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<SavedSoundViewState>()
    val viewState : LiveData<SavedSoundViewState> = _viewState

    var playingIndex: Int = -1



    fun getRecords() {
        disposibles.add(
            getRecordsUseCase.execute()
                .subscribe({
                    _viewState.value = SavedSoundViewState.GetRecordsSuccess(it)
                },{
                    _viewState.value = SavedSoundViewState.Failure(it)
                })
        )
    }

    fun playSound(selectedSound: String) {
        disposibles.add(
            playSoundUseCase.execute(selectedSound)
                .subscribe({
                    _viewState.value =
                            SavedSoundViewState.PlayFinish
                }, {
                    _viewState.value =
                            SavedSoundViewState.Failure(it)
                })
        )
    }

    fun stopSound() {
        disposibles.add(
            stopSoundUseCase.execute()
                .subscribe({
                }, {
                    _viewState.value = SavedSoundViewState.Failure(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }
}

sealed class SavedSoundViewState {
    object Loading: SavedSoundViewState()
    object PlayFinish: SavedSoundViewState()
    data class Failure(val throwable: Throwable) : SavedSoundViewState()
    data class GetRecordsSuccess(val localRecords: List<LocalRecord>) : SavedSoundViewState()
}
package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.data.entity.RemoteSound
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.domain.library.BeginSearchUseCase
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.library.StopSoundUseCase
import com.example.dinhh.soundscape.domain.record.DeleteRecordUseCase
import com.example.dinhh.soundscape.domain.record.GetRecordsUseCase
import com.example.dinhh.soundscape.domain.soundscape.AddSoundScapeUseCase
import io.reactivex.disposables.CompositeDisposable

class SoundViewModel(
    private val beginSearchUseCase: BeginSearchUseCase,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val stopSoundUseCase: StopSoundUseCase,
    private val addSoundScapeUseCase: AddSoundScapeUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase
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
                            SoundViewState.GetRemoteSoundSuccess(it)
                }, {
                    _viewState.value =
                            SoundViewState.Failure(it)
                })
        )
    }

    fun getRecords() {
        disposables.add(
            getRecordsUseCase.execute()
                .subscribe({
                    _viewState.value = SoundViewState.GetRecordsSuccess(it)
                },{
                    _viewState.value = SoundViewState.Failure(it)
                })
        )
    }

    fun playSound(selectedSound: String) {
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
        _viewState.value =
                SoundViewState.Loading
        disposables.add(
            stopSoundUseCase.execute()
                .subscribe({
                    _viewState.value =
                            SoundViewState.Sucess
                }, {
                    _viewState.value = SoundViewState.Failure(it)
                })
        )
    }

    fun deleteRecord(id: Long) {
        disposables.add(
            deleteRecordUseCase.execute(id)
                .subscribe({
                }, {
                    _viewState.value = SoundViewState.Failure(it)
                })
        )
    }

    fun addSoundScape(soundscapeItem: SoundscapeItem) {
        disposables.add(
            addSoundScapeUseCase.execute(soundscapeItem)
                .subscribe({
                }, {
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
    object Sucess: SoundViewState()
    object PlayFinish: SoundViewState()
    data class GetRemoteSoundSuccess(val listRemoteSound: List<List<RemoteSound>>) : SoundViewState()
    data class GetRecordsSuccess(val localRecords: List<LocalRecord>) : SoundViewState()
    data class Failure(val throwable: Throwable) : SoundViewState()
}
package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.domain.library.PlaySoundUseCase
import com.example.dinhh.soundscape.domain.record.DeleteTempRecordUseCase
import com.example.dinhh.soundscape.domain.record.SaveRecordUseCase
import com.example.dinhh.soundscape.domain.record.StartRecordUseCase
import com.example.dinhh.soundscape.domain.record.StopRecordUseCase
import io.reactivex.disposables.CompositeDisposable

class RecordViewModel(
    private val startRecordUseCase: StartRecordUseCase,
    private val stopRecordUseCase: StopRecordUseCase,
    private val playSoundUseCase: PlaySoundUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val deleteTempRecordUseCase: DeleteTempRecordUseCase
) : ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<RecordViewState>()
    val viewState: LiveData<RecordViewState> = _viewState
    var fileUrl: String? = null
    var recordLength: Long? = null

    fun startRecording() {
        disposibles.add(
            startRecordUseCase.execute()
                .subscribe({
                    _viewState.value = RecordViewState.Success
                }, {
                    _viewState.value = RecordViewState.Failure(it)
                })
        )
    }

    fun stopRecording() {
        disposibles.add(
            stopRecordUseCase.execute()
                .subscribe({
                    fileUrl = it
                }, {
                    _viewState.value = RecordViewState.Failure(it)
                })
        )
    }

    fun playRecord() {
        disposibles.add(
            playSoundUseCase.execute(fileUrl!!)
                .subscribe({
                    _viewState.value = RecordViewState.Success
                }, {
                    _viewState.value = RecordViewState.Failure(it)
                })
        )
    }

    fun saveRecord(localRecord: LocalRecord) {
        _viewState.value = RecordViewState.SaveRecordLoading
        disposibles.add(
            saveRecordUseCase.execute(localRecord)
                .subscribe({
                    _viewState.value = RecordViewState.SaveRecordSuccess
                }, {
                    _viewState.value = RecordViewState.SaveRecordFailure(it)
                })
        )
    }

    fun deleteTempRecord() {
        disposibles.add(
            deleteTempRecordUseCase.execute()
                .subscribe({}, {})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }
}

sealed class RecordViewState {
    // Common
    object Success : RecordViewState()

    data class Failure(val throwable: Throwable) : RecordViewState()

    //Save
    object SaveRecordLoading : RecordViewState()
    object SaveRecordSuccess : RecordViewState()
    data class SaveRecordFailure(val throwable: Throwable) : RecordViewState()
}
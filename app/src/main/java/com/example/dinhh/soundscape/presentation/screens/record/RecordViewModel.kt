package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.domain.record.*
import io.reactivex.disposables.CompositeDisposable

class RecordViewModel(
    private val startRecordUseCase: StartRecordUseCase,
    private val stopRecordUseCase: StopRecordUseCase,
    private val playRecordUseCase: PlayRecordUseCase,
    private val saveRecordUseCase: SaveRecordUseCase,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val deleteTempRecordUseCase: DeleteTempRecordUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<RecordViewState>()
    val viewState : LiveData<RecordViewState> = _viewState
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
            playRecordUseCase.execute()
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

    fun getRecords() {
        disposibles.add(
            getRecordsUseCase.execute()
                .subscribe({
                    _viewState.value = RecordViewState.GetRecordsSuccess(it)
                },{
                    _viewState.value = RecordViewState.Failure(it)
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

    data class GetRecordsSuccess(val localRecords: List<LocalRecord>) : RecordViewState()
}
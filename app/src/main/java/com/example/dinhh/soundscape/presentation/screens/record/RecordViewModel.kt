package com.example.dinhh.soundscape.presentation.screens.record

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.domain.record.PlayRecordUseCase
import com.example.dinhh.soundscape.domain.record.StartRecordUseCase
import com.example.dinhh.soundscape.domain.record.StopRecordUseCase
import io.reactivex.disposables.CompositeDisposable

class RecordViewModel(
    private val startRecordUseCase: StartRecordUseCase,
    private val stopRecordUseCase: StopRecordUseCase,
    private val playRecordUseCase: PlayRecordUseCase
): ViewModel() {
    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<RecordViewState>()
    val viewState : LiveData<RecordViewState> = _viewState

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
                    _viewState.value = RecordViewState.Success
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

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }

}

sealed class RecordViewState {
    object Success : RecordViewState()
    data class Failure(val throwable: Throwable) : RecordViewState()
}
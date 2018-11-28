package com.example.dinhh.soundscape.presentation.screens.savedrecord

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalRecord
import com.example.dinhh.soundscape.domain.record.GetRecordsUseCase
import io.reactivex.disposables.CompositeDisposable

class SavedRecordViewModel(private val getRecordsUseCase: GetRecordsUseCase): ViewModel() {

    private val disposibles = CompositeDisposable()
    private val _viewState = MutableLiveData<SavedSoundViewState>()
    val viewState : LiveData<SavedSoundViewState> = _viewState


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

    override fun onCleared() {
        super.onCleared()
        disposibles.clear()
    }
}

sealed class SavedSoundViewState {
    object Loading: SavedSoundViewState()
    data class Failure(val throwable: Throwable) : SavedSoundViewState()
    data class GetRecordsSuccess(val localRecords: List<LocalRecord>) : SavedSoundViewState()
}
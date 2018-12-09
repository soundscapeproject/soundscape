package com.example.dinhh.soundscape.presentation.screens.sounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.dinhh.soundscape.data.entity.LocalSoundscape
import com.example.dinhh.soundscape.device.SoundscapeItem
import com.example.dinhh.soundscape.domain.soundscape.*
import io.reactivex.disposables.CompositeDisposable

class MixerViewModel(
    private val playSoundScapesUseCase: PlaySoundScapesUseCase,
    private val stopSoundsScapeUseCase: StopSoundsScapeUseCase,
    private val playSingleSoundScapeUseCase: PlaySingleSoundScapeUseCase,
    private val stopSingleSoundScapeUseCase: StopSingleSoundScapeUseCase,
    private val getSoundScapeUseCase: GetSoundScapeUseCase,
    private val removeSingleSoundScapeUseCase: RemoveSingleSoundScapeUseCase,
    private val saveSoundScapesUseCase: SaveSoundScapesUseCase,
    private val setLoopingUseCase: SetLoopingUseCase,
    private val clearSoundScapesUseCase: ClearSoundScapesUseCase,
    private val addAllSoundscapesUseCase: AddAllSoundscapesUseCase,
    private val getOneLocalSoundscapeUseCase: GetOneLocalSoundscapeUseCase,
    private val updateLocalSoundScapeUseCase: UpdateLocalSoundScapeUseCase
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
                    _viewState.value = MixerViewState.PlaySoundScapeFinish
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
                    _viewState.value = MixerViewState.PlaySoundFinish(it)
                }, {
                })
        )
    }

    fun getOneSoundScape(id: Long) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            getOneLocalSoundscapeUseCase.execute(id)
                .subscribe({
                    _viewState.value = MixerViewState.GetOneLocalSoundScapeSuccess(it)
                }, {
                })
        )
    }

    fun updateSoundScape(localSoundscape: LocalSoundscape) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            updateLocalSoundScapeUseCase.execute(localSoundscape)
                .subscribe({
                    _viewState.value = MixerViewState.UpdateSoundScapeSuccess
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun stopSingleSoundScape(index: Int) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
           stopSingleSoundScapeUseCase.execute(index)
                .subscribe({
                    _viewState.value = MixerViewState.Success
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun removeSingleSoundScape(index: Int) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            removeSingleSoundScapeUseCase.execute(index)
                .subscribe({
                    _viewState.value = MixerViewState.RemoveSoundScapeSuccess
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun saveSoundScape(localSoundscape: LocalSoundscape) {
        _viewState.value =
                MixerViewState.SaveSoundScapeLoading
        disposables.add(
            saveSoundScapesUseCase.execute(localSoundscape)
                .subscribe({
                    _viewState.value = MixerViewState.SaveSoundScapeSuccess
                }, {
                    _viewState.value =
                            MixerViewState.SaveSoundScapeFailure(it)
                })
        )
    }

    fun clearSoundScapes() {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            clearSoundScapesUseCase.execute()
                .subscribe({
                    _viewState.value = MixerViewState.Success
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun addAllSoundScapes(list: List<SoundscapeItem>) {
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            addAllSoundscapesUseCase.execute(list)
                .subscribe({
                    _viewState.value = MixerViewState.Success
                }, {
                    _viewState.value =
                            MixerViewState.Failure(it)
                })
        )
    }

    fun loopSingleSound(index: Int, isLooping: Boolean){
        _viewState.value =
                MixerViewState.Loading
        disposables.add(
            setLoopingUseCase.execute(index, isLooping).subscribe({
                _viewState.value = MixerViewState.Success
            }, {
                _viewState.value =
                        MixerViewState.Failure(it)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
sealed class MixerViewState {
    object Loading: MixerViewState()
    object Success : MixerViewState()
    object PlaySoundScapeFinish: MixerViewState()
    data class PlaySoundFinish(val index: Int): MixerViewState()
    data class Failure(val throwable: Throwable) : MixerViewState()

    data class GetSoundScapesSuccess(val soundScapeItems: MutableList<SoundscapeItem>): MixerViewState()
    object RemoveSoundScapeSuccess : MixerViewState()

    data class GetOneLocalSoundScapeSuccess(val localSoundscape: LocalSoundscape): MixerViewState()

    //Update
    object UpdateSoundScapeSuccess : MixerViewState()

    //Save
    object SaveSoundScapeLoading : MixerViewState()
    object SaveSoundScapeSuccess : MixerViewState()
    data class SaveSoundScapeFailure(val throwable: Throwable) : MixerViewState()
}
package com.example.dinhh.soundscape.domain.user

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.repository.UserRepository
import io.reactivex.Completable

class LogoutUseCase(private val userRepository: UserRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Completable {
        return userRepository.clearLoginData()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }

}
package com.example.dinhh.soundscape.domain

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.repository.UserRepository
import io.reactivex.Single

class GetTokenUseCase(private val userRepository: UserRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(): Single<String> {
        return userRepository.getToken()
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }

}
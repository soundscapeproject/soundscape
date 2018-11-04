package com.example.dinhh.soundscape.domain

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.entity.Token
import com.example.dinhh.soundscape.data.repository.UserRepository
import io.reactivex.Single

class LoginUseCase(private val userRepository: UserRepository, private val schedulerProvider: SchedulerProvider) {

    fun execute(username: String, password: String): Single<Token> {
        return userRepository.login(username, password)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }

}
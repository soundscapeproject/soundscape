package com.example.dinhh.soundscape.domain.user

import com.example.dinhh.soundscape.common.SchedulerProvider
import com.example.dinhh.soundscape.data.repository.UserRepository
import io.reactivex.Single

class LoginUseCase(private val userRepository: UserRepository, private val schedulerProvider: SchedulerProvider) {

    /**
    Checks if the login credentials are correct. If they are, access to use the application is granted. If not, the application stays in the login screen.
     **/
    fun execute(username: String, password: String): Single<String> {
        return userRepository.login(username, password)
            .subscribeOn(schedulerProvider.getIOScheduler())
            .observeOn(schedulerProvider.getUIScheduler())
    }

}
package com.trx.habitmeta.wearos.data.repositories

import com.trx.habitmeta.wearos.models.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalRepository
@Inject
constructor() {
    private val user = MutableStateFlow<User?>(null)

    fun getUser() = user

    fun saveUser(user: User) {
        this.user.value = user
    }

    fun clearData() {
        user.value = null
    }
}

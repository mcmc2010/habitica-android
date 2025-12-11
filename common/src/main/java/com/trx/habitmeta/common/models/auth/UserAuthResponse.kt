package com.trx.habitmeta.common.models.auth

data class UserAuthResponse(
    val apiToken: String = "",
    val id:       String = "",
    val newUser:  Boolean = false
) {

    val token: String
        get() = apiToken
}


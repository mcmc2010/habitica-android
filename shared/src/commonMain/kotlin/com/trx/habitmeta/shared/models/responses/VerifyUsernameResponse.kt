package com.trx.habitmeta.shared.models.responses

class VerifyUsernameResponse {
    var isUsable: Boolean = false
    var issues = emptyList<String>()
}

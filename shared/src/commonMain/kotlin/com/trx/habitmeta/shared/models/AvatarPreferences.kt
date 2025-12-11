package com.trx.habitmeta.shared.models

interface AvatarPreferences {
    val hair: AvatarHair?
    val costume: Boolean
    val sleep: Boolean
    val shirt: String?
    val skin: String?
    val size: String?
    val background: String?
    val chair: String?
    val disableClasses: Boolean
}

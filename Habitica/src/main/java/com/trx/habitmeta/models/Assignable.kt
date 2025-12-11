package com.trx.habitmeta.models

import com.trx.habitmeta.shared.models.Avatar

interface Assignable {
    val id: String?
    val avatar: Avatar?
    val identifiableName: String
}

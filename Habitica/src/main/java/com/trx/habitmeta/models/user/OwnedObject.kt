package com.trx.habitmeta.models.user

import com.trx.habitmeta.models.BaseObject

interface OwnedObject : BaseObject {
    var userID: String?
    var key: String?
}

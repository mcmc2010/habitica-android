package com.trx.habitmeta.models.social

import com.trx.habitmeta.models.BaseObject
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class ChatMessageLike(var id: String = "") : RealmObject(), BaseObject

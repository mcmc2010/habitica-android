package com.trx.habitmeta.models.tasks

import com.trx.habitmeta.models.BaseObject
import com.trx.habitmeta.models.Tag
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class TaskTag : RealmObject(), BaseObject {
    var tag: Tag? = null
    var task: Task? = null
}

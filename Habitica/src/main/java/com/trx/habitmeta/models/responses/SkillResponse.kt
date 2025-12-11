package com.trx.habitmeta.models.responses

import com.trx.habitmeta.models.tasks.Task
import com.trx.habitmeta.models.user.User

class SkillResponse {
    var user: User? = null
    var task: Task? = null
    var expDiff = 0.0
    var hpDiff = 0.0
    var goldDiff = 0.0
    var damage = 0.0f
}

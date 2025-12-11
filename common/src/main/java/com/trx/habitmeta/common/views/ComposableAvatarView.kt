package com.trx.habitmeta.common.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.trx.habitmeta.common.helpers.AppConfigManager
import com.trx.habitmeta.shared.models.Avatar

@Composable
fun ComposableAvatarView(
    avatar: Avatar?,
    configManager: AppConfigManager?,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier, // Occupy the max size in the Compose UI tree
        factory = { context ->
            val view = AvatarView(context)
            view.configManager = configManager
            return@AndroidView view
        },
        update = { view ->
            if (avatar != null) {
                view.setAvatar(avatar)
            }
        }
    )
}

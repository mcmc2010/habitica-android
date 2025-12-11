package com.trx.habitmeta.data.implementation

import android.content.Context
import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.ContentRepository
import com.trx.habitmeta.data.local.ContentLocalRepository
import com.trx.habitmeta.helpers.AprilFoolsHandler
import com.trx.habitmeta.models.ContentResult
import com.trx.habitmeta.models.WorldState
import com.trx.habitmeta.models.inventory.SpecialItem
import com.trx.habitmeta.modules.AuthenticationHandler
import io.realm.RealmList
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ContentRepositoryImpl<T : ContentLocalRepository>(
    localRepository: T,
    apiClient: ApiClient,
    context: Context,
    authenticationHandler: AuthenticationHandler
) : BaseRepositoryImpl<T>(localRepository, apiClient, authenticationHandler), ContentRepository {
    private val mysteryItem = SpecialItem.makeMysteryItem(context)

    private var lastContentSync = 0L
    private var lastWorldStateSync = 0L

    override suspend fun retrieveContent(forced: Boolean): ContentResult? {
        val now = Date().time
        if (forced || now - this.lastContentSync > 300000) {
            val content = apiClient.getContent() ?: return null
            lastContentSync = now
            content.special = RealmList()
            content.special.add(mysteryItem)
            localRepository.saveContent(content)
            return content
        }
        return null
    }

    override suspend fun retrieveWorldState(forced: Boolean): WorldState? {
        val now = Date().time
        if (forced || now - this.lastWorldStateSync > 300000) {
            val state = apiClient.getWorldState() ?: return null
            lastWorldStateSync = now
            localRepository.save(state)
            for (event in state.events) {
                if (event.aprilFools != null && event.isCurrentlyActive) {
                    AprilFoolsHandler.handle(event.aprilFools, event.end)
                }
            }
            return state
        }
        return null
    }

    override fun getWorldState(): Flow<WorldState> {
        return localRepository.getWorldState()
    }
}

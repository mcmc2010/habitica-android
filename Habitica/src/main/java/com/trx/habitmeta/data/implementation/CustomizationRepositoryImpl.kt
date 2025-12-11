package com.trx.habitmeta.data.implementation

import com.trx.habitmeta.data.ApiClient
import com.trx.habitmeta.data.CustomizationRepository
import com.trx.habitmeta.data.local.CustomizationLocalRepository
import com.trx.habitmeta.models.inventory.Customization
import com.trx.habitmeta.modules.AuthenticationHandler
import kotlinx.coroutines.flow.Flow

class CustomizationRepositoryImpl(
    localRepository: CustomizationLocalRepository,
    apiClient: ApiClient,
    authenticationHandler: AuthenticationHandler
) : BaseRepositoryImpl<CustomizationLocalRepository>(
    localRepository,
    apiClient,
    authenticationHandler
),
    CustomizationRepository {
    override fun getCustomizations(
        type: String,
        category: String?,
        onlyAvailable: Boolean
    ): Flow<List<Customization>> {
        return localRepository.getCustomizations(type, category, onlyAvailable)
    }
}

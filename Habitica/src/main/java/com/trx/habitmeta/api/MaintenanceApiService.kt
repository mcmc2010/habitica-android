package com.trx.habitmeta.api

import com.trx.habitmeta.shared.models.responses.MaintenanceResponse
import retrofit2.http.GET

interface MaintenanceApiService {
    @GET("maintenance-android.json")
    suspend fun getMaintenanceStatus(): MaintenanceResponse?

    @GET("deprecation-android.json")
    suspend fun getDepricationStatus(): MaintenanceResponse?
}

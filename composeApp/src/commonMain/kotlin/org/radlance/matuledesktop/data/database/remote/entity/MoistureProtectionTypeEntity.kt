package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class MoistureProtectionTypeEntity(
    val id: Int,
    val name: String
)
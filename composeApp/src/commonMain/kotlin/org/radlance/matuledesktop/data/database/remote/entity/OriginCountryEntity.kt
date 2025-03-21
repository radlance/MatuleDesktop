package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class OriginCountryEntity(
    val id: Int,
    val name: String
)
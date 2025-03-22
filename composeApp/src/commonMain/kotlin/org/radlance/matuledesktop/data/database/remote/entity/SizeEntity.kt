package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class SizeEntity(
    val id: Int,
    val number: Int
)

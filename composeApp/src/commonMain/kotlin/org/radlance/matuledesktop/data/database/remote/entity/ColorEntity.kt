package org.radlance.matuledesktop.data.database.remote.entity

import kotlinx.serialization.Serializable

@Serializable
data class ColorEntity(
    val id: Int,
    val name: String,
    val red: Int,
    val green: Int,
    val blue: Int
)

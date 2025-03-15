package org.radlance.matuledesktop.domain.auth

data class User(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = ""
)

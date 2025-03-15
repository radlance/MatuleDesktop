package org.radlance.matuledesktop.presentation.auth.common

interface Validate {

    fun validEmail(value: String): Boolean

    fun validPassword(value: String): Boolean

    fun validName(value: String): Boolean

    class Base : Validate {

        override fun validEmail(value: String): Boolean = Regex(
            "^[a-z0-9]+@[a-z0-9]+(-[a-z0-9]+)*(\\.[a-z0-9]+(-[a-z0-9]+)*)*\\.[a-z]{2,}$"
        ).matches(value)

        override fun validPassword(value: String): Boolean = value.length >= 6

        override fun validName(value: String): Boolean = value.isNotBlank()

    }
}
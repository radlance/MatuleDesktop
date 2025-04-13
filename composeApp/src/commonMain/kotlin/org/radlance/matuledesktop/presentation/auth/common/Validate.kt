package org.radlance.matuledesktop.presentation.auth.common

interface Validate {

    fun validEmail(value: String): String

    fun validPassword(value: String): String

    fun validName(value: String): String

    class Base : Validate {

        override fun validEmail(value: String): String {
            val validEmail = Regex(
                "^[a-z0-9]+@[a-z0-9]+(-[a-z0-9]+)*(\\.[a-z0-9]+(-[a-z0-9]+)*)*\\.[a-z]{2,}$"
            ).matches(value)

            return if (validEmail) "" else "Неправильный формат email"
        }

        override fun validPassword(value: String): String {
            return if (value.length >= 6) "" else "Минимальная длина пароля — 6 символов"
        }

        override fun validName(value: String): String {
            return if (value.isNotBlank()) "" else "Имя не должно быть пустым"
        }
    }
}
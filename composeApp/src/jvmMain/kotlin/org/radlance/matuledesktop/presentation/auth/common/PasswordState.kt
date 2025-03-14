package org.radlance.matuledesktop.auth.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.hide_password
import matuledesktop.composeapp.generated.resources.show_password
import org.jetbrains.compose.resources.StringResource

interface PasswordState {

    fun icon(): ImageVector

    fun inverse(): PasswordState

    fun contentDescriptionId(): StringResource

    fun visualTransformation(): VisualTransformation

    object Visible : PasswordState {

        override fun icon(): ImageVector = Icons.Default.Visibility

        override fun inverse(): PasswordState = Invisible

        override fun contentDescriptionId(): StringResource = Res.string.hide_password

        override fun visualTransformation(): VisualTransformation = VisualTransformation.None
    }

    object Invisible : PasswordState {

        override fun icon(): ImageVector = Icons.Default.VisibilityOff

        override fun inverse(): PasswordState = Visible

        override fun contentDescriptionId(): StringResource = Res.string.show_password

        override fun visualTransformation(): VisualTransformation = PasswordVisualTransformation()
    }
}
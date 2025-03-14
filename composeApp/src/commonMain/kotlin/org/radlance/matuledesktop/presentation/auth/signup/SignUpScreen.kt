package org.radlance.matuledesktop.presentation.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(
    navigateToSignInScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var nameFieldValue by remember { mutableStateOf("") }
        var emailFieldValue by remember { mutableStateOf("") }
        var passwordFieldValue by remember { mutableStateOf("") }

        Text(
            text = "Регистрация",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 11.dp)
        )

        Text(
            text = "Заполните свои данные",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nameFieldValue,
            onValueChange = { nameFieldValue = it },
            label = { Text(text = "Ваше имя") },
            placeholder = { Text(text = "xxxxxxxx") },
            singleLine = true,
            modifier = Modifier.width(300.dp)
        )

        OutlinedTextField(
            value = emailFieldValue,
            onValueChange = { emailFieldValue = it },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "xyz@gmail.com") },
            singleLine = true,
            modifier = Modifier.width(300.dp)
        )

        OutlinedTextField(
            value = passwordFieldValue,
            onValueChange = { passwordFieldValue = it },
            singleLine = true,
            label = { Text(text = "Пароль") },
            placeholder = { Text(text = "•••••••") },
            modifier = Modifier.width(300.dp),
            visualTransformation = PasswordVisualTransformation()
            )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {}) {
            Text(text = "Зарегистрироваться")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Войти",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 1.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { navigateToSignInScreen() }
        )
    }
}
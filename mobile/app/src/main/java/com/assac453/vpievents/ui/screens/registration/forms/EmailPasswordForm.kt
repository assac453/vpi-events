package com.assac453.vpievents.ui.screens.registration.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.assac453.vpievents.logic.utils.isValidEmail


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailPasswordForm(
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    isEmailValid: Boolean,
    onIsEmailValidChanged: (Boolean) -> Unit,
    isConfirmPasswordValid: Boolean,
    onIsConfirmPasswordValidChanged: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )



        OutlinedTextField(
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {onEmailChanged(it); onIsEmailValidChanged(isValidEmail(it))},
            label = { Text("Электронная почта") },
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotEmpty() && !isValidEmail(email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {onPasswordChanged(it); },
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {onConfirmPasswordChanged(it);  onIsConfirmPasswordValidChanged(it == password)},
            label = { Text("Подтвердите пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPassword.isNotEmpty() && password != confirmPassword
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isEmailValid && isConfirmPasswordValid) {
                    onNextClicked()
                }

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isEmailValid && isConfirmPasswordValid

        ) {
            Text("Далее")
        }
    }
}
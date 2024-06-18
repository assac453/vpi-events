package com.assac453.vpievents.ui.screens.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.assac453.vpievents.MainActivity
import com.assac453.vpievents.logic.utils.isValidEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizeEntryForm(
    navController: NavHostController,
    activity: ComponentActivity,
    auth: FirebaseAuth
) {
    var email by rememberSaveable { mutableStateOf("email@mail.ru") }
    var password by rememberSaveable { mutableStateOf("password") }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(text = "Войдите в профиль", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { text ->
                    email = text
                },
                label = { Text("Электронная почта") },
                modifier = Modifier.fillMaxWidth(),
                isError = email.isNotEmpty() && !isValidEmail(email)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { onLoginClicked(email, password, navController, activity, auth) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Войти")
            }
        }
        println(innerPadding)
    }
}

fun onLoginClicked(
    email: String,
    password: String,
    navController: NavController,
    activity: ComponentActivity,
    auth: FirebaseAuth
) {

    val signInWithEmailAndPassword = auth.signInWithEmailAndPassword(email, password)
    signInWithEmailAndPassword.addOnCompleteListener {
        if (it.isSuccessful) {
            Toast.makeText(navController.context, "Успешный вход", Toast.LENGTH_SHORT).show()
            val intent = Intent(navController.context, MainActivity::class.java)
            navController.context.startActivity(intent)
            activity.finish()
        } else {
            when (val exception = it.exception) {
                is FirebaseAuthInvalidUserException -> {
                    Toast.makeText(
                        navController.context,
                        "Пользователь с такой почтой не зарегистрирован",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is FirebaseAuthEmailException -> {
                    Toast.makeText(
                        navController.context,
                        "Неправильная почта или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    Toast.makeText(
                        navController.context,
                        "Неправильная почта или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is FirebaseAuthUserCollisionException -> {
                    Toast.makeText(
                        navController.context,
                        "Почта занята",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                else -> {
                    Toast.makeText(
                        navController.context,
                        "Ошибка входа: ${exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    exception?.message?.let { it1 -> Log.e("AUTH", it1) }
                }
            }
        }
    }
    signInWithEmailAndPassword.addOnSuccessListener { task ->
        Toast.makeText(navController.context, "Успешный вход", Toast.LENGTH_SHORT).show()
        val intent = Intent(navController.context, MainActivity::class.java)
        navController.context.startActivity(intent)
        activity.finish()

    }
}

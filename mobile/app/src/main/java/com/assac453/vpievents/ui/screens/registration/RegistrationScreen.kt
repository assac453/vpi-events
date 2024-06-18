package com.assac453.vpievents.ui.screens.registration

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.assac453.vpievents.MainActivity
import com.assac453.vpievents.network.model.UserNetwork
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.screens.registration.forms.AdditionalInfoForm
import com.assac453.vpievents.ui.screens.registration.forms.EmailPasswordForm
import com.assac453.vpievents.ui.screens.registration.forms.PersonalInfoForm
import com.assac453.vpievents.ui.screens.registration.forms.SchoolInfoForm
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    activity: ComponentActivity,
    auth: FirebaseAuth
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var fullName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var birthDate by rememberSaveable { mutableStateOf("") }
    var school by rememberSaveable { mutableStateOf("") }
    var primarySchool by rememberSaveable { mutableStateOf("") }
    var grade by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }

    var currentPage by remember { mutableStateOf(0) }

    var isEmailValid by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordValid by rememberSaveable { mutableStateOf(false) }

    var isFullNameValid by rememberSaveable { mutableStateOf(false) }
    var isPhoneNumberValid by rememberSaveable { mutableStateOf(false) }

    // Новые переменные и состояния для дополнительной информации
    var telegramLink by rememberSaveable { mutableStateOf("") }
    var vkLink by rememberSaveable { mutableStateOf("") }
    var hasDisability by rememberSaveable { mutableStateOf(false) }
    var isOrphan by rememberSaveable { mutableStateOf(false) }
    var registrationAddress by rememberSaveable { mutableStateOf("") }
    var residentialAddress by rememberSaveable { mutableStateOf("") }


    // Функции для обновления новых полей
    var onTelegramLinkChanged: (String) -> Unit = { text -> telegramLink = text }
    var onVkLinkChanged: (String) -> Unit = { text -> vkLink = text }
    var onHasDisabilityChanged: (Boolean) -> Unit = { value -> hasDisability = value }
    var onIsOrphanChanged: (Boolean) -> Unit = { value -> isOrphan = value }
    var onRegistrationAddressChanged: (String) -> Unit = { text -> registrationAddress = text }
    var onResidentialAddressChanged: (String) -> Unit = { text -> residentialAddress = text }


    Scaffold { innerPadding ->
        println(innerPadding)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (currentPage) {
                0 -> {
                    EmailPasswordForm(
                        email = email,
                        onEmailChanged = { text -> email = text },
                        password = password,
                        onPasswordChanged = { text -> password = text },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChanged = { text -> confirmPassword = text },
                        onNextClicked = { currentPage++ },
                        isEmailValid = isEmailValid,
                        isConfirmPasswordValid = isConfirmPasswordValid,
                        onIsEmailValidChanged = { text -> isEmailValid = text },
                        onIsConfirmPasswordValidChanged = { text -> isConfirmPasswordValid = text }
                    )
                }

                1 -> {
                    PersonalInfoForm(
                        fullName = fullName,
                        onFullNameChanged = { text -> fullName = text },
                        phoneNumber = phoneNumber,
                        onPhoneNumberChanged = { text -> phoneNumber = text },
                        birthDate = birthDate,
                        onBirthDateChanged = { text -> birthDate = text },
                        onBackClicked = { currentPage-- },
                        onNextClicked = { currentPage++ },
                        isFullNameValid = isFullNameValid,
                        isPhoneNumberValid = isPhoneNumberValid,
                        onIsFullNameValidChanged = { text -> isFullNameValid = text },
                        onIsPhoneNumberValidChanged = { text -> isPhoneNumberValid = text },
                        onGenderChanged = {text -> gender = text},
                    )
                }

                2 -> {
                    SchoolInfoForm(
                        school = school,
                        onSchoolChanged = { text -> school = text },
                        grade = grade,
                        onGradeChanged = { text -> grade = text },
                        trainingClass = direction,
                        onTrainingClassChanged = { text -> direction = text },
                        onBackClicked = { currentPage-- },
                        onNextClicked = { currentPage++ },
                        onPrimarySchoolChanged = { text -> primarySchool = text }
                    )
                }

                3 -> {
                    AdditionalInfoForm(
                        telegramLink = telegramLink,
                        onTelegramLinkChanged = onTelegramLinkChanged,
                        vkLink = vkLink,
                        onVkLinkChanged = onVkLinkChanged,
                        hasDisability = hasDisability,
                        onHasDisabilityChanged = onHasDisabilityChanged,
                        isOrphan = isOrphan,
                        onIsOrphanChanged = onIsOrphanChanged,
                        registrationAddress = registrationAddress,
                        onRegistrationAddressChanged = onRegistrationAddressChanged,
                        residentialAddress = residentialAddress,
                        onResidentialAddressChanged = onResidentialAddressChanged,
                        onBackClicked = { currentPage-- },
                        onNextClicked = {
                            onRegistrationSuccess(navController, activity, auth, email, password)
                            /*AppNavigation.UserContainer.user = UserNetwork(
                                email = email,
                                password = password,
                                points = 0,
                                registrationDate = LocalDateTime.now().toString(),
                                personalInfo = PersonalInfoNetwork(
                                    birthDate = birthDate,
                                    lastName = fullName.split(" ")[0],
                                    firstName = fullName.split(" ")[1],
                                    patronymic = if (fullName.split(" ").size > 2) "" else "",
                                    gender = gender,
                                ),
                                additionalInfo = AdditionalInfoNetwork(

                                ),
                                contactInfo = ContactInfoNetwork(
                                    phoneNumber = phoneNumber
                                ),
                                schoolInfo = SchoolInfoNetwork(
                                    school = SchoolNetwork(name = if (school == "Моей школы нет в списке") primarySchool else school)
                                ),
                            )*/
                        },
                    )
                }
            }
        }
    }
}



fun onRegistrationSuccess(
    navController: NavHostController,
    activity: ComponentActivity,
    auth: FirebaseAuth,
    email: String,
    password: String
) {
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(navController.context, "Успешная регистрация", Toast.LENGTH_SHORT).show()
            navController.navigate(AppNavigation.QRCODE_ROUTE);

            AppNavigation.UserContainer.user = UserNetwork(
                FirebaseAuth.getInstance().currentUser?.uid,
            )

            val intent = Intent(navController.context, MainActivity::class.java)
            navController.context.startActivity(intent)

            activity.finish()
        } else {
            Toast.makeText(navController.context, "Неуспешная регистрация", Toast.LENGTH_SHORT)
                .show()
            Toast.makeText(navController.context, "Почта уже занята", Toast.LENGTH_SHORT)
                .show()
//            Toast.makeText(navController.context, task.exception?.message, Toast.LENGTH_SHORT)
//                .show()
        }
    }


}





package com.assac453.vpievents.ui.screens.registration.forms

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.assac453.vpievents.logic.utils.DateUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoForm(
    fullName: String,
    onFullNameChanged: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    birthDate: String,
    onBirthDateChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    isFullNameValid: Boolean,
    isPhoneNumberValid: Boolean,
    onIsFullNameValidChanged: (Boolean) -> Unit,
    onIsPhoneNumberValidChanged: (Boolean) -> Unit,
    onGenderChanged: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        mutableStateOf("Мужской")
    }
    var showDialog by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Личная информация",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        OutlinedTextField(
            value = fullName,
            onValueChange = { onFullNameChanged(it); onIsFullNameValidChanged(isFullNameValid((it))) },
            label = { Text("ФИО") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isFullNameValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                onPhoneNumberChanged(it);
                onIsPhoneNumberValidChanged(isPhoneNumberValid(it))
            },
            label = { Text("Номер телефона") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPhoneNumberValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Пол:")
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.padding(4.dp)
            ) {
                DropdownMenuItem(onClick = {
                    selectedOption = "Мужской"
                    expanded = false
                    onGenderChanged(selectedOption)
                }, text = { Text("Мужской") })
                DropdownMenuItem(onClick = {
                    selectedOption = "Женский"
                    expanded = false
                    onGenderChanged(selectedOption)
                }, text = { Text("Женский") })
            }

            Box(
                modifier = Modifier.fillMaxWidth()
//                    .size(80.dp)
                    .clickable { expanded = true }
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedOption,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        OutlinedTextField(
            value = birthDate,//birthDate,
            onValueChange = {},  // Disable editing
            label = { Text("Дата рождения") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialog = true },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = "Select Date",
                    modifier = Modifier.clickable { showDialog = true }
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackClicked) {
                Text("Назад")
            }
            Button(
                onClick = {
                    if (isFullNameValid && isPhoneNumberValid) {
                        onNextClicked()
                    }
                },
                enabled = isFullNameValid && isPhoneNumberValid
            ) {
                Text("Далее")
            }
        }

        // DatePicker Dialog
        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false;
                            onBirthDateChanged(dateStateToLocalDate(datePickerState))
                        }
                    ) {
                        Text(text = "ОК")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "Отмена")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = true
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun dateStateToLocalDate(dateState: DatePickerState): String {
    val millisToLocalDate = dateState.selectedDateMillis?.let {
        DateUtils().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateUtils().dateToString(millisToLocalDate)
    } ?: "Choose Date"
    return dateToString
}


fun isFullNameValid(fullName: String): Boolean {
    return fullName.matches(Regex("^([а-яА-ЯёЁ]+)\\s+([а-яА-ЯёЁ]+)(\\s+([а-яА-ЯёЁ]+))?\$"))
}

fun isPhoneNumberValid(phoneNumber: String): Boolean {
    return phoneNumber.matches(Regex("^(\\+7|8)[0-9]{10}\$"))
}

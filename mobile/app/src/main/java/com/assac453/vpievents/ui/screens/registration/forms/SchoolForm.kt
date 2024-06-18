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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolInfoForm(
    school: String,
    onSchoolChanged: (String) -> Unit,
    onPrimarySchoolChanged: (String) -> Unit,
    grade: String,
    onGradeChanged: (String) -> Unit,
    trainingClass: String,
    onTrainingClassChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    var expandedSchool by remember { mutableStateOf(false) }
    var selectedIndexSchool by remember { mutableStateOf(0) }
    var selectedOptionSchool by remember { mutableStateOf("") }
    val schools = listOf("Моей школы нет в списке", "Школа 1", "Школа 2", "Школа 3")

    var expandedDirection by remember { mutableStateOf(false) }
    var selectedIndexDirection by remember { mutableStateOf(0) }
    var selectedOptionDirection by remember { mutableStateOf("") }
    val directions = listOf("Нет предпочтений", "Программная инженерия", "Информатика и вычислительная техника")


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Информация о школе",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )



        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Школа:")
            Spacer(modifier = Modifier.width(16.dp))
            DropdownMenu(
                expanded = expandedSchool,
                onDismissRequest = { expandedSchool = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                schools.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        selectedIndexSchool = index
                        onPrimarySchoolChanged(item)
                        selectedOptionSchool = item
                        expandedSchool = false
                    }, text = {Text(text = item)})
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .clickable { expandedSchool = true }
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedOptionSchool,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }


        if (selectedOptionSchool == "Моей школы нет в списке"){
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = school,
                onValueChange = {onSchoolChanged(it)},
                label = { Text("Школа") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

        }


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = trainingClass,
            onValueChange = {onTrainingClassChanged(it)},
            label = { Text("Класс обучения") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Направление обучения в институте:")

            Spacer(modifier = Modifier.height(16.dp))
            DropdownMenu(
                expanded = expandedDirection,
                onDismissRequest = { expandedDirection = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                directions.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        selectedIndexDirection = index
                        onGradeChanged(item)
                        selectedOptionDirection = item
                        expandedDirection = false
                    }, text = {Text(text = item)})
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .clickable { expandedDirection = true }
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedOptionDirection,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBackClicked) {
                Text("Back")
            }
            Button(onClick = onNextClicked) {
                Text("Next")
            }
        }
    }
}

package com.assac453.vpievents.ui.screens.main.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.assac453.vpievents.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize().padding(it), contentAlignment = Alignment.Center){
            Image(alignment = Alignment.Center,
                modifier = Modifier.size(200.dp).fillMaxSize(),
                painter = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(R.string.loading))
        }
    }

}
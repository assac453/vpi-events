package com.assac453.vpievents

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.theme.VPIEventsTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : ComponentActivity() {

    private var textResult: MutableState<String> = mutableStateOf("")
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null){
            Toast.makeText(this, currentUser.uid, Toast.LENGTH_SHORT).show()
            Log.d("UID", currentUser.uid)
            userLoggerIn()
        }
    }

    private val requestPermissionLauncherInternet = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        askInternetPermission(this)
        setContent {
            VPIEventsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        content = {padding ->
                            AppNavigation.createNavGraph(
                                navController = navController,
                                context = this,
                                checkCameraPermission = {},
                                textResult = textResult,
                                activity = this,
                                startDestination = AppNavigation.MAIN_LOGIN_ROUTE,
                                auth,
                                {}
                            )
                            println(padding)
                        }
                    )
                }
            }
        }
    }
    private fun askInternetPermission(context: Context){

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED){

        }
        else if(shouldShowRequestPermissionRationale(android.Manifest.permission.INTERNET)){
            Toast.makeText(this, "Internet permission required", Toast.LENGTH_SHORT).show()
        }
        else {
            requestPermissionLauncherInternet.launch(android.Manifest.permission.INTERNET)
        }
    }

    private fun userLoggerIn(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}

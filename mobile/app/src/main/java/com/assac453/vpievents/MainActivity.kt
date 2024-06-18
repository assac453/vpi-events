package com.assac453.vpievents

import android.Manifest
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.assac453.vpievents.data.DefaultAppContainer
import com.assac453.vpievents.data.getBottomItems
import com.assac453.vpievents.logic.notification.InternalNotificationService
import com.assac453.vpievents.logic.notification.NotificationService
import com.assac453.vpievents.network.service.ErrorResponse
import com.assac453.vpievents.network.service.EventRegistrationRequest
import com.assac453.vpievents.network.service.RegisterOnEventService
import com.assac453.vpievents.ui.graph.AppNavigation
import com.assac453.vpievents.ui.theme.VPIEventsTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.UUID


class MainActivity : ComponentActivity() {

    private var textResult: MutableState<String> = mutableStateOf("")
    private val notificationService = NotificationService()

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val appContainer = DefaultAppContainer()
    private val apiService: RegisterOnEventService = appContainer.retrofitServiceRegisterOnEventService


    private lateinit var internalNotificationService: InternalNotificationService
    private fun showCamera() {
        val options = ScanOptions()
        options.setPrompt("Сканируйте QR-code")
        options.setBeepEnabled(false)
        scanLauncher.launch(options)
    }


    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            val eventId = result.contents
            getLastLocation(fusedLocationClient) { location ->
                val userId = AppNavigation.User.user?.id
                if (userId != null && location != null) {
                    val request = EventRegistrationRequest(
                        user_id = userId,
                        event_id = eventId,
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                    sendPostRequest(request)
                } else {
                    Toast.makeText(this, "Error: User ID or Location is null", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private val requestPermissionLauncherCamera =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showCamera()
            }
        }
    private val requestPermissionLauncherNotification = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private val requestPermissionLauncherGPS =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }


    private fun getLastLocation(fusedLocationClient: FusedLocationProviderClient, callback: (Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "GPS required", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                callback(location)
            } else {
                callback(null)
            }
        }
    }
    private fun sendPostRequest(request: EventRegistrationRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.registerEvent(request)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.success != null){
                            showAlertDialog("Успех", body.success.toString())
                        }
                    } else {
                        val errorBody = response.errorBody()?.bytes()?.toString(Charsets.UTF_8)
                        var errorMessage = "Не удалось зарегистрироваться на мероприятие"
                        errorBody?.let {
                            try {
                                val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                                errorMessage = errorResponse.error
                            } catch (e: JsonSyntaxException) {
                                // Ошибка парсинга JSON
                                Log.e("JSON_PARSE_ERROR", "Ошибка парсинга JSON: ${e.message}")
                            }
                        }
                        Log.e("ERROR_TO_REGISTER", errorMessage)
                        showAlertDialog("Ошибка", errorMessage)
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    val errorMessage = "Ошибка: ${e.message()}"
                    Log.e("ERROR_TO_REGISTER", errorMessage)
                    showAlertDialog("Ошибка", errorMessage)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val errorMessage = "Ошибка: ${e.message}"
                    Log.e("ERROR_TO_REGISTER", errorMessage)
                    showAlertDialog("Ошибка", errorMessage)
                }
            }
        }
    }
    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        internalNotificationService = InternalNotificationService.getInstance(this)
        askNotificationPermission(this)
        askGpsPermission(this)
        getRegisterToken(this)
        showNotification()
        setContent {
            VPIEventsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val bottomItems = getBottomItems()
                    var selectedScreen by remember { mutableStateOf(bottomItems[1]) }
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar =
                        {
                            BottomAppBar {
                                bottomItems.forEach { screen ->
                                    NavigationBarItem(
                                        selected = selectedScreen == screen,
                                        label = { Text(screen.showName) },
                                        onClick = {
                                            selectedScreen = screen
                                            navController.navigate(screen.name)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon,
                                                contentDescription = "icon"
                                            )
                                        })
                                }
                            }
                        },
                        content = { padding ->
                            Box(modifier = Modifier.padding(padding)) {
                                AppNavigation.createNavGraph(
                                    navController = navController,
                                    context = this@MainActivity,
                                    checkCameraPermission = { checkCameraPermission(this@MainActivity) },
                                    textResult = textResult,
                                    activity = this@MainActivity,
                                    startDestination = AppNavigation.QRCODE_ROUTE,
                                    auth = FirebaseAuth.getInstance(),
                                    onOrderClicked = { order() }

                                )
                            }
                            println(padding)
                        }
                    )
                }
            }
        }
    }


    private fun getLastLocation(fusedLocationClient: FusedLocationProviderClient) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "GPS required", Toast.LENGTH_SHORT).show()
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                Toast.makeText(this, "GPS is successfully", Toast.LENGTH_SHORT).show()
                Log.d("GPS", "GPS: ${location.latitude}, ${location.longitude}")
            } else if (task.isCanceled) {
                Toast.makeText(this, "GPS required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun order() {

        internalNotificationService.showNotification(
            title = "Заказ осуществлён",
            message = "Заявка на заказ создана"
        )

        Toast.makeText(this, "Заказ осуществен", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "TODO прислать оповещение о создании заявки", Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkCameraPermission(context: Context) {
        getLastLocation(fusedLocationClient)
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Camera required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncherCamera.launch(android.Manifest.permission.CAMERA)
        }
    }


    private fun askGpsPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else if (
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
            ||
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            Toast.makeText(this, "GPS permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncherGPS.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissionLauncherGPS.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askNotificationPermission(context: Context) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
            Toast.makeText(this, "Notification permission required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncherNotification.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    fun showNotification() {
        var channel: NotificationChannel = NotificationChannel(
            getString(R.string.REGISTRATION_EVENT),
            getString(R.string.REGISTRATION_EVENT),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        var notification: Notification = NotificationCompat.Builder(this, "TEST_CHANNEL")
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        notificationManager.notify(42, notification)
    }
}


fun getRegisterToken(context: Context) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FCM", "Fetching FCM token is failed", task.exception)
        }
        val token = task.result
        sendTokenToFirebase(token)
        Log.d("FCM", token)
        Toast.makeText(context, token, Toast.LENGTH_SHORT).show()

    }
}


fun sendTokenToFirebase(token: String) {

    // Получение ссылки на базу данных
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users")
    val curUser = FirebaseAuth.getInstance().currentUser
    val userRef = reference.child(UUID.nameUUIDFromBytes(curUser!!.uid.toByteArray()).toString())
    val users = hashMapOf<String, String?>(
        "notification_token" to token
    )
    val userId = UUID.nameUUIDFromBytes(curUser!!.uid.toByteArray()).toString()
    AppNavigation.User.user?.id = userId
    Log.e("USERID", AppNavigation.User.user?.id.toString())
    userRef.setValue(users).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Firebase", "success send token")
        } else if (!task.isSuccessful) {
            Log.d("Firebase", "failure send token")
        }
    }
}
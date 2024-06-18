package com.assac453.vpievents.ui.graph

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.assac453.vpievents.data.entity.Event
import com.assac453.vpievents.data.entity.Notification
import com.assac453.vpievents.data.entity.Reward
import com.assac453.vpievents.network.model.UserNetwork
import com.assac453.vpievents.ui.screens.login.AuthorizeEntryForm
import com.assac453.vpievents.ui.screens.login.MainEntryForm
import com.assac453.vpievents.ui.screens.main.CalendarScreen
import com.assac453.vpievents.ui.screens.main.FullEventScreen
import com.assac453.vpievents.ui.screens.main.GeolocationScreen
import com.assac453.vpievents.ui.screens.main.QRCodeScreen
import com.assac453.vpievents.ui.screens.main.helper.ErrorScreen
import com.assac453.vpievents.ui.screens.main.helper.LoadingScreen
import com.assac453.vpievents.ui.screens.profile.MainProfileScreen
import com.assac453.vpievents.ui.screens.profile.notifications.NotificationFullScreen
import com.assac453.vpievents.ui.screens.profile.notifications.NotificationScreen
import com.assac453.vpievents.ui.screens.registration.RegistrationScreen
import com.assac453.vpievents.ui.screens.shop.FullRewardScreen
import com.assac453.vpievents.ui.screens.shop.ShopGridScreen
import com.google.firebase.auth.FirebaseAuth

object AppNavigation {
    const val MAIN_LOGIN_ROUTE = "Main"
    const val AUTH_ROUTE = "Authorize"
    const val REGISTER_MAIN_ROUTE = "RegistrationMain"
    const val REGISTER_PERSONAL_INFO_ROUTE = "RegistrationPersonalInfo"
    const val REGISTER_SCHOOL_ROUTE = "RegistrationSchoolInfo"

    const val ERROR_SCREEN = "ErrorScreen"
    const val LOADING_SCREEN = "LoadingScreen"

    const val PROFILE_ROUTE = "Profile"
    const val SHOP_ROUTE = "ShopMain"

    const val EVENT_DESCRIPTION = "EventDescription"
    const val REWARD_DESCRIPTION = "RewardDescription"
    const val NOTIFICATION_DESCRIPTION = "NotificationDescription"

    const val GEOPOSITION_ROUTE = "Geo"
    const val QRCODE_ROUTE = "QR-code"
    const val CALENDAR_ROUTE = "Calendar"

    const val NOTIFICATION_ROUTE = "Notification"

    object EventContainer {
        var selectedEvent: Event? by mutableStateOf(null)
    }

    object RewardContainer {
        var selectedReward: Reward? by mutableStateOf(null)
    }

    object UserContainer{
        var user: UserNetwork? by mutableStateOf(null)
    }

    object User {
        var user: com.assac453.vpievents.data.entity.User? = com.assac453.vpievents.data.entity.getEmptyUser()
    }

    object NotificationContainer{
        var selectedNotification: Notification? by mutableStateOf(null)
    }

    @Composable
    fun createNavGraph(
        navController: NavHostController,
        context: Context,
        checkCameraPermission: (Context) -> Unit,
        textResult: MutableState<String>,
        activity: ComponentActivity,
        startDestination: String,
        auth: FirebaseAuth,
        onOrderClicked: () -> Unit
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable(ERROR_SCREEN) { ErrorScreen(retryAction = {}) }
            composable(LOADING_SCREEN) { LoadingScreen() }


            composable(MAIN_LOGIN_ROUTE) { MainEntryForm(navController = navController) }
            composable(AUTH_ROUTE) {
                AuthorizeEntryForm(
                    navController = navController,
                    activity = activity,
                    auth
                )
            }
            composable(REGISTER_MAIN_ROUTE) {
                RegistrationScreen(
                    navController = navController,
                    activity,
                    auth
                )
            }
            composable(PROFILE_ROUTE) {
                MainProfileScreen(context, navController, activity)
            }
            composable(SHOP_ROUTE) {
                ShopGridScreen(navController)
            }
            composable(GEOPOSITION_ROUTE) { GeolocationScreen() }
            composable(QRCODE_ROUTE) {
                QRCodeScreen(
                    { checkCameraPermission(context) },
                    textResult,
                    context, navController
                )
            }
            composable(CALENDAR_ROUTE) {
                CalendarScreen(
                    navController = navController
                )
            }
            composable(EVENT_DESCRIPTION) {
                FullEventScreen(
                    onScanClicked = { checkCameraPermission(context) },
                    context = context
                )
            }

            composable(REWARD_DESCRIPTION){
                FullRewardScreen(context = context, onOrderClicked = onOrderClicked)
            }
            composable(NOTIFICATION_DESCRIPTION){
                NotificationFullScreen(context = context)
            }

            composable(NOTIFICATION_ROUTE){
                NotificationScreen(navController = navController)
            }

        }
    }
}
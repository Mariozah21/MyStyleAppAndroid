package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.data.Item
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import org.koin.androidx.compose.getViewModel

@Composable
fun MyProfileScreen(
    navigation: INavigationRouter,
    navController: NavController,
    viewModel: FirestoreViewModel = getViewModel()
){
    BottomNavigation(false, navigation ,navController = navController, topBarTitle = stringResource(
            R.string.my_profile_app_bar_title)
        ) {
        MyProfileScreenContent(navigation = navigation, viewModel = viewModel)
    }

}

@Composable
fun MyProfileScreenContent(navigation: INavigationRouter,viewModel: FirestoreViewModel) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val myItems by viewModel.myItems
    val description: String = ""

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        user?.photoUrl?.let { profileImageUrl ->
            Image(
                painter = rememberImagePainter(profileImageUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
        }

        // User Info
        user?.let { currentUser ->
            Text(
                text = currentUser.displayName ?: "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = currentUser.email ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Description (if available)


            // Number of items they are selling
            Text(
                text = stringResource(R.string.my_profile_items_for_sale) + myItems.size.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Sign Out Button
        Button(
            onClick = {
                Firebase.auth.signOut()
                user = null
                navigation.navToWelcomeScreen()
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.my_profile_sign_out))
        }
    }
}
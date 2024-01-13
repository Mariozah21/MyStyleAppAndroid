package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.WelcomeScreenDestination
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun MyProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel = getViewModel()
){
    MyScaffold(topBarTitle = stringResource(R.string.my_profile_app_bar_title), navigator = navigator, showBottomNavigation = true, showCartIcon = true) {
        MyProfileScreenContent(navigator, viewModel = viewModel)
    }
    /*
    BottomNavigation(false, navigator, topBarTitle = stringResource(
            R.string.my_profile_app_bar_title)
        ) {
        MyProfileScreenContent(navigator, viewModel = viewModel)
    }

     */

}

@Composable
fun MyProfileScreenContent(navigator: DestinationsNavigator,viewModel: FirestoreViewModel) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val myItems by viewModel.myItems

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
                navigator.navigate(WelcomeScreenDestination)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.my_profile_sign_out))
        }
    }
}
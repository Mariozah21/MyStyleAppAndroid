package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation

@Composable
fun ChatScreen(
    navigation: INavigationRouter,
    navController: NavController
){
    BottomNavigation(false,navigation,navController = navController, topBarTitle = stringResource(R.string.chat_screen_title)) {
        ChatScreenContent()
    }
}
@Composable
fun ChatScreenContent(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.chat_screen_will_be_implemented_later),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
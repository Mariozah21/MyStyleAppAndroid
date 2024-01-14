package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MainScreenDestination

@Destination
@Composable
fun OrderSuccessScreen(
    navigator: DestinationsNavigator,
    totalCost: Double,
    streetName: String?,
    number: String?,
    city: String?,
    searchText: String?,

){
    BottomNavigation(false,navigator, topBarTitle = "Success screen") {
        OrderSuccessScreenContent(
            navigator = navigator,
            totalCost = totalCost,
            streetName = streetName,
            number = number,
            city = city,
            searchText = searchText,
            )
    }
}
@Composable
fun OrderSuccessScreenContent(
    navigator: DestinationsNavigator,
    streetName: String?,
    number: String?,
    city: String?,
    searchText: String?,
    totalCost: Double,
     // Callback for the button click
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val addressText = buildAddressText(streetName, number, city, searchText)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thank you for your order! We will process it and deliver it to your address:\n\n$addressText\n\nas soon as possible. The total price of your order is €$totalCost.",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Button to return to the main screen
            Button(
                onClick = { navigator.navigate(MainScreenDestination) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Return to Main Screen")
            }
        }
    }
}

private fun buildAddressText(streetName: String?, number: String?, city: String?, searchText: String?): String {
    return when {
        !streetName.isNullOrBlank() && !number.isNullOrBlank() && !city.isNullOrBlank() -> "$streetName $number, $city"
        !searchText.isNullOrBlank() -> searchText
        else -> "Address not provided"
    }
}

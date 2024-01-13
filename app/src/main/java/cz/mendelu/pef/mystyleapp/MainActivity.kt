package cz.mendelu.pef.mystyleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import cz.mendelu.pef.mystyleapp.ui.theme.MyStyleAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.mystyleapp.ui.screens.NavGraphs


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseFirestore.setLoggingEnabled(true)
        setContent {
            MyStyleAppTheme {
                // A surface container using the 'background' color from the theme
                DestinationsNavHost(
                    navGraph = NavGraphs.root
                )
            }
        }
    }
}

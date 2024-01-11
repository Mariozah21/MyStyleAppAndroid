package cz.mendelu.pef.mystyleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import cz.mendelu.pef.mystyleapp.navigation.Destination
import cz.mendelu.pef.mystyleapp.ui.theme.MyStyleAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import cz.mendelu.pef.mystyleapp.ui.screens.NavGraphs
import dagger.hilt.android.AndroidEntryPoint



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

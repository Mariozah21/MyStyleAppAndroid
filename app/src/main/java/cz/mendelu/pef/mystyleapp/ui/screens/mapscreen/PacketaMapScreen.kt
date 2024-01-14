package cz.mendelu.pef.mystyleapp.ui.screens.mapscreen

import android.graphics.Path
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.map.CustomMapRenderer
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointResponse
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import org.koin.androidx.compose.getViewModel


@Destination()
@Composable
fun PacketaMapScreen(
    navigator: DestinationsNavigator,
    viewModel: PacketaMapViewModel = getViewModel())
{


    val uiState: MutableState<UiState<List<PointResponse>, MarkerClusteringErrors>> =
        rememberSaveable { mutableStateOf(UiState()) }

    viewModel.placesUIState.value.let {
        uiState.value = it
    }
    MyScaffold(topBarTitle = "Packeta map", navigator = navigator, showBackArrow = true, onBackClick = {navigator.popBackStack()}) {
        PacketaMapScreenContent(
            paddingValues = it,
            navigator = navigator,
            mapData = uiState.value.data
        )
    }

}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun PacketaMapScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    mapData: List<PointResponse>?,
) {


    val mapUiSettings by remember { mutableStateOf(
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false)
    ) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(49.20486174657262, 16.590964320587158), 11f)
    }
    val context = LocalContext.current
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var customRenderer by remember { mutableStateOf<CustomMapRenderer?>(null) }
    var manager by remember { mutableStateOf<ClusterManager<PointItem>?>(null) }

    if (mapData != null) {
        manager?.addItems(mapData.flatMap { it.data.values })
        manager?.cluster()
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState
        ){
            if (mapData != null) {
                MapEffect(mapData) {
                    if (googleMap == null) {
                        googleMap = it
                    }
                    if (manager == null) {
                        manager = ClusterManager<PointItem>(context, googleMap)
                        customRenderer = CustomMapRenderer(context, googleMap!!, manager!!)

                        manager?.apply {
                            algorithm = GridBasedAlgorithm()
                            renderer = customRenderer
                        }

                        manager?.addItems(mapData.flatMap { it.data.values })

                    }

                    googleMap?.setOnCameraIdleListener {
                        manager?.cluster()
                    }
                }
            }
        }
    }
}





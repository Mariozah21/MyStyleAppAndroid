package cz.mendelu.pef.mystyleapp.ui.screens.mapscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.mystyleapp.architecture.BaseViewModel
import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.packetaApi.communication.IPacketaRemoteRepository
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class PacketaMapViewModel (
    private val repository: IPacketaRemoteRepository
) : BaseViewModel(){

    val placesUIState: MutableState<UiState<List<PointResponse>,
            MarkerClusteringErrors>> = mutableStateOf(
        UiState(loading = true)
    )

    init {
        loadPlaces()
    }
    fun loadPlaces() {
        launch {
            val result = withContext(Dispatchers.IO) {
                repository.getAllBranches()
            }

            when (result) {
                is CommunicationResult.Error ->
                    placesUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MarkerClusteringErrors()
                    )
                is CommunicationResult.Exception ->
                    placesUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MarkerClusteringErrors()
                    )
                is CommunicationResult.Success -> {
                    placesUIState.value = UiState(
                        loading = false,
                        data = result.data,
                        errors = null)
                }
                is CommunicationResult.CommunicationError ->
                    placesUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MarkerClusteringErrors()
                    )
            }
        }
    }
}
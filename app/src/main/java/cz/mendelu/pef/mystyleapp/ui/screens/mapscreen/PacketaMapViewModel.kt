package cz.mendelu.pef.mystyleapp.ui.screens.mapscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.mendelu.pef.mystyleapp.architecture.BaseViewModel
import cz.mendelu.pef.mystyleapp.architecture.CommunicationResult
import cz.mendelu.pef.mystyleapp.packetaApi.communication.IPacketaRemoteRepository
import cz.mendelu.pef.mystyleapp.packetaApi.model.BranchesResponse
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class PacketaMapViewModel (
    private val repository: IPacketaRemoteRepository
) : BaseViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val placesUIState: MutableState<UiState<BranchesResponse,
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
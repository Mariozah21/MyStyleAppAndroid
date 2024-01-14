package cz.mendelu.pef.mystyleapp.map

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.collections.PolygonManager
import cz.mendelu.pef.mystyleapp.packetaApi.model.PointItem

class CustomMapRenderer(
    private val context: Context,
    googleMap: GoogleMap,
    manager: ClusterManager<PointItem> //stara se o to kde jsou ulozene
) : DefaultClusterRenderer<PointItem>(context, googleMap, manager) {

    private val icons: MutableMap<Int, Bitmap> = mutableMapOf()

    private val polylineManager = PolygonManager(googleMap)

    override fun onBeforeClusterItemRendered(item: PointItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getIcon(item)))
    }
    override fun shouldRenderAsCluster(cluster: Cluster<PointItem>): Boolean {
        return cluster.size > 5
    }

    private fun getIcon(pointItem: PointItem): Bitmap {
        return MarkerUtil.createBitmapMarker(context,pointItem.place)
    }

}
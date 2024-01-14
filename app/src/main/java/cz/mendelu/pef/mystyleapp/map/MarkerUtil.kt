package cz.mendelu.pef.mystyleapp.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import cz.mendelu.pef.mystyleapp.R

class MarkerUtil {

    companion object {
        private const val CLUSTER_THRESHOLD = 4
        fun createMarkerIconFromResource(context: Context, resource: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, resource)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bm)
            drawable.draw(canvas)
            return bm
        }


        fun createBitmapMarker(
            context: Context,
            type: String,

        ): Bitmap {
            return createMarkerIconFromResource(context, R.drawable.packeta_logo)
        }
    }
}


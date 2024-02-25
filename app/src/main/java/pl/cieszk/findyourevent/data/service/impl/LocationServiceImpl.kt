package pl.cieszk.findyourevent.data.service.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import pl.cieszk.findyourevent.data.service.module.LocationService
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class LocationService(private val context: Context,
                      override var fusedLocationProviderClient: FusedLocationProviderClient
): LocationService {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun getLastLocation(activity: Activity, onSuccess: (Double, Double) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener(activity) { location ->
            if (location != null) {
                onSuccess.invoke(location.latitude, location.longitude)
            }
        }
    }
}
package pl.cieszk.findyourevent.data.service.module

import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient

interface LocationService {
    var fusedLocationProviderClient: FusedLocationProviderClient
    fun getLastLocation(activity: Activity, onSuccess: (Double, Double) -> Unit)
}
package pl.cieszk.findyourevent.common

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import pl.cieszk.findyourevent.data.model.Event
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

val SOME_DISTANCE_THRESHOLD = 10_000

suspend fun filterEventsByLocation(events: List<Event>, userLat: Double, userLng: Double): List<Event> {
    return events.filter { event ->
        val (eventLat, eventLng) = geocodeCityAndCountry(event.city, event.country)
        val distance = calculateDistance(userLat, userLng, eventLat, eventLng)
        distance <= SOME_DISTANCE_THRESHOLD
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371e3
    val latDistance = Math.toRadians(lat2 - lat1)
    val lonDistance = Math.toRadians(lon2 - lon1)
    val a = sin(latDistance / 2) * sin(latDistance / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(lonDistance / 2) * sin(lonDistance / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}

suspend fun geocodeCityAndCountry(city: String, country: String): Pair<Double, Double> {
    val client = OkHttpClient()
    val apiKey = "YOUR_API_KEY"
    val address = "$city, $country"
    val url = "https://maps.googleapis.com/maps/api/geocode/json?address=${address.replace(" ", "+")}&key=$apiKey"

    val request = Request.Builder()
        .url(url)
        .build()

    val response = client.newCall(request).execute()
    val responseBody = response.body?.string()

    return if (responseBody != null) {
        val jsonObject = JSONObject(responseBody)
        if ("OK".equals(jsonObject.getString("status"), ignoreCase = true)) {
            val location = jsonObject.getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONObject("location")
            val lat = location.getDouble("lat")
            val lng = location.getDouble("lng")
            Pair(lat, lng)
        } else {
            Pair(0.0, 0.0)
        }
    } else {
        Pair(0.0, 0.0)
    }
}
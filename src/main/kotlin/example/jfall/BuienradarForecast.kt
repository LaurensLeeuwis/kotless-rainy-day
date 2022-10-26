package example.jfall

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Serializable
data class BuienRadar(val forecast: Forecast)

@Serializable
data class Forecast(val fivedayforecast: List<DayForecast>)

@Serializable
data class DayForecast(val day: String, val rainChance: Int,
                       val weatherdescription: String, val iconurl: String)

fun getForecast(): BuienRadar {
    val client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(30)).build()
    val request = HttpRequest.newBuilder(URI.create("https://data.buienradar.nl/2.0/feed/json"))
        .GET().build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    return Json{ignoreUnknownKeys = true}.decodeFromString(response.body())
}

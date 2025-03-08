package com.practice

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import org.jsoup.Jsoup
import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val title: String,
    val artist: String,
    val link: String,
    val image: String
)

suspend fun scrapeSongs(): List<Song> {
    val client = HttpClient(CIO)
    val url = "https://www.pagalworld.us/"
    val songs = mutableListOf<Song>()

    try {
        // Fetching HTML content from the page
        val response: HttpResponse = client.get(url)
        val htmlContent: String = response.body()
        val document = Jsoup.parse(htmlContent)

        // Parsing the document and extracting song details
        document.select("a:has(img.b-lazy)").forEach { element ->
            // Extracting song details safely with default values
            val title = element.selectFirst("h3")?.text() ?: "Unknown Title"
            val artist = element.selectFirst("span.bk.s")?.ownText() ?: "Unknown Artist"
            val link = element.attr("href").takeIf { it.isNotBlank() } ?: "#"
            val imageUrl = element.selectFirst("img.b-lazy")
                ?.attr("src")?.takeIf { it.startsWith("https") }
                ?: element.selectFirst("img.b-lazy")?.attr("data-src")
                ?: ""  // Default empty if no image found

            // Only add songs with valid image URL
            if (imageUrl.isNotEmpty()) {
                songs.add(Song(title, artist, link, imageUrl))
            }
        }
    } catch (e: Exception) {
        // Improved error handling for better debugging
        println("Error occurred while scraping songs: ${e.message}")
        e.printStackTrace()
    } finally {
        client.close()  // Ensure to close the HTTP client after use
    }

    return songs
}

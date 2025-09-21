package util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import java.util.zip.GZIPInputStream


class DefaultUtils(private val httpClient: HttpClient = HttpClient.newHttpClient(),
    private val mapper: ObjectMapper = ObjectMapper()) : Utils {

    override fun fetchData(route: String?, authorizationToken: String?): JsonNode {
        val request = HttpRequest.newBuilder()
            .uri(URI(route))
            .header("Authorization", "Bearer $authorizationToken")
            .build()

        val response: HttpResponse<String> = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return mapper.readTree(response.body())
    }

    override fun postData(route: String?, body: Any, authorizationToken: String?): Int {
        val requestBody = ObjectMapper().writeValueAsString(body)

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI(route))
            .headers("Authorization", "Bearer $authorizationToken")
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val httpResponse = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString())

        return httpResponse.statusCode()
    }

    override fun decompressAndDecode(base64Text: String): String {
        val decoded: ByteArray = Base64.getDecoder().decode(base64Text)
        val gzip = GZIPInputStream(ByteArrayInputStream(decoded))
        val inputStreamReader = InputStreamReader(gzip)
        val reader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var line: String

        while ((reader.readLine().also { line = it }) != null) {
            stringBuilder.append(line).append('\n')
        }

        return stringBuilder.toString()
    }
}
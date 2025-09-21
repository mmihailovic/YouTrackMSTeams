package util

import com.fasterxml.jackson.databind.JsonNode

interface Utils {

    /**
     * This method fetches data from endpoint
     * @param route URL of endpoint
     * @param authorizationToken token used for authorization
     * @return [JsonNode] objects which represents the response
     */
    fun fetchData(route: String?, authorizationToken: String?): JsonNode

    /**
     * This method executes a POST request to endpoint
     * @param route URL of endpoint
     * @param body request body
     * @param authorizationToken token used for authorization
     * @return [Int] representing the status code of the response
     */
    fun postData(route: String?, body: Any, authorizationToken: String?): Int

    /**
     * This method decodes and decompresses the given string
     * @param base64Text Base64 encoded and compressed text
     * @return The decoded and decompressed string
     */
    fun decompressAndDecode(base64Text: String): String
}
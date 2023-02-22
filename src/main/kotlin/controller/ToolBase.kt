package org.alerHughes.controller
import kotlinx.serialization.json.Json

internal val CustomJson = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    isLenient = true
    allowStructuredMapKeys = true
}
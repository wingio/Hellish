package xyz.wingio.hellish.di

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.hellish.BuildConfig
import xyz.wingio.hellish.rest.ApiService
import xyz.wingio.hellish.rest.PointercrateClient
import xyz.wingio.hellish.util.AppLogger
import xyz.wingio.hellish.util.buildUserAgent


val HttpModule = module {

    @OptIn(ExperimentalSerializationApi::class)
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
    }

    fun provideHttpClient(context: Context, appLogger: AppLogger): HttpClient {
        return HttpClient(CIO) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLogger.debug(message)
                        }
                    }
                }
            }

            defaultRequest {
                header(HttpHeaders.UserAgent, buildUserAgent(context))
            }
        }
    }

    singleOf(::provideJson)
    singleOf(::provideHttpClient)
    singleOf(::ApiService)
    singleOf(::PointercrateClient)

}
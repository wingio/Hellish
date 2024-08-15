package xyz.wingio.hellish.di

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
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
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.domain.repository.PointercrateRepository
import xyz.wingio.hellish.rest.service.ApiService
import xyz.wingio.hellish.rest.service.PointercrateService
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
    fun provideHttpClient(
        context: Context,
        appLogger: AppLogger,
        authManager: AuthManager
    ): HttpClient {
        return HttpClient(CIO) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    sanitizeHeader("[ CENSORED ]") { it == HttpHeaders.Authorization }
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLogger.debug(message)
                        }
                    }
                }
            }

            defaultRequest {
                if (authManager.isAuthed) header(HttpHeaders.Authorization, "Bearer ${authManager.authToken}")
                header(HttpHeaders.UserAgent, buildUserAgent(context))
            }
        }
    }

    singleOf(::provideJson)
    singleOf(::provideHttpClient)

    singleOf(::ApiService)
    singleOf(::PointercrateService)
    singleOf(::PointercrateRepository)

}
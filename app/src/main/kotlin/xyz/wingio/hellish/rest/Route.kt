package xyz.wingio.hellish.rest

import xyz.wingio.hellish.BuildConfig

open class Route(private val route: String) {
    val url get() = "${BuildConfig.BASE_URL}$route"
    override fun toString() = route
}

object Routes {

    object V1: Route("/v1") {

        object Auth: Route("$V1/auth")

    }

    object V2: Route("/v2") {

        class Demons(id: Int): Route("$V2/demons/$id") {

            companion object: Route("$V2/demons") {

                val Listed = Route("$Demons/listed")

            }

        }

    }

}
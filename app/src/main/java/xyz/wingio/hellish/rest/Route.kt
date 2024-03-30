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

}

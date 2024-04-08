package xyz.wingio.hellish.util.coil

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Target
import coil3.BitmapImage
import coil3.annotation.ExperimentalCoilApi
import coil3.intercept.Interceptor
import coil3.request.ImageResult
import xyz.wingio.hellish.domain.manager.PaletteManager
import xyz.wingio.hellish.util.AppLogger
import xyz.wingio.hellish.util.url

/**
 * Uses [Palette] to retrieve a seed color from any image
 */
class PaletteGeneratorInterceptor(
    private val paletteManager: PaletteManager
): Interceptor {

    private val logger = AppLogger("PaletteGenerator")

    @OptIn(ExperimentalCoilApi::class)
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val result = chain.proceed()

        result.image?.let {
            // Only generate if image is a bitmap and hasn't been processed before
            if (it is BitmapImage && !paletteManager.hasSeedColor(result.request.data.toString())) {
                Palette.Builder(it.bitmap.copy(Bitmap.Config.ARGB_8888, false)) // Force bitmap to use different Config due to Config.HARDWARE not being supported
                    .addTarget(Target.DARK_VIBRANT)
                    .generate { palette ->
                        palette?.getSwatchForTarget(Target.DARK_VIBRANT).let { swatch ->
                            if (swatch == null) logger.warn("Couldn't generate color for: ${result.request.url}")
                            else paletteManager.setSeedColor(result.request.url, Color(swatch.rgb))
                        }
                    }
            }
        }

        return result
    }

}
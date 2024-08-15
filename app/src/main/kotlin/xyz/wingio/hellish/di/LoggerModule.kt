package xyz.wingio.hellish.di

import org.koin.dsl.module
import xyz.wingio.hellish.util.AppLogger

val LoggerModule = module {

    single { AppLogger("HTTP") }

}
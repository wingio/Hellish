package xyz.wingio.hellish.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.domain.manager.PaletteManager
import xyz.wingio.hellish.domain.manager.PreferenceManager
import xyz.wingio.hellish.domain.manager.RecentSearchManager

val ManagerModule = module {

    singleOf(::AuthManager)
    singleOf(::PreferenceManager)
    singleOf(::PaletteManager)
    singleOf(::RecentSearchManager)

}
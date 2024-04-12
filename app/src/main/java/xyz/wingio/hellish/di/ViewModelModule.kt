package xyz.wingio.hellish.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import xyz.wingio.hellish.ui.screen.auth.viewmodel.LoginViewModel
import xyz.wingio.hellish.ui.screen.demonlist.viewmodel.DemonListViewModel
import xyz.wingio.hellish.ui.screen.settings.viewmodel.SettingsViewModel

val ViewModelModule = module {

    factoryOf(::LoginViewModel)

    factoryOf(::DemonListViewModel)

    factoryOf(::SettingsViewModel)

}
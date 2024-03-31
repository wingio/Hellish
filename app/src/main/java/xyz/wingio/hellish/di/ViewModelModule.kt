package xyz.wingio.hellish.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import xyz.wingio.hellish.ui.screen.auth.viewmodel.LoginViewModel

val ViewModelModule = module {
    factoryOf(::LoginViewModel)
}
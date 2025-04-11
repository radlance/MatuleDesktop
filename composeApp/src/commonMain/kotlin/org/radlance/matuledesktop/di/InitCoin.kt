package org.radlance.matuledesktop.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initCoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(authModule, commonModule, productModule, cartModule)
    }
}
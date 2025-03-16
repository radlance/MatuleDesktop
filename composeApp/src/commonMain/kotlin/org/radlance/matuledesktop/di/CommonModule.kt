package org.radlance.matuledesktop.di

import MatuleDesktop.composeApp.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.radlance.matuledesktop.data.auth.AuthRepositoryImpl
import org.radlance.matuledesktop.data.product.RemoteProductRepository
import org.radlance.matuledesktop.domain.auth.AuthRepository
import org.radlance.matuledesktop.domain.auth.AuthResult
import org.radlance.matuledesktop.domain.product.ProductRepository
import org.radlance.matuledesktop.presentation.auth.common.AuthResultMapper
import org.radlance.matuledesktop.presentation.auth.common.AuthResultUiState
import org.radlance.matuledesktop.presentation.auth.common.Validate
import org.radlance.matuledesktop.presentation.auth.signin.SignInViewModel
import org.radlance.matuledesktop.presentation.auth.signup.SignUpViewModel
import org.radlance.matuledesktop.presentation.common.ProductViewModel

val authModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)

    singleOf(Validate::Base).bind<Validate>()
    singleOf(::AuthResultMapper).bind<AuthResult.Mapper<AuthResultUiState>>()

}

val commonModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Storage)
            defaultSerializer = KotlinXSerializer()
        }
    }

    single<Auth> { get<SupabaseClient>().auth }
}

val productModule = module {
    singleOf(::RemoteProductRepository).bind<ProductRepository>()
    viewModelOf(::ProductViewModel)
}
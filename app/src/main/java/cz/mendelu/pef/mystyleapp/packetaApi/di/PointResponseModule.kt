package cz.mendelu.pef.mystyleapp.packetaApi.di

import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val pointResponseModule = module {
    single(named("IO")) { Dispatchers.IO }
    single { Moshi.Builder().build() }
}

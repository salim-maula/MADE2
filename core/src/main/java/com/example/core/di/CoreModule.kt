package com.example.core.di

import androidx.room.Room
import com.example.core.BuildConfig
import com.example.core.data.source.DeveloperRepository
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.room.DeveloperRoomDatabase
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.repository.IDeveloperRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val API_KEY = BuildConfig.API_KEY
private const val BASE_URL = BuildConfig.BASE_URL

val networkModule = module {
    single {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/ORtIOYkm5k6Nf2tgAK/uwftKfNhJB3QS0Hs608SiRmE=")
            .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
            .add(hostname, "sha256/uyPYgclc5Jt69vKu92vci6etcBDY8UNTyrHQZJpVoZY=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestbuilder = original.newBuilder()
                    .header("Authorization", API_KEY)
                val requst = requestbuilder.build()
                chain.proceed(requst)
            }.connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(11, TimeUnit.MINUTES)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IDeveloperRepository> {
        DeveloperRepository(
            get(),
            get()
        )
    }
}

val databaseModule = module {
    factory {
        get<DeveloperRoomDatabase>().developerDao()
    }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("githubUser".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            DeveloperRoomDatabase::class.java, "Developer.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}


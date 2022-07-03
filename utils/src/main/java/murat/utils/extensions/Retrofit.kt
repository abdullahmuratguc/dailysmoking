package murat.utils.extensions

import dagger.Lazy
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Created by Murat
 */

fun Retrofit.Builder.lazyClient(client: Lazy<OkHttpClient>): Retrofit.Builder {
    return callFactory { request -> client.get().newCall(request) }
}
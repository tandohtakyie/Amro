package aim.high.amro.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class TmdbSecurityInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}

package br.com.murilomoro.data.remote

import br.com.murilomoro.data.remote.dto.MovieDto
import br.com.murilomoro.data.remote.interceptor.RxRemoteErrorInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<ResponseWrap<MovieDto.Response>>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id: Long): Single<MovieDto.Response>

    companion object {
        private const val API_KEY = "b5805265ed3b109f745c58879e79f31e"
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun createMovieService(
            rxRemoteErrorInterceptor: RxRemoteErrorInterceptor
        ): MovieService {
            val client = OkHttpClient().newBuilder()
                .addInterceptor {
                    val url = it.request().url().newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()

                    val request = it.request().newBuilder()
                        .url(url)
                        .build()

                    it.proceed(request)
                }
                .addInterceptor(rxRemoteErrorInterceptor)
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build()
                .create(MovieService::class.java)
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }
    }
}
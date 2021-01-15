package com.example.demo.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/*
 * Created by yaodh on 2019/2/19.
 */
class RetrofitClient private constructor() {
    val query8864Api by lazy {
        create("https://api.8684.cn/", Query8864Api::class.java)
    }
    val queryBjgjApi by lazy {
        create("https://www.bjbus.com/home/", QueryBjgjApi::class.java)
    }
    private fun <T> create(baseUrl: String, clazz: Class<T>): T {
        val okBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

        val builder: Retrofit.Builder = Retrofit.Builder()
        // 优先使用该类
        builder.addConverterFactory(ScalarsConverterFactory.create())
        builder.addConverterFactory(GsonConverterFactory.create())
        builder.client(okBuilder.build())
        builder.baseUrl(baseUrl)
        val retrofit: Retrofit = builder.build()
        return retrofit.create(clazz)
    }

    companion object {
        private const val TIMEOUT = 30 * 1000 // 超时时间

        // 单例的实例
        val instance = RetrofitClient()
    }
}
package com.example.demo.http

import com.example.demo.gps.BaseModel
import com.example.demo.gps.NearByResult
import com.example.demo.gps.StationRoutes
import retrofit2.http.*

interface QueryBjgjApi {
    @GET("ajax_rtbus_data.php?act=busTime")
    suspend fun realTimeBusInfo(@Query("selBLine") selBLine: String,
                                @Query("selBDir") selBDir: String,
                                @Query("selBStop") selBStop: String): String

}

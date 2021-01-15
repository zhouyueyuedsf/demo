package com.example.demo.http

import com.example.demo.gps.BaseModel
import com.example.demo.gps.NearByResult
import com.example.demo.gps.RealTimeInfo
import com.example.demo.gps.StationRoutes
import retrofit2.http.*

interface Query8864Api {
    @FormUrlEncoded
    @POST("bus_api_v1.php")
    suspend fun nearBusByLocation(
        @Field("k") k: String,
        @Field("ecity") ecity: String,
        @Field("lng") lng: String, // 经度
        @Field("lat") lat: String, // 纬度
        @Field("num") num: String, // 返回的数量
        @Field("jl") jl: String = "1000",
        @Field("appkey") appkey: String = "Yv9cL8wTwZgr",
    ): BaseModel<List<StationRoutes>>

    /**
     * @param line_name 公交站名字
     * @param to_station_name 终点名字，确定方向
     */
    @GET("bus_position_1.php")
    suspend fun realTimeBusInfo(@Query("ecity") ecity: String,
                                @Query("line_name") line_name: String,
                                @Query("to_station_name") to_station_name: String,
                                @Query("appkey") appkey: String = "Yv9cL8wTwZgr"): RealTimeInfo

    @GET("bus_position_1.php?ecity=beijing&line_name=963路&to_station_name=龙泉驾校&appkey=Yv9cL8wTwZgr")
    suspend fun realTimeBusInfo(): String
}

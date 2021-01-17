package com.example.demo.gps


/**
 * 一个公交站拥有的公交路线
 */
data class StationRoutes(val zid: String,
                         val zhan: String,
                         val t_name: String,
                         val xzhan: String,
                         val yzhan: String,
                         val code: String,
                         val ezhan: String,
                         val juli: String,
                         val line_info: List<RouteInfo>)


data class RouteInfo(val id: String,
                     val busw: String,
                     val code: String,
                     val shuzi: String)

//
data class RealTimeInfo(val line_info: LineInfo,
                        val stations: List<SimpleStations>,
                        val timestamp: Int)

data class LineInfo(val bus_staname: String,
                         val sta_sta: String,
                         val end_sta: String,
                         val station_count: Int)

data class SimpleStations(val station_name: String,
                          val zhan: String,
                          val code: String)



data class StationStartAndEnd(val id: String,
                              val busname: String,
                              val t_name_1: String,
                              val t_name_2: String,
                              val tid: String,
                              val kind: String,
                              val pm: String,
                              val code: String)


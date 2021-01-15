package com.example.demo.gps

import android.Manifest
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.demo.R
import com.example.demo.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.net.URLEncoder


class GpsActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        private const val RC_LOCATION_CONTACTS_PERM = 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        methodRequiresLocationPermission()
    }


    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private fun methodRequiresLocationPermission() {
        val perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            lifecycleScope.launch() {
                val nearByResult = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.query8864Api.nearBusByLocation("find_zhans_by_jwd", "beijing", "116.282071", "40.048732", "3")
                }
                Log.d("yyyyyy", "nearBusByLocation data $nearByResult")
                // 后厂村路，后厂村东路，永丰路南站
                val stationRoutes = nearByResult.data
                val startAndEndList = withContext(Dispatchers.IO) {
                    val res = arrayListOf<StationStartAndEnd>()
                    stationRoutes.forEach {
                        // 找到it.zid对应的站名的所有公交车路线的start和end
                        RetrofitClient.instance.query8864Api.stationLineStartAndEndList("the_station_line", "beijing", it.zid).data.map { stationStartAndEnd ->
                            res.add(stationStartAndEnd)
                        }
                    }
                    res
                }
                Log.d("yyyyyy", "startAndEndList $startAndEndList")
                val realTimeInfoList = withContext(Dispatchers.IO) {
                    // fetchBjBusApiData()
                    val res = arrayListOf<RealTimeInfo>()
                    // https://api.8684.cn/bus_position_1.php?ecity=beijing&line_name=963路&to_station_name=龙泉驾校&appkey=Yv9cL8wTwZgr
                    startAndEndList.forEach {
                        res.add(RetrofitClient.instance.query8864Api.realTimeBusInfo("beijing", it.busname, it.t_name_1))
                    }
                    res
                }
                Log.d("yyyyyy", "realTimeInfo $realTimeInfoList")

            }
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale),
                RC_LOCATION_CONTACTS_PERM, *perms)
        }
    }

    private suspend fun fetchBjBusApiData(): Pair<ArrayList<String>, ArrayList<String>> {
        val html = RetrofitClient.instance.queryBjgjApi.realTimeBusInfo("10", "5582831127904445311", "3")
        Log.d("yyyyyy", "html $html")
        val document = Jsoup.parse(html)
        val aEliments = document.getElementsByTag("i")
        // 途中车辆
        val busingList = arrayListOf<String>()
        // 到站车辆
        val busedList = arrayListOf<String>()
        aEliments.forEach {
            val classValue = it.attr("class")
            Log.d("yyyyyy", "classValue $classValue")
            if (classValue.isNotEmpty()) {
                if (classValue == "\\\"busc\\\"") {
                    // 途中车辆
                    val parent = it.parent()
                    val parentId = parent.attr("id")
                    if (parentId.isEmpty()) return@forEach
                    busingList.add(parentId.substring(0, parentId.length - 1))
                } else if (classValue == "\\\"buss\\\"") {
                    // 到站车辆
                    val parent = it.parent()
                    busedList.add(parent.attr("id"))
                }
            }
        }
        return Pair(busingList, busedList)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }
}